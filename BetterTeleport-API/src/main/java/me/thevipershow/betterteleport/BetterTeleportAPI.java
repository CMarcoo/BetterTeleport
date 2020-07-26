package me.thevipershow.betterteleport;

import org.bukkit.Bukkit;

public final class BetterTeleportAPI {
    private static BetterTeleportAPI instance = null;

    private BetterTeleportAPI() {
    }

    public static synchronized BetterTeleportAPI getInstance() {
        if (instance == null) instance = new BetterTeleportAPI();
        return instance;
    }

    public boolean isBetterTeleportEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("BetterTeleport");
    }
}
