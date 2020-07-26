package me.thevipershow.betterteleport.events;

import me.thevipershow.betterteleport.objects.TeleportRequest;
import org.bukkit.event.Cancellable;

public class TeleportCancelEvent extends TeleportEvent implements Cancellable {

    protected final CancelReason cancelReason;
    protected boolean cancel = false;

    public TeleportCancelEvent(final TeleportRequest teleportRequest, final CancelReason cancelReason) {
        super(teleportRequest);
        this.cancelReason = cancelReason;
    }

    public CancelReason getCancelReason() {
        return cancelReason;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
