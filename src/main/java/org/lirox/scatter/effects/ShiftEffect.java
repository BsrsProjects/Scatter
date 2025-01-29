package org.lirox.scatter.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ShiftEffect extends StatusEffect {
    public ShiftEffect() {
        super(StatusEffectCategory.HARMFUL, 0xffffff);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    // Todo: Fix color + Make it cancel first damage
}