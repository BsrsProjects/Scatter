package org.lirox.scatter.items.punishments;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

public class BorderTravelerItem extends Item {
    public BorderTravelerItem(Settings settings) {
        super(settings);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }

    @Override
    public boolean isFireproof() {
        return true;
    }
}
