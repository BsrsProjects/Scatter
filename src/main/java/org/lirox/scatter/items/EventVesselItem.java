package org.lirox.scatter.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EventVesselItem extends Item {
    public EventVesselItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        // Todo: Text by event type
        return "MAgic thing: Spell12321312312312312";
    }

    @Override
    public boolean isFireproof() {
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.clear();
        tooltip.add(Text.empty()); // Todo: What does it do + uses, maybe remove later idk
        super.appendTooltip(stack, world, tooltip, context);
    }

    // Todo: Something that edit its color idk
}
