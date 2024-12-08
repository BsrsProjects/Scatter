package org.lirox.scatter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lirox.scatter.entities.MeteoriteEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scatter implements ModInitializer {
    public final static String MOD_ID = "scatter";
    public final static Logger LOG = LoggerFactory.getLogger("Scatter");
    @Override
    public void onInitialize() {
        LOG.info("BALLS?!??!?!?!!???!?!?!?");
        Register.RegisterAll();
    }
}
