package me.thevipershow.betterteleport.objects;

import me.thevipershow.betterteleport.events.RequestType;
import org.bukkit.entity.Player;

public class TeleportRequest {

    private final long startTime;
    private final long maxAcceptTime;
    private final Player sender;
    private Player target;
    private RequestType requestType;

    private boolean expired = false;

    public TeleportRequest(final long startTime, final long maxAcceptTime, final Player sender, final Player target, final RequestType requestType) {
        this.startTime = startTime;
        this.maxAcceptTime = maxAcceptTime;
        this.sender = sender;
        this.target = target;
        this.requestType = requestType;
    }

    public long getStartTime() {
        return startTime;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(final RequestType requestType) {
        this.requestType = requestType;
    }

    public boolean isExpired() {
        return expired;
    }

    public long getMaxAcceptTime() {
        return maxAcceptTime;
    }

    public Player getSender() {
        return sender;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(final Player target) {
        this.target = target;
    }

    public void setExpired(final boolean expired) {
        this.expired = expired;
    }
}
