package org.lirox.scatter.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.lirox.scatter.Auxilium;
import org.lirox.scatter.Register;

import java.util.ArrayList;
import java.util.List;

import static org.lirox.scatter.Auxilium.calculatePotionDurationByAmplifier;

public class LargePotionItem extends Item {
    public LargePotionItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (!PotionUtil.getCustomPotionEffects(stack).isEmpty()) return UseAction.DRINK;
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 37; // Why 37? no reason
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        Auxilium.defaultBooleanNbt(stack, "potionInfuse", true);
        Auxilium.defaultIntNbt(stack, "uses", 0);
        Auxilium.defaultIntNbt(stack, "maxUses", 50);

        if (entity instanceof PlayerEntity user) {
            ItemStack mainHand = user.getStackInHand(Hand.MAIN_HAND);
            ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(mainHand);
            if (offHand.getItem().equals(Items.POTION) && mainHand.getItem().equals(Register.LARGE_POTION)) {
                List<StatusEffectInstance> potionEffects = Auxilium.mergePotionEffects(
                                PotionUtil.getPotionEffects(offHand), PotionUtil.getCustomPotionEffects(offHand),
                        false, false);
                if (potionEffects.isEmpty()) return;
                offHand.decrement(1);
                user.giveItemStack(new ItemStack(Items.GLASS_BOTTLE, 1));
                int uses = mainHand.getNbt().getInt("uses");
                int maxUses = mainHand.getNbt().getInt("maxUses");
                if (Auxilium.comparePotionEffects(effects, potionEffects)) {
                    if (uses < maxUses) mainHand.getNbt().putInt("uses", Math.min(maxUses, uses+10));
                } else {
                    mainHand.getNbt().putInt("uses", 10);
                    PotionUtil.setCustomPotionEffects(mainHand, potionEffects);
                }
                user.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1, 1);
            }
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (Auxilium.stackNbtHasKey(stack, "uses") && stack.getNbt().getInt("uses") > 0) {
            for (StatusEffectInstance effect : PotionUtil.getCustomPotionEffects(stack)) {
                user.addStatusEffect(effect);
            }
            stack.getNbt().putInt("uses", Math.max(0, stack.getNbt().getInt("uses")-10));
            if (stack.getNbt().getInt("uses") == 0) stack.getNbt().remove("CustomPotionEffects");
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.addAll(Auxilium.generateDescriptionTooltip("large_potion", 3));
        tooltip.add(Auxilium.generateUsesTooltip(stack, 10));
        tooltip.addAll(Auxilium.generateEffectsTooltip(PotionUtil.getCustomPotionEffects(stack)));
    }
}
