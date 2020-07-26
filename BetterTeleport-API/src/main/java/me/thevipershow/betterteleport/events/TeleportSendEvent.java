package me.thevipershow.betterteleport.events;

import me.thevipershow.betterteleport.objects.TeleportRequest;
import org.bukkit.event.Cancellable;

public class TeleportSendEvent extends TeleportEvent implements Cancellable {

    private boolean cancelled = false;
    private final RequestType requestType;

    public TeleportSendEvent(final TeleportRequest teleportRequest, final RequestType requestType) {
        super(teleportRequest);
        this.requestType = requestType;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
