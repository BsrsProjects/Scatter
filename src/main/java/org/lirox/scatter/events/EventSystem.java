package org.lirox.scatter.events;

import net.minecraft.server.PlayerManager;
import java.util.ArrayList;
import java.util.List;

public class EventSystem {
    public static final List<Event> events = new ArrayList<>();

    private static PlayerManager playerManager;

    public EventSystem(PlayerManager playerManager) {
        EventSystem.playerManager = playerManager;
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void add(Event event) {
        events.add(event);
    }

    public boolean generate(String id, int tickRate, int tickTime) {
        if (EventRegistry.isRegistered(id)) {
            events.add(new Event(id, tickRate, tickTime, EventRegistry.getEventAction(id), EventRegistry.getEventFinalAction(id)));
            return true;
        } else {
            System.err.println("Event with ID '" + id + "' is not registered!");
            return false;
        }
    }

    public void tick() {
        events.removeIf(event -> {
            event.update(playerManager);
            return event.getRemainingTime() <= 0;
        });
    }
}
