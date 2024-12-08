package org.lirox.scatter.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.lirox.scatter.Register;
import org.lirox.scatter.entities.MeteoriteEntity;

public class InfusedMagmaItem extends Item {
    public InfusedMagmaItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getItemCooldownManager().isCoolingDown(user.getStackInHand(hand).getItem())) {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
//        world.spawnEntity();
        user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), 300);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
