package org.lirox.scatter;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;

import java.time.Duration;
import java.util.*;

public class Auxilium {
    public static String generateCustomPotionName(List<Item> ingredients) {
        String name = "";
        for (Item ingredient : ingredients) {

        }
        return name;
    }


    public static List<Text> generateEffectsTooltip(List<StatusEffectInstance> effects) {
        return generateEffectsTooltip(effects, true, true, true);
    }

    public static List<Text> generateEffectsTooltip(List<StatusEffectInstance> effects, boolean addName, boolean addAmplifier, boolean addTime) {
        List<Text> text = new ArrayList<>();
        text.add(Text.translatable("special.potion_effects.effects").formatted(Formatting.GOLD));
        if (!effects.isEmpty()) {
            for (StatusEffectInstance effect : effects) {
                String amplifier = String.valueOf(effect.getAmplifier()+1);
                if (amplifier.equals("1")) amplifier = "";
                String duration = "";
                if (effect.getDuration() >= 20) {
                    Duration duration_raw = Duration.ofSeconds(effect.getDuration()/20);
                    String seconds = String.valueOf(duration_raw.toSecondsPart());
                    if (seconds.length() < 2) seconds = "0"+seconds;
                    duration = duration_raw.toMinutesPart()+":"+seconds;
                }
                text.add(Text.literal("- ").append(Text.translatable(effect.getTranslationKey())).append(" ").append(amplifier)
                        .append(" ").append(duration).formatted(Formatting.GREEN));
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

    public static boolean comparePotionAmplifiers(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2) {
         return comparePotionEffects(effects1, effects2, true, false);
    }

    public static boolean comparePotionDurations(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2) {
        return comparePotionEffects(effects1, effects2, false, true);
    }

    public static boolean comparePotionEffects(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2) {
        return comparePotionEffects(effects1, effects2, true, true);
    }

    public static boolean comparePotionEffects(List<StatusEffectInstance> effects1, List<StatusEffectInstance> effects2, boolean compareAmplifier, boolean compareDuration) {
        if (effects1.size() != effects2.size()) {
            return false;
        }

        for (int i = 0; i < effects1.size(); i++) {
            StatusEffectInstance effect1 = effects1.get(i);
            StatusEffectInstance effect2 = effects2.get(i);

            if (!effect1.getEffectType().equals(effect2.getEffectType())) return false;
            if (compareAmplifier && effect1.getAmplifier() != effect2.getAmplifier()) return false;
            if (compareDuration && effect1.getDuration() != effect2.getDuration()) return false;
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

    public static Text generateUsesTooltip(ItemStack stack) {
        return generateUsesTooltip(stack, 1);
    }

    public static Text generateUsesTooltip(ItemStack stack, float divideFactor) {
        if (stack.hasNbt() && stack.getNbt().contains("uses") && stack.getNbt().contains("maxUses")) {
            int maxUses = stack.getNbt().getInt("maxUses");
            int uses = (int) (MathHelper.clamp(stack.getNbt().getInt("uses"), 0, maxUses) /divideFactor);
            maxUses = (int) (maxUses/divideFactor);
            return Text.translatable("special.uses").append(String.valueOf(uses)).append("/").append(String.valueOf(maxUses)).formatted(Formatting.AQUA);
        }
        return Text.empty();
    }

    public static boolean stackNbtHasKey(ItemStack stack, String key) {
        return stack.hasNbt() && stack.getNbt().contains(key);
    }

    public static void defaultBooleanNbt(ItemStack stack, String key, boolean value) {
        if (!stackNbtHasKey(stack, key)) stack.getOrCreateNbt().putBoolean(key, value);
    }

    public static void defaultIntNbt(ItemStack stack, String key, int value) {
        if (!stackNbtHasKey(stack, key)) stack.getOrCreateNbt().putInt(key, value);
    }

    public static void defaultFloatNbt(ItemStack stack, String key, float value) {
        if (!stackNbtHasKey(stack, key)) stack.getOrCreateNbt().putFloat(key, value);
    }

    public static void defaultStringNbt(ItemStack stack, String key, String value) {
        if (!stackNbtHasKey(stack, key)) stack.getOrCreateNbt().putString(key, value);
    }

    public static List<Text> generateDescriptionTooltip(String key, int count) {
        List<Text> text = new ArrayList<>();
        for (int i = 0; i <= count-1; i++) {
            text.add(Text.translatable("item.scatter." + key + ".desc." + i).formatted(Formatting.GRAY));
        }
        return text;
    }
}
