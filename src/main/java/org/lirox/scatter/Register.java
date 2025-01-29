package org.lirox.scatter;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.lirox.scatter.blocks.AlchemistsCrucibleBlock;
import org.lirox.scatter.effects.*;
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

    public static Block ALCHEMISTS_CRUCIBLE;

    public static StatusEffect POTION_INSTABILITY;
    public static StatusEffect IGNITED;
    public static StatusEffect DECAY;
    public static StatusEffect ANCHOR;
    public static StatusEffect BOUNCY;
    public static StatusEffect CAT_PAWS;
    public static StatusEffect FROZEN;
    public static StatusEffect LAVA_AFFINITY;

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
//        METEORITE = registerEntity("meteorite", EntityType.Builder.create(MeteoriteEntity::new, SpawnGroup.CREATURE));


        // Blocks
        ALCHEMISTS_CRUCIBLE = registerBlock("alchemists_crucible", new AlchemistsCrucibleBlock(FabricBlockSettings.create()), true);

        // Status effects
        POTION_INSTABILITY = registerStatusEffect("potion_instability", new PotionInstabilityEffect(), 20*60);
        IGNITED = registerStatusEffect("ignited", new IgnitedEffect(), 20*60);
        DECAY = registerStatusEffect("decay", new DecayEffect(), 20*60);
        ANCHOR = registerStatusEffect("anchor", new AnchorEffect(), 20*60);
        BOUNCY = registerStatusEffect("bouncy", new BouncyEffect(), 20*60);
        CAT_PAWS = registerStatusEffect("cat_paws", new CatPawsEffect(), 20*60);
        FROZEN = registerStatusEffect("frozen", new FrozenEffect(), 20*60);
        LAVA_AFFINITY = registerStatusEffect("lava_affinity", new LavaAffinityEffect(), 20*60);

        ColorProviderRegistry.ITEM.register(new PotionOverlayColorProvider(), POTION_EFFECT_INFUSER);
        ColorProviderRegistry.ITEM.register(new PotionOverlayColorProvider(), LARGE_POTION);
        ColorProviderRegistry.ITEM.register(new PotionOverlayColorProvider(), VENOMFANG);
    }

    public static Item registerItem(String path, Item item, RegistryKey<ItemGroup> group) {
        Item registered = Registry.register(Registries.ITEM, new Identifier(Scatter.MOD_ID, path), item);
        addToItemGroup(registered, group);
        return registered;
    }

    public static Block registerBlock(String path, Block block, boolean shouldRegisterItem) {
        Identifier id = new Identifier(Scatter.MOD_ID, path);
        Block registeredBlock = Registry.register(Registries.BLOCK, id, block);

        if (shouldRegisterItem) {
            Item blockItem = new BlockItem(block, new FabricItemSettings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return registeredBlock;
    }
    public static StatusEffect registerStatusEffect(String path, StatusEffect statusEffect) {
        return registerStatusEffect(path, statusEffect, 0);
    }

    public static StatusEffect registerStatusEffect(String path, StatusEffect statusEffect, int registeredPotionDuration) {
        StatusEffect registered = Registry.register(Registries.STATUS_EFFECT, new Identifier(Scatter.MOD_ID, path), statusEffect);

        if (registeredPotionDuration > 0) {
            Potion potion = Registry.register(Registries.POTION, new Identifier(Scatter.MOD_ID, path + "_potion"), new Potion(new StatusEffectInstance(statusEffect, registeredPotionDuration)));
//            addToItemGroup(path + "_potion", potion);
        }

        return registered;
    }

    public static EntityType<Entity> registerEntity(String path, EntityType.Builder entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(Scatter.MOD_ID, path), entity.build(path));
    }

    public static void addToItemGroup(Item item, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> {
            entries.add(item);
        });
    }
}
