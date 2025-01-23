package org.lirox.scatter.events;

import net.minecraft.server.PlayerManager;
import java.util.function.Consumer;

public class Event {
    private final String id;
    private final int tickRate;
    private int remainingRateTicks;
    private int remainingTimeTicks;
    private final Consumer<Event> action;
    private final Consumer<Event> finalAction;

    public Event(String id, int tickRate, int tickTime, Consumer<Event> action, Consumer<Event> finalAction) {
        this.id = id;
        this.tickRate = tickRate;
        this.remainingRateTicks = tickRate;
        this.remainingTimeTicks = tickTime;
        this.action = action;
        this.finalAction = finalAction;
    }

    public String getId() {
        return id;
    }

    public int getTickRate() {
        return tickRate;
    }

    public int getRemainingTime() {
        return remainingTimeTicks;
    }

    public void update(PlayerManager playerManager) {
        remainingRateTicks--;
        remainingTimeTicks--;
        if (remainingRateTicks <= 0) {
            execute(playerManager);
            remainingRateTicks = tickRate;
        }
        if (remainingTimeTicks <= 0) {
            finalExecute(playerManager);
            EventSystem.events.remove(this);
        }
    }

    public void execute(PlayerManager playerManager) {
        action.accept(this);
    }

    public void finalExecute(PlayerManager playerManager) {
        finalAction.accept(this);
    }
}