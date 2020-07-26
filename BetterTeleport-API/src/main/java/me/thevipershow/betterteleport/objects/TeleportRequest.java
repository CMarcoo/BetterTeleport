package me.thevipershow.betterteleport.objects;

import org.bukkit.entity.Player;

public class TeleportRequest {

    private final long startTime;
    private final long maxAcceptTime;
    private final Player sender;
    private Player target;

    private boolean expired = false;

    public TeleportRequest(final long startTime, final long maxAcceptTime, final Player sender, final Player target) {
        this.startTime = startTime;
        this.maxAcceptTime = maxAcceptTime;
        this.sender = sender;
        this.target = target;
    }

    public long getStartTime() {
        return startTime;
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
