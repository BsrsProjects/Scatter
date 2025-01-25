package org.lirox.scatter;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.lirox.scatter.entities.MeteoriteEntity;
import org.lirox.scatter.items.*;
import org.lirox.scatter.providers.PotionOverlayColorProvider;

public class Register {
    public static EntityType<Entity> METEORITE;
    public static Item INFUSED_MAGMA;
    public static Item EVENT_VESSEL;
    public static Item EVENT_SEALER;
    public static Item POTION_EFFECT_INFUSER;
    public static Item LARGE_POTION;
    public static Item PLAYER_SOUL_SHARD;
    public static Item VENOMFANG;

    public static void RegisterAll() {
        // -- Items
        INFUSED_MAGMA = registerItem("infused_magma", new InfusedMagmaItem(new Item.Settings()), ItemGroups.COMBAT);
        EVENT_VESSEL = registerItem("event_vessel", new EventVesselItem(new Item.Settings()), ItemGroups.COMBAT);
        EVENT_SEALER = registerItem("event_sealer", new EventSealerItem(new Item.Settings()), ItemGroups.COMBAT);
        POTION_EFFECT_INFUSER = registerItem("potion_effect_infuser", new PotionEffectInfuserItem(new Item.Settings().maxCount(1)), ItemGroups.COMBAT);
        LARGE_POTION = registerItem("large_potion", new LargePotionItem(new Item.Settings().maxCount(1)), ItemGroups.FOOD_AND_DRINK);

        // - Weapons
        VENOMFANG = registerItem("venomfang", new VenomfangItem(ToolMaterials.NETHERITE, 2, -2.0F, new Item.Settings()), ItemGroups.COMBAT);

        // Exclusives
        PLAYER_SOUL_SHARD = registerItem("player_soul_shard", new PlayerSoulShardItem(new Item.Settings()), ItemGroups.COMBAT);


        // -

        // Entities
        METEORITE = registerEntity("meteorite", EntityType.Builder.create(MeteoriteEntity::new, SpawnGroup.CREATURE));


        ColorProviderRegistry.ITEM.register(new PotionOverlayColorProvider(), POTION_EFFECT_INFUSER);
        ColorProviderRegistry.ITEM.register(new PotionOverlayColorProvider(), LARGE_POTION);
        ColorProviderRegistry.ITEM.register(new PotionOverlayColorProvider(), VENOMFANG);

    }

    public static Item registerItem(String path, Item item, RegistryKey<ItemGroup> group) {
        return Registry.register(Registries.ITEM, new Identifier(Scatter.MOD_ID, path), item);
    }

    public static EntityType<Entity> registerEntity(String path, EntityType.Builder entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(Scatter.MOD_ID, path), entity.build(path));
    }

//    public static RecipeSerializer<> registerRecipe(String path,) {
//        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Scatter.MOD_ID, path), recipe);
//    }
}
