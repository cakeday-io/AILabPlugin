package com.lucaslab;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public class LucasLabPlugin extends JavaPlugin {
    public static final Logger LOG = Logger.getLogger("LucasLabPlugin");

    @Override
    public void onDisable() {
        // Don't log disabling, Spigot does that for you automatically!
    }

    @Override
    public void onEnable() {
        // Don't log enabling, Spigot does that for you automatically!
        PluginManager pluginManager = getServer().getPluginManager();

        //Do all setup in here
    }

    public boolean onCommand(CommandSender sender,
                             Command command, String label, String[] arguments) {

        LOG.info("Command Called: " + label);
        if (sender instanceof Player) {
            Player me = (Player) sender;


            Location spot = me.getWorld().getSpawnLocation().clone();
            logSpot(spot);

            if (label.equalsIgnoreCase("lucaslab:test")) {
                LOG.info("Lucas Lab Test Complete");
            }
        }
        return false;
    }

    private void logSpot(Location spot) {
        LOG.info("Spot at [" + spot.getX() + "," + spot.getY() + "," + spot.getZ() + "]");
    }
}
