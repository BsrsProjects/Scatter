package org.lirox.scatter.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.lirox.scatter.Auxilium;
import org.lirox.scatter.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VenomfangItem extends SwordItem {
    private Random random = new Random();
    public VenomfangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!stack.hasNbt() || !stack.getNbt().contains("potionInfuse")) stack.getOrCreateNbt().putBoolean("potionInfuse", true);
        if (!stack.hasNbt() || !stack.getNbt().contains("calculateEffectTime")) stack.getOrCreateNbt().putBoolean("calculateEffectTime", true);
        if (!stack.hasNbt() || !stack.getNbt().contains("uses")) stack.getOrCreateNbt().putInt("uses", 0);
        if (!stack.hasNbt() || !stack.getNbt().contains("maxUses")) stack.getOrCreateNbt().putInt("maxUses", 50);

        if (entity instanceof PlayerEntity user) {
            ItemStack mainHand = user.getStackInHand(Hand.MAIN_HAND);
            ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(mainHand);
            if (offHand.getItem().equals(Items.POTION) && mainHand.getItem().equals(Register.VENOMFANG)) {
                List<StatusEffectInstance> potionEffects = Auxilium.calculatePotionDurationByAmplifier(
                        Auxilium.mergePotionEffects(
                                PotionUtil.getPotionEffects(offHand), PotionUtil.getCustomPotionEffects(offHand),
                                false, false), 20, 80);

                offHand.decrement(1);
                user.giveItemStack(new ItemStack(Items.GLASS_BOTTLE, 1));
                int increment = 10;
                if (Auxilium.maxPotionAmplifier(potionEffects) > 0) increment = 5;
                int uses = mainHand.getNbt().getInt("uses");
                int maxUses = mainHand.getNbt().getInt("maxUses");
                if (Auxilium.comparePotionTypesAndAmplifiers(effects, potionEffects)) {
                    if (uses < maxUses) mainHand.getNbt().putInt("uses", Math.min(maxUses, uses+increment));
                } else {
                    mainHand.getNbt().putInt("uses", increment);
                    PotionUtil.setCustomPotionEffects(mainHand, potionEffects);
                }
                user.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1, 1);
            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.hasNbt() && stack.getNbt().contains("CustomPotionEffects") && stack.getNbt().contains("uses") && stack.getNbt().getInt("uses") > 0) {
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(stack);
            for (StatusEffectInstance effect : effects) {
                if (!target.hasStatusEffect(effect.getEffectType())) {
                    target.addStatusEffect(effect);
                    stack.getNbt().putInt("uses", stack.getNbt().getInt("uses")-1);
//                    for (int i = 0; i < 15; i++) {
//                        target.getWorld().addParticle(ParticleTypes.EFFECT, target.getX(), target.getY(), target.getZ(), random.nextDouble()-0.5, random.nextDouble()-0.5, random.nextDouble()-0.5);
//                    }
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        for (int i = 0; i <= 0; i++) {
            tooltip.add(Text.translatable("item.scatter.venomfang.desc." + i).formatted(Formatting.GRAY));
        }

        if (stack.hasNbt() && stack.getNbt().contains("uses") && stack.getNbt().contains("maxUses")) {
            int maxUses = stack.getNbt().getInt("maxUses");
            int uses = Math.min(maxUses, Math.max(0, stack.getNbt().getInt("uses")));
            tooltip.add(Text.translatable("special.uses").append(String.valueOf(uses)).append("/").append(String.valueOf(maxUses)).formatted(Formatting.AQUA));
        }
        if (stack.hasNbt() && stack.getNbt().contains("CustomPotionEffects")) {
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(stack);
            tooltip.addAll(Auxilium.generatEffectsTooltip(effects));
        } else tooltip.addAll(Auxilium.generatEffectsTooltip(new ArrayList<>()));
    }
}
