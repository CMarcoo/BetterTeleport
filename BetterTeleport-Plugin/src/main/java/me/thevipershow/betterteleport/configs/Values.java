package me.thevipershow.betterteleport.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public final class Values {
    private static Values instance = null;

    private final Plugin plugin;

    private Values(final Plugin plugin) {
        this.plugin = plugin;
    }

    public static Values getInstance(final Plugin plugin) {
        if (instance == null) instance = new Values(plugin);
        return instance;
    }

    private final static String BASE_NODE = "teleport.";
    private final static String REQUEST_TEXT_NODE = "clickable-tpa-request-text.";

    private String teleportToOtherPermission = null;
    private String teleportToSelfPermission = null;
    private String teleportToOtherDenyMessage = null;
    private String teleportToSelfDenyMessage = null;
    private List<String> teleportToOtherRequestMessage = null;
    private List<String> teleportToSelfRequestMessage = null;
    private Long teleportToOtherRequestTime = null;
    private Long teleportToSelfRequestTime = null;
    private Boolean appendableClickableTeleportRequest = null;
    private String appendableClickableTeleportRequestAcceptMessage = null;
    private String appendableClickableTeleportRequestDenyMessage = null;
    private String invalidTargetMessage = null;
    private String invalidSenderMessage = null;
    private String targetQuitDuringDelayedRequest = null;
    private String teleportToSelfError = null;
    private String noPendingTeleportRequests = null;
    private String requestTimeExpired = null;
    private String senderQuitDuringRequest = null;
    private String requestAccepted = null;
    private String requestDenied = null;
    private String tpaDenyDenyMessage = null;
    private String tpaDenyPermission = null;
    private String adminOnlyCommand = null;
    private String requestSent = null;

    public final void updateValues() {
        plugin.reloadConfig();
        final FileConfiguration c = plugin.getConfig();
        teleportToOtherPermission = c.getString(BASE_NODE + "tpa-to-other-permission");
        teleportToSelfPermission = c.getString(BASE_NODE + "tpa-to-self-permission");
        teleportToOtherDenyMessage = c.getString(BASE_NODE + "tpa-to-other-deny-message");
        teleportToSelfDenyMessage = c.getString(BASE_NODE + "tpa-to-self-deny-message");
        teleportToOtherRequestMessage = c.getStringList(BASE_NODE + "tpa-to-other-message");
        teleportToSelfRequestMessage = c.getStringList(BASE_NODE + "tpa-to-self-message");
        teleportToOtherRequestTime = c.getLong(BASE_NODE + "tpa-to-other-time");
        teleportToSelfRequestTime = c.getLong(BASE_NODE + "tpa-to-self-time");
        appendableClickableTeleportRequest = c.getBoolean(BASE_NODE + "append-clickable-tpa-request");
        appendableClickableTeleportRequestAcceptMessage = c.getString(BASE_NODE + REQUEST_TEXT_NODE + "accept-message");
        appendableClickableTeleportRequestDenyMessage = c.getString(BASE_NODE + REQUEST_TEXT_NODE + "deny-message");
        invalidTargetMessage = c.getString(BASE_NODE + "invalid-target-message");
        invalidSenderMessage = c.getString(BASE_NODE + "invalid-sender-message");
        targetQuitDuringDelayedRequest = c.getString(BASE_NODE + "target-quit-during-delayed-request");
        teleportToSelfError = c.getString(BASE_NODE + "teleport-to-self");
        noPendingTeleportRequests = c.getString(BASE_NODE + "no-pending-requests");
        requestTimeExpired = c.getString(BASE_NODE + "request-time-exceeded");
        senderQuitDuringRequest = c.getString(BASE_NODE + "sender-quit-during-request");
        requestAccepted = c.getString(BASE_NODE + "request-accepted");
        requestDenied = c.getString(BASE_NODE + "request-denied");
        tpaDenyDenyMessage = c.getString(BASE_NODE + "tpa-deny-deny-message");
        tpaDenyPermission = c.getString(BASE_NODE + "tpa-deny-permission");
        adminOnlyCommand = c.getString(BASE_NODE + "admin-only-command");
        requestSent = c.getString(BASE_NODE + "request-sent");
    }

    public String getTeleportToOtherPermission() {
        return teleportToOtherPermission;
    }

    public String getTeleportToSelfPermission() {
        return teleportToSelfPermission;
    }

    public String getTeleportToOtherDenyMessage() {
        return teleportToOtherDenyMessage;
    }

    public String getTeleportToSelfDenyMessage() {
        return teleportToSelfDenyMessage;
    }

    public List<String> getTeleportToOtherRequestMessage() {
        return teleportToOtherRequestMessage;
    }

    public List<String> getTeleportToSelfRequestMessage() {
        return teleportToSelfRequestMessage;
    }

    public Long getTeleportToOtherRequestTime() {
        return teleportToOtherRequestTime;
    }

    public Long getTeleportToSelfRequestTime() {
        return teleportToSelfRequestTime;
    }

    public Boolean getAppendableClickableTeleportRequest() {
        return appendableClickableTeleportRequest;
    }

    public String getAppendableClickableTeleportRequestAcceptMessage() {
        return appendableClickableTeleportRequestAcceptMessage;
    }

    public String getAppendableClickableTeleportRequestDenyMessage() {
        return appendableClickableTeleportRequestDenyMessage;
    }

    public String getInvalidTargetMessage() {
        return invalidTargetMessage;
    }

    public String getInvalidSenderMessage() {
        return invalidSenderMessage;
    }

    public String getTargetQuitDuringDelayedRequest() {
        return targetQuitDuringDelayedRequest;
    }

    public String getTeleportToSelfError() {
        return teleportToSelfError;
    }

    public String getNoPendingTeleportRequests() {
        return noPendingTeleportRequests;
    }

    public String getRequestTimeExpired() {
        return requestTimeExpired;
    }

    public String getSenderQuitDuringRequest() {
        return senderQuitDuringRequest;
    }

    public String getRequestAccepted() {
        return requestAccepted;
    }

    public String getRequestDenied() {
        return requestDenied;
    }

    public String getTpaDenyDenyMessage() {
        return tpaDenyDenyMessage;
    }

    public String getTpaDenyPermission() {
        return tpaDenyPermission;
    }

    public String getAdminOnlyCommand() {
        return adminOnlyCommand;
    }

    public String getRequestSent() {
        return requestSent;
    }
}
