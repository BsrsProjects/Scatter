package org.lirox.scatter.items.exclusives;
// LiroxDeYamon


import net.fabricmc.loader.api.LanguageAdapter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Language;
import net.minecraft.util.Rarity;

public class ScatterItem extends Item {
    public ScatterItem(Settings settings) {
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
