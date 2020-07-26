package me.thevipershow.betterteleport.events;

import me.thevipershow.betterteleport.objects.TeleportRequest;
import org.bukkit.event.Cancellable;

public class TeleportAcceptEvent extends TeleportEvent implements Cancellable {

    protected boolean cancel = false;

    public TeleportAcceptEvent(final TeleportRequest teleportRequest) {
        super(teleportRequest);
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
