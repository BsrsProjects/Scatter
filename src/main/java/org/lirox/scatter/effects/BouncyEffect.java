package org.lirox.scatter.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BouncyEffect extends StatusEffect {
    public BouncyEffect() {
        super(StatusEffectCategory.HARMFUL, 0xffffff);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    // Todo: Fix color + Make it bounce on fall
}