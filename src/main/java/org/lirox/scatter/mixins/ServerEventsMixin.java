package org.lirox.scatter.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import org.lirox.scatter.events.EventSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class ServerEventsMixin {
    @Shadow private PlayerManager playerManager;
    @Unique
    private EventSystem eventSystem = new EventSystem(playerManager);

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        eventSystem.tick();
    }
}
