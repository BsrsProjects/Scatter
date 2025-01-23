package org.lirox.scatter.events;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventRegistry {
    private static final Map<String, Consumer<Event>> actionRegistry = new HashMap<>();
    private static final Map<String, Consumer<Event>> finalActionRegistry = new HashMap<>();

    public static void registerEvent(String id, Consumer<Event> action, Consumer<Event> finalAction) {
        actionRegistry.put(id, action);
        finalActionRegistry.put(id, finalAction);
    }

    public static Consumer<Event> getEventAction(String id) {
        return actionRegistry.get(id);
    }

    public static Consumer<Event> getEventFinalAction(String id) {
        return finalActionRegistry.get(id);
    }

    public static boolean isRegistered(String id) {
        return actionRegistry.containsKey(id);
    }

    public static void initializeDefaultEvents() {
        registerEvent("global_regeneration", event -> {
            PlayerManager playerManager = EventSystem.getPlayerManager();
            for (ServerPlayerEntity player : playerManager.getPlayerList()) {
                player.damage(player.getDamageSources().generic(), 1.0F);
            }
        }, event -> {
            // Example final action logic
        });

        registerEvent("damage_players", event -> {
            // Example action logic
        }, event -> {
            // Example final action logic
        });
    }
}