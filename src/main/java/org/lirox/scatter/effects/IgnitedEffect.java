package org.lirox.scatter.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class IgnitedEffect extends StatusEffect {
    public IgnitedEffect() {
        super(StatusEffectCategory.HARMFUL, 0xff6200);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setFireTicks(10*amplifier);
        super.applyUpdateEffect(entity, amplifier);
    }
    // TODO: Fix fire ticks
}