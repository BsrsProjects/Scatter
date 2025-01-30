package org.lirox.scatter.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class LavaAffinityEffect extends StatusEffect {
    public LavaAffinityEffect() {
        super(StatusEffectCategory.HARMFUL, 0xcd5b45);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    // Todo: Make it make you fast when in lava
}