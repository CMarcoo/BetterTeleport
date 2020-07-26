package me.thevipershow.betterteleport.events;

import me.thevipershow.betterteleport.objects.TeleportRequest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeleportEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    protected final TeleportRequest teleportRequest;

    public TeleportEvent(final TeleportRequest teleportRequest) {
        this.teleportRequest = teleportRequest;
    }

    public TeleportRequest getTeleportRequest() {
        return teleportRequest;
    }
}
