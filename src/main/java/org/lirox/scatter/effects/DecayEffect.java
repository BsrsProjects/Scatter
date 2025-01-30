package org.lirox.scatter.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class DecayEffect extends StatusEffect {
    public DecayEffect() {
        super(StatusEffectCategory.HARMFUL, 0x6b212e);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    private static final UUID HEALTH_MODIFIER_ID = UUID.fromString("4896fe2b-6a06-4cf1-9fa7-f11a92895f1d");

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        EntityAttributeInstance healthAttribute = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthAttribute != null) {
            healthAttribute.removeModifier(HEALTH_MODIFIER_ID);
            double reduction = -(amplifier+1) * 2;
            EntityAttributeModifier modifier = new EntityAttributeModifier(HEALTH_MODIFIER_ID, "decay", reduction, EntityAttributeModifier.Operation.ADDITION);
            healthAttribute.addPersistentModifier(modifier);
            entity.damage(entity.getDamageSources().dragonBreath(), 1f);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        EntityAttributeInstance healthAttribute = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthAttribute != null) healthAttribute.removeModifier(HEALTH_MODIFIER_ID);
        entity.heal(amplifier+1);
    }
}