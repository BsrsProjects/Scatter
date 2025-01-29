package org.lirox.scatter.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class PotionInstabilityEffect extends StatusEffect {
    public PotionInstabilityEffect() {
        super(StatusEffectCategory.HARMFUL, 0xffffff);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    // Todo: Fix
}