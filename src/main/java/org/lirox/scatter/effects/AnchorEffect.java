package org.lirox.scatter.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class AnchorEffect extends StatusEffect {
    public AnchorEffect() {
        super(StatusEffectCategory.HARMFUL, 0xffffff);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    // Todo: Fix color + Make it pull you down in liquids
}