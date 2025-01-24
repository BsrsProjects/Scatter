package org.lirox.scatter.mixins;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(DrawContext.class)
public abstract class ItemUsesRenderMixin {


    @Shadow public abstract void fill(int x1, int y1, int x2, int y2, int color);

    @Inject(method = "drawItemInSlot*", at = @At("TAIL"))
    public void drawItemInSlot(TextRenderer textRenderer, ItemStack stack, int x, int y, CallbackInfo ci) {
        renderUsesBar(x, y, stack);
    }

    @Unique
    private void renderUsesBar(int x, int y, ItemStack stack) {
        if (!stack.isEmpty() && stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            if (nbt.contains("uses") && nbt.contains("maxUses") && nbt.getInt("maxUses") > 0) {
                int color = Color.CYAN.getRGB();
                if (nbt.contains("usesColor")) color = nbt.getInt("usesColor");
                int uses = nbt.getInt("uses");
                int maxUses = nbt.getInt("maxUses");

                int length = MathHelper.ceil((float) Math.min(maxUses, Math.max(0, uses)) / maxUses * 13);
                drawBar(x+2, y + 12, 13, 2, Color.BLACK.getRGB());
                drawBar(x+2, y + 11, length, 1, color);
                // TODO: Fix it appearing behind item
                // TODO: Fix it not rendering in inventory
            }
        }
    }

    @Unique
    private void drawBar(int x, int y, int width, int height, int color) {
        fill(x, y, x + width, y - height, color);
    }
}