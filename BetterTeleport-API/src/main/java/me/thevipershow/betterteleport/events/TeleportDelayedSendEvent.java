package me.thevipershow.betterteleport.events;

import me.thevipershow.betterteleport.objects.TeleportRequest;

public class TeleportDelayedSendEvent extends TeleportSendEvent {

    protected final long requestDelayMillis;
    protected boolean hasRequestStarted = false;

    public TeleportDelayedSendEvent(final TeleportRequest teleportRequest, final RequestType requestType, final long requestDelayMillis) {
        super(teleportRequest, requestType);
        this.requestDelayMillis = requestDelayMillis;
    }

    public long getRequestDelayMillis() {
        return requestDelayMillis;
    }

    public void setHasRequestStarted(final boolean hasRequestStarted) {
        this.hasRequestStarted = hasRequestStarted;
    }
}
