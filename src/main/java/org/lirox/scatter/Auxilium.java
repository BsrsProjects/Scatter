package org.lirox.scatter;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Auxilium {
    public static String generateCustomPotionName(List<Item> ingredients) {
        String name = "";
        for (Item ingredient : ingredients) {

        }
        return name;
    }

    public static List<Text> generatEffectsTooltip(List<StatusEffectInstance> effects) {
        List<Text> text = new ArrayList<>();
        text.add(Text.translatable("special.potion_effects.effects").formatted(Formatting.GOLD));
        if (!effects.isEmpty()) {
            for (StatusEffectInstance effect : effects) {
                String amp = String.valueOf(effect.getAmplifier()+1);
                if (amp.equals("1")) amp = "";
                text.add(Text.literal("- ").append(Text.translatable(effect.getTranslationKey()).append(" ").append(amp).formatted(Formatting.GREEN)));
            }
        } else text.add(Text.translatable("special.potion_effects.drained").formatted(Formatting.DARK_GRAY));
        return text;
    }

    public static List<StatusEffectInstance> mergePotionEffects(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2) {
        return mergePotionEffects(effects1, effects2, true, true);
    }

    public static List<StatusEffectInstance> mergePotionEffectsDuration(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2) {
        return mergePotionEffects(effects1, effects2, true, false);
    }

    public static List<StatusEffectInstance> mergePotionEffectsAmplifier(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2) {
        return mergePotionEffects(effects1, effects2, false, true);
    }

    public static List<StatusEffectInstance> mergePotionEffects(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2, boolean mergeDuration, boolean mergeAmplifier) {
        for (StatusEffectInstance newEffect : effects2) {
            boolean merged = false;
            for (int i = 0; i < effects1.size(); i++) {
                StatusEffectInstance existingEffect = effects1.get(i);
                if (existingEffect.getEffectType().equals(newEffect.getEffectType())) {
                    int amplifier = existingEffect.getAmplifier();
                    if (mergeAmplifier) amplifier += newEffect.getAmplifier() + 1;
                    int duration = existingEffect.getDuration();
                    if (mergeDuration) duration += newEffect.getDuration();
                    effects1.set(i, new StatusEffectInstance(existingEffect.getEffectType(), duration, amplifier));
                    merged = true;
                    break;
                }
            }
            if (!merged) effects1.add(newEffect);
        }
        return effects1;
    }

    public static boolean comparePotionTypes(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2) {
        if (effects1.size() != effects2.size()) {
            return false;
        }

        for (int i = 0; i < effects1.size(); i++) {
            StatusEffectInstance effect1 = effects1.get(i);
            StatusEffectInstance effect2 = effects2.get(i);

            if (!effect1.getEffectType().equals(effect2.getEffectType())) return false;
        }

        return true;
    }

    public static boolean comparePotionTypesAndAmplifiers(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2) {
        if (effects1.size() != effects2.size()) {
            return false;
        }

        for (int i = 0; i < effects1.size(); i++) {
            StatusEffectInstance effect1 = effects1.get(i);
            StatusEffectInstance effect2 = effects2.get(i);

            if (!effect1.getEffectType().equals(effect2.getEffectType()) || effect1.getAmplifier() != effect2.getAmplifier()) return false;
        }

        return true;
    }

    public static int maxPotionAmplifier(List<StatusEffectInstance> effects) {
        int maxAmplifier = 0;

        for (StatusEffectInstance effect : effects) {
            int amplifier = effect.getAmplifier();
            if (amplifier > maxAmplifier) maxAmplifier = amplifier;
        }

        return maxAmplifier;
    }

    public static List<StatusEffectInstance> calculatePotionDurationByAmplifier(List<StatusEffectInstance> effects, int minTicks, int maxTicks) {
        for (int i = 0; i < effects.size(); i++) {
            StatusEffectInstance effect = effects.get(i);
            int duration = maxTicks / (effect.getAmplifier()+1);
            if (duration < minTicks) duration = minTicks;
            if (effect.getEffectType().equals(StatusEffects.INSTANT_DAMAGE) || effect.getEffectType().equals(StatusEffects.INSTANT_HEALTH)) duration = 1;
            effects.set(i, new StatusEffectInstance(effect.getEffectType(), duration, effect.getAmplifier()));
        }
        return effects;
    }

}
