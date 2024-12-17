package org.lirox.scatter.mixin;

import me.x150.renderer.render.Renderer3d;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(PlayerEntityRenderer.class)
public class TestRenderMixin {
    @Inject(method = "render*", at = @At("TAIL"))
    public void renderTest(AbstractClientPlayerEntity player, float f, float g, MatrixStack matrixStack, VertexConsumerProvider buffer, int i, CallbackInfo ci) {
        Renderer3d.renderFilled(matrixStack, Color.RED, player.getPos(), new Vec3d(1, 1, 1));
    }
}
