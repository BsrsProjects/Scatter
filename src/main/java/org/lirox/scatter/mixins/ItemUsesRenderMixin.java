package org.lirox.scatter.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(DrawContext.class)
public abstract class ItemUsesRenderMixin {

    @Shadow public abstract void fill(int x1, int y1, int x2, int y2, int z, int color);

    @Inject(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At("TAIL"))
    public void drawItemInSlot(TextRenderer textRenderer, ItemStack stack, int x, int y, @Nullable String countOverride, CallbackInfo ci) {
        renderUsesBar(x, y, stack);
    }

    @Unique
    private void renderUsesBar(int x, int y, ItemStack stack) {
        if (!stack.isEmpty() && stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            if (nbt.contains("uses") && nbt.contains("maxUses") && nbt.getInt("maxUses") > 0 && nbt.getInt("uses") < nbt.getInt("maxUses")){
                int color = Color.CYAN.getRGB();
                if (nbt.contains("usesColor")) color = nbt.getInt("usesColor");

                int uses = nbt.getInt("uses");
                int maxUses = nbt.getInt("maxUses");

                int addY = 12;
                if (!stack.isItemBarVisible()) addY = 15;

                int length = MathHelper.ceil((float) Math.min(maxUses, Math.max(0, uses)) / maxUses * 13);

                drawBar(x+2, y + addY, 13, 2, Color.BLACK.getRGB());
                drawBar(x+2, y + addY - 1, length, 1, color);
            }
        }
    }

    @Unique
    private void drawBar(int x, int y, int width, int height, int color) {
        fill(x, y, x + width, y - height, 200, color);
        // the game translates by +200 when it has to draw the item count text - Octol1ttle
    }
}
