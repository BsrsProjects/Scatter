package org.lirox.scatter;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.impl.object.builder.FabricEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lirox.scatter.entities.MeteoriteEntity;
import org.lirox.scatter.items.InfusedMagmaItem;

public class Register {
    private static EntityType<MeteoriteEntity> METEORITE;
    public static Item INFUSED_MAGMA;
    public static void RegisterAll() {
        // Items
        INFUSED_MAGMA = registerItem("infused_magma", new InfusedMagmaItem(new FabricItemSettings()), ItemGroup.COMBAT);


        // Entities
//        METEORITE = registerEntity("meteorite", new MeteoriteEntity(EntityType<MeteoriteEntity>);

    }

    public static Item registerItem(String path, Item item, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Scatter.MOD_ID, path), item);
    }

    public static EntityType<Entity> registerEntity(String path, EntityType<Entity> entity) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Scatter.MOD_ID, path), entity);
    }
}
