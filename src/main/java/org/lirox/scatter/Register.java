package org.lirox.scatter;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.lirox.scatter.entities.MeteoriteEntity;
import org.lirox.scatter.items.InfusedMagmaItem;

public class Register {
    private static EntityType<Entity> METEORITE;
    public static Item INFUSED_MAGMA;
    public static void RegisterAll() {
        // Items
        INFUSED_MAGMA = registerItem("infused_magma", new InfusedMagmaItem(new Item.Settings()), ItemGroups.COMBAT);


        // Entities
        METEORITE = registerEntity("meteorite", EntityType.Builder.create(MeteoriteEntity::new, SpawnGroup.CREATURE));

    }

    public static Item registerItem(String path, Item item, RegistryKey<ItemGroup> group) {
        return Registry.register(Registries.ITEM, new Identifier(Scatter.MOD_ID, path), item);
    }

    public static EntityType<Entity> registerEntity(String path, EntityType.Builder entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(Scatter.MOD_ID, path), entity.build(path));
    }
}
