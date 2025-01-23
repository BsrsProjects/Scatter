package org.lirox.scatter.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

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
        if (!stack.hasNbt() || !stack.getNbt().contains("potionInfuse")) {
            stack.getOrCreateNbt().putBoolean("potionInfuse", true);
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.hasNbt() && stack.getNbt().contains("CustomPotionEffects")) {
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(stack);
            for (StatusEffectInstance effect : effects) {
                int time = 20*4/effect.getAmplifier();
                if (time < 20) time = 20;
                if (effect.getEffectType().equals(StatusEffects.INSTANT_DAMAGE) || effect.getEffectType().equals(StatusEffects.INSTANT_HEALTH)) time = 1;
                if (!target.hasStatusEffect(effect.getEffectType())) {
                    target.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), time, effect.getAmplifier()));
                    for (int i = 0; i < 15; i++) {
                        target.getWorld().addParticle(ParticleTypes.END_ROD, target.getX(), target.getY(), target.getZ(), random.nextDouble()-0.5, random.nextDouble()-0.5, random.nextDouble()-0.5);
                    }
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
