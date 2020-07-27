package me.thevipershow.betterteleport;

import me.thevipershow.betterteleport.commands.BetterTeleportCommand;
import me.thevipershow.betterteleport.configs.Values;
import me.thevipershow.betterteleport.events.*;
import me.thevipershow.betterteleport.objects.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

public final class TeleportManager {
    private final Plugin plugin;
    private final Values values;
    private static TeleportManager instance = null;

    private TeleportManager(final Plugin plugin) {
        this.plugin = plugin;
        values = Values.getInstance(plugin);
        teleportRequests = new HashMap<>();
    }

    public static TeleportManager getInstance(final Plugin plugin) {
        if (instance == null) instance = new TeleportManager(plugin);
        return instance;
    }

    private final HashMap<UUID, TeleportRequest> teleportRequests;

    public static void invalidSenderMessage(final CommandSender sender, final Values values) {
        sender.sendMessage(BetterTeleportCommand.color(values.getInvalidSenderMessage()));
    }

    public static void invalidTargetMessage(final Player sender, final String target, final Values values) {
        sender.sendMessage(BetterTeleportCommand.color(values.getInvalidTargetMessage().replace("{PLAYER}", target)));
    }

    public static void sendGoRequestMessage(final Player target, final String senderName, final long time, final Values values) {
        if (!target.isOnline()) {
            final Player player = Bukkit.getPlayer(senderName);
            if (player == null) return;
            player.sendMessage(BetterTeleportCommand.color(values.getTargetQuitDuringDelayedRequest().replace("{PLAYER}", target.getName())));
            return;
        }
        values.getTeleportToOtherRequestMessage().forEach(message -> {
            target.sendMessage(BetterTeleportCommand.color(message.replace("{SENDER}", senderName).replace("{REQUEST_TIME}", String.valueOf(time / 1000))));
        });
    }

    public static void sendComeRequestMessage(final Player target, final String senderName, final long time, final Values values) {
        if (!target.isOnline()) {
            final Player player = Bukkit.getPlayer(senderName);
            if (player == null) return;
            player.sendMessage(BetterTeleportCommand.color(values.getTargetQuitDuringDelayedRequest().replace("{PLAYER}", target.getName())));
            return;
        }
        values.getTeleportToSelfRequestMessage().forEach(message -> {
            target.sendMessage(BetterTeleportCommand.color(message.replace("{SENDER}", senderName).replace("{REQUEST_TIME}", String.valueOf(time / 1000))));
        });
    }

    public final void goRequest(final CommandSender sender, final String targetArgument) {
        if (!(sender instanceof Player)) {
            invalidSenderMessage(sender, values);
            return;
        }

        final Player playerSender = (Player) sender;
        if (teleportToSelf(playerSender, targetArgument, values)) return;
        final Player target = plugin.getServer().getPlayer(targetArgument);
        if (target == null) {
            invalidTargetMessage(playerSender, targetArgument, values);
            return;
        }

        final TeleportRequest teleportRequest = new TeleportRequest(currentTimeMillis(), values.getTeleportToOtherRequestTime(), playerSender, target, RequestType.OTHER);
        final TeleportSendEvent teleportSendEvent = new TeleportSendEvent(teleportRequest, RequestType.OTHER);
        plugin.getServer().getPluginManager().callEvent(teleportSendEvent);

        if (teleportSendEvent.isCancelled()) return; // Some third party plugin cancelled the request ^

        sendGoRequestMessage(target, playerSender.getName(), teleportRequest.getMaxAcceptTime(), values);
        sender.sendMessage(BetterTeleportCommand.color(values.getRequestSent().replace("{PLAYER}", targetArgument)));
        teleportRequests.put(target.getUniqueId(), teleportRequest);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> teleportRequests.remove(target.getUniqueId()), teleportRequest.getMaxAcceptTime() / 1000 * 20);
    }

    public final void goDelayedRequest(final CommandSender sender, final String targetArgument, final long delay) {
        if (!(sender instanceof Player)) {
            invalidSenderMessage(sender, values);
            return;
        }

        final Player playerSender = (Player) sender;
        if (teleportToSelf(playerSender, targetArgument, values)) return;
        final Player target = plugin.getServer().getPlayer(targetArgument);
        if (target == null) {
            invalidTargetMessage(playerSender, targetArgument, values);
            return;
        }

        final TeleportRequest teleportRequest = new TeleportRequest(currentTimeMillis(), values.getTeleportToOtherRequestTime(), playerSender, target, RequestType.OTHER);
        final TeleportDelayedSendEvent teleportDelayedSendEvent = new TeleportDelayedSendEvent(teleportRequest, RequestType.OTHER, delay);
        plugin.getServer().getPluginManager().callEvent(teleportDelayedSendEvent);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (!teleportDelayedSendEvent.isCancelled()) {
                sendGoRequestMessage(target, playerSender.getName(), teleportRequest.getMaxAcceptTime(), values);
                sender.sendMessage(BetterTeleportCommand.color(values.getRequestSent().replace("{PLAYER}", targetArgument)));
                teleportRequests.put(target.getUniqueId(), teleportRequest);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> teleportRequests.remove(target.getUniqueId()), teleportRequest.getMaxAcceptTime() / 1000 * 20);
            }
        }, delay / 1000 * 20);
    }

    public final void comeRequest(final CommandSender sender, final String targetArgument) {
        if (!(sender instanceof Player)) {
            invalidSenderMessage(sender, values);
            return;
        }

        final Player playerSender = (Player) sender;
        if (teleportToSelf(playerSender, targetArgument, values)) return;
        final Player target = plugin.getServer().getPlayer(targetArgument);
        if (target == null) {
            invalidTargetMessage(playerSender, targetArgument, values);
            return;
        }

        final TeleportRequest teleportRequest = new TeleportRequest(currentTimeMillis(), values.getTeleportToOtherRequestTime(), playerSender, target, RequestType.SELF);
        final TeleportSendEvent teleportSendEvent = new TeleportSendEvent(teleportRequest, RequestType.SELF);
        plugin.getServer().getPluginManager().callEvent(teleportSendEvent);

        if (teleportSendEvent.isCancelled()) return;

        sendComeRequestMessage(target, playerSender.getName(), teleportRequest.getMaxAcceptTime(), values);
        sender.sendMessage(BetterTeleportCommand.color(values.getRequestSent().replace("{PLAYER}", targetArgument)));
        teleportRequests.put(target.getUniqueId(), teleportRequest);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> teleportRequests.remove(target.getUniqueId()), teleportRequest.getMaxAcceptTime() / 1000 * 20);
    }

    public static boolean teleportToSelf(final Player player, final String targetName, final Values values) {
        if (player.getName().equals(targetName)) {
            player.sendMessage(BetterTeleportCommand.color(values.getTeleportToSelfError()));
            return true;
        }
        return false;
    }

    public final void comeDelayedRequest(final CommandSender sender, final String targetArgument, final long delay) {
        if (!(sender instanceof Player)) {
            invalidSenderMessage(sender, values);
            return;
        }

        final Player playerSender = (Player) sender;
        if (teleportToSelf(playerSender, targetArgument, values)) return;
        final Player target = plugin.getServer().getPlayer(targetArgument);
        if (target == null) {
            invalidTargetMessage(playerSender, targetArgument, values);
            return;
        }

        final TeleportRequest teleportRequest = new TeleportRequest(currentTimeMillis(), values.getTeleportToOtherRequestTime(), playerSender, target, RequestType.SELF);
        final TeleportDelayedSendEvent teleportDelayedSendEvent = new TeleportDelayedSendEvent(teleportRequest, RequestType.SELF, delay);
        plugin.getServer().getPluginManager().callEvent(teleportDelayedSendEvent);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (!teleportDelayedSendEvent.isCancelled()) {
                sendGoRequestMessage(target, playerSender.getName(), teleportRequest.getMaxAcceptTime(), values);
                sender.sendMessage(BetterTeleportCommand.color(values.getRequestSent().replace("{PLAYER}", targetArgument)));
                teleportRequests.put(target.getUniqueId(), teleportRequest);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> teleportRequests.remove(target.getUniqueId()), teleportRequest.getMaxAcceptTime() / 1000 * 20);
            }
        }, delay / 1000 * 20);
    }

    public Optional<TeleportRequest> lastRequestMatching(final Player target) {
        final TeleportRequest teleportRequest = teleportRequests.get(target.getUniqueId());
        return teleportRequest == null ? Optional.empty() : Optional.of(teleportRequest);
    }

    public final void acceptRequest(final CommandSender sender) {
        if (!(sender instanceof Player)) {
            invalidSenderMessage(sender, values);
            return;
        }

        final Player playerSender = (Player) sender;
        final Optional<TeleportRequest> lastRequestMatching = lastRequestMatching(playerSender);
        if (!lastRequestMatching.isPresent()) {
            playerSender.sendMessage(BetterTeleportCommand.color(values.getNoPendingTeleportRequests()));
            return;
        }

        final TeleportRequest teleportRequest = lastRequestMatching.get(); // safe
        if (currentTimeMillis() - teleportRequest.getStartTime() <= teleportRequest.getMaxAcceptTime()) {
            final Player originalSender = teleportRequest.getSender();
            if (!originalSender.isOnline()) {
                playerSender.sendMessage(BetterTeleportCommand.color(values.getSenderQuitDuringRequest()));
                return;
            }

            final TeleportAcceptEvent teleportAcceptEvent = new TeleportAcceptEvent(teleportRequest);
            plugin.getServer().getPluginManager().callEvent(teleportAcceptEvent);

            if (teleportAcceptEvent.isCancelled()) return;

            playerSender.sendMessage(BetterTeleportCommand.color(values.getRequestAccepted().replace("{REPLACE}", teleportRequest.getSender().getName())));
            if (teleportRequest.getRequestType() == RequestType.OTHER) {
                originalSender.teleport(playerSender, PlayerTeleportEvent.TeleportCause.PLUGIN);
            } else if (teleportRequest.getRequestType() == RequestType.SELF) {
                playerSender.teleport(originalSender, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
            teleportRequests.remove(playerSender.getUniqueId());
        } else {
            playerSender.sendMessage(BetterTeleportCommand.color(values.getRequestTimeExpired()));
        }

    }

    public final void denyRequest(final CommandSender sender) {
        if (!(sender instanceof Player)) {
            invalidSenderMessage(sender, values);
            return;
        }

        final Player playerSender = (Player) sender;
        final Optional<TeleportRequest> lastRequestMatching = lastRequestMatching(playerSender);
        if (!lastRequestMatching.isPresent()) {
            playerSender.sendMessage(BetterTeleportCommand.color(values.getNoPendingTeleportRequests()));
            return;
        }

        final TeleportCancelEvent teleportCancelEvent = new TeleportCancelEvent(lastRequestMatching.get(), CancelReason.PLAYER_DENY);
        plugin.getServer().getPluginManager().callEvent(teleportCancelEvent);

        if (teleportCancelEvent.isCancelled()) return;

        teleportRequests.remove(playerSender.getUniqueId());
        playerSender.sendMessage(BetterTeleportCommand.color(values.getRequestDenied()));
    }

}
