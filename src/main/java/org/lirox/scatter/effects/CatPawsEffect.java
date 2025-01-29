package org.lirox.scatter.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CatPawsEffect extends StatusEffect {
    public CatPawsEffect() {
        super(StatusEffectCategory.HARMFUL, 0xffffff);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    // Todo: Fix color + Make it ignore fall damage 5 block/level
}