package org.lirox.scatter.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class AnchorEffect extends StatusEffect {
    public AnchorEffect() {
        super(StatusEffectCategory.HARMFUL, 0x423c63);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.isInLava() || entity.isSubmergedInWater()) entity.addVelocity(new Vec3d(0,-1,0));
        super.applyUpdateEffect(entity, amplifier);
    }
}