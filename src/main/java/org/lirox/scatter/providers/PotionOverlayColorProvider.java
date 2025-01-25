package org.lirox.scatter.providers;

import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;

import java.awt.*;
import java.util.List;

public class PotionOverlayColorProvider implements ItemColorProvider {
    @Override
    public int getColor(ItemStack stack, int layer) {
        if (layer == 1) {
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(stack);
            if (effects.isEmpty()) return 0;
            return PotionUtil.getColor(effects);
        }
        return 0xFFFFFF;
    }
}