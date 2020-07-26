package me.thevipershow.betterteleport.commands;

import me.thevipershow.betterteleport.TeleportManager;
import me.thevipershow.betterteleport.configs.Values;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public final class BetterTeleportCommand implements CommandExecutor {
    private final Plugin plugin;
    private final Values values;
    private final TeleportManager teleportManager;

    public BetterTeleportCommand(final Plugin plugin) {
        this.plugin = plugin;
        this.values = Values.getInstance(plugin);
        this.teleportManager = TeleportManager.getInstance(plugin);
        plugin.getServer().getPluginCommand("bt").setExecutor(this);
    }

    public static String color(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void help_1(final CommandSender commandSender) {
        commandSender.sendMessage(color("&8[&cBetterTeleport&8]&7 Commands help page (1):"));
        commandSender.sendMessage(color("&7  Ask a player to teleport to him"));
        commandSender.sendMessage(color("&7> &c/bt go <player>"));
        commandSender.sendMessage(color("&7  Ask a player to teleport to you"));
        commandSender.sendMessage(color("&7> &c/bt come <player>"));
        commandSender.sendMessage(color("&7  Accept a teleport request"));
        commandSender.sendMessage(color("&7> &c/bt accept"));
        commandSender.sendMessage(color("&7  Deny a teleport request"));
        commandSender.sendMessage(color("&7> &c/bt deny"));
        commandSender.sendMessage(color("&7  Deny a teleport request with a message"));
        commandSender.sendMessage(color("&7> &c/bt deny <message>"));
        commandSender.sendMessage(color("&7&lUse &c/bt help 2 &7to continue reading the help page!"));
    }

    public static void help_2(final CommandSender commandSender) {
        commandSender.sendMessage(color("&8[&cBetterTeleport&8]&7 Commands help page (2):"));
        commandSender.sendMessage(color("&7  Ask a player to teleport to him after a delay"));
        commandSender.sendMessage(color("&7> &c/bt go <player> <milliseconds>"));
        commandSender.sendMessage(color("&7  Ask a player to teleport to you after a delay"));
        commandSender.sendMessage(color("&7> &c/bt come <player> <milliseconds>"));
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

        final int count = args.length;

        if (count == 0) {
            help_1(sender); //bt
        } else if (count == 1) {

            final String firstArg = args[0];
            if (firstArg.equalsIgnoreCase("help")) { //bt help
                help_1(sender);
            } else if (firstArg.equalsIgnoreCase("accept")) { //bt accept
                teleportManager.acceptRequest(sender);
            } else if (firstArg.equalsIgnoreCase("deny")) { //bt deny
                teleportManager.denyRequest(sender);
            }

        } else if (count == 2) {
            final String firstArg = args[0];
            final String secondArg = args[1];

            if (firstArg.equalsIgnoreCase("help")) {
                if (secondArg.equals("1")) help_1(sender); //bt help 1
                else if (secondArg.equals("2")) help_2(sender); //bt help 2
            } else if (firstArg.equalsIgnoreCase("go")) {
                teleportManager.goRequest(sender, secondArg);
            } else if (firstArg.equalsIgnoreCase("come")) {
                teleportManager.comeRequest(sender, secondArg);
            }

        }

        return true;
    }
}
