package org.lirox.scatter.items;

import com.google.gson.Gson;
import net.fabricmc.loader.api.LanguageAdapter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.command.argument.NbtElementArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.visitor.NbtElementVisitor;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lirox.scatter.Register;
import org.spongepowered.asm.util.LanguageFeatures;

import java.awt.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.List;

public class EventSealerItem extends Item {
    private final Random random = new Random();

    public EventSealerItem(Settings settings) {
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

//    @Override
//    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
//        return super.finishUsing(stack, world, user);
//
//    }

//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        ItemStack stack = user.getStackInHand(hand);
////        stack.decrement(1);
////        ItemStack newStack = new ItemStack(Register.EVENT_VESSEL);
////        newStack.writeNbt();
////        user.giveItemStack(newStack);
//        // HOW DO I FUCKING WRITE NBT?????
//        return super.use(world, user, hand);
//    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 150;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return Color.cyan.getRGB();
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.sendMessage(Text.literal("SHIT"));
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }
}
