package me.thevipershow.betterteleport;

import me.thevipershow.betterteleport.commands.BetterTeleportCommand;
import me.thevipershow.betterteleport.configs.Values;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterTeleport extends JavaPlugin {

    @Override
    public void onEnable() { // Plugin startup logic
        saveDefaultConfig();
        final Values values = Values.getInstance(this);
        values.updateValues();

        new BetterTeleportCommand(this);
    }
}
