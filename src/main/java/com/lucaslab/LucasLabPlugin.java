package com.lucaslab;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Mob;
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
            Player thisPlayer = (Player) sender;


            Location spot = thisPlayer.getWorld().getSpawnLocation().clone();
            Location spawnSpot = spot.clone();
            spawnSpot.setX(spawnSpot.getX() + 20);
            Location goSpot = spot.clone();
            spawnSpot.setX(spawnSpot.getX() + 100);

            logSpot(spot);


            if (label.equalsIgnoreCase("lucaslab:test")) {
                LOG.info("Creating Giant");

                World world = spawnSpot.getWorld();
                Mob waveMember = (Mob) world.spawnEntity(spawnSpot, EntityType.GIANT);
                Bukkit.getMobGoals().addGoal(waveMember, 1, new BoxyMoveGoal<Giant>(this, waveMember, goSpot));
                LOG.info("Lucas Lab Test Complete");
            }
        }
        return false;
    }

    private void logSpot(Location spot) {
        LOG.info("Spot at [" + spot.getX() + "," + spot.getY() + "," + spot.getZ() + "]");
    }
}
