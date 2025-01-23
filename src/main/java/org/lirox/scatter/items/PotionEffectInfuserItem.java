package org.lirox.scatter.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lirox.scatter.Register;

import java.awt.*;
import java.time.Duration;
import java.util.List;

public class PotionEffectInfuserItem extends Item {
    public PotionEffectInfuserItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack mainHand = user.getStackInHand(Hand.MAIN_HAND);
        ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);

        if (hand == Hand.MAIN_HAND) {
            if (mainHand.hasNbt() && mainHand.getNbt().contains("CustomPotionEffects")) {
                List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(mainHand);
                if (effects.isEmpty()) return TypedActionResult.fail(mainHand);

                mainHand.getNbt().remove("CustomPotionEffects");
                user.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT, 1, 1);
                if (offHand.hasNbt() && offHand.getNbt().contains("potionInfuse")) {
                    PotionUtil.setCustomPotionEffects(offHand, effects);
                    return TypedActionResult.success(mainHand);
                }

                for (StatusEffectInstance effect : effects) user.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), StatusEffectInstance.INFINITE, effect.getAmplifier() - 1));
                return TypedActionResult.success(mainHand);
            }
        }
        return TypedActionResult.fail(mainHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity user) {
            ItemStack mainHand = user.getStackInHand(Hand.MAIN_HAND);
            ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(mainHand);
            if (user.isCreative() && offHand.getItem().equals(Items.POTION) && mainHand.getItem().equals(Register.POTION_EFFECT_INFUSER)) {
                List<StatusEffectInstance> potionEffects = PotionUtil.getPotionEffects(offHand);
                effects.addAll(potionEffects);
                offHand.decrement(1);
                PotionUtil.setCustomPotionEffects(mainHand, effects);
                user.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT, 1, 1);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        for (int i = 0; i <= 2; i++) {
            tooltip.add(Text.translatable("item.scatter.potion_effect_infuser.desc." + i).formatted(Formatting.GRAY));
        }
        tooltip.add(Text.translatable("special.potion_effects.effects").formatted(Formatting.GOLD));
        if (stack.hasNbt() && stack.getNbt().contains("CustomPotionEffects")) {
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(stack);
            for (StatusEffectInstance effect : effects) {
                String amp = String.valueOf(effect.getAmplifier()+1);
                if (amp.equals("1")) amp = "";
                tooltip.add(Text.literal("- ").append(Text.translatable(effect.getTranslationKey()).append(" ").append(amp).formatted(Formatting.AQUA)));
            }
        } else tooltip.add(Text.translatable("special.potion_effects.drained").formatted(Formatting.DARK_GRAY));
    }
}
