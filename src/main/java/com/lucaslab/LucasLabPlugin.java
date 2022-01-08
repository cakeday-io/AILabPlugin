package com.lucaslab;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.*;
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


            Location spot = thisPlayer.getLocation().clone();

            Location spawnSpot = spot.clone();
//            spawnSpot.setX(spawnSpot.getX() + 0);
//            clearLocation(spawnSpot);
//            Location goSpot = spot.clone();
//            goSpot.setX(spawnSpot.getX() + 100);
//            clearLocation(goSpot);
//            logSpot(spot);


            if (label.equalsIgnoreCase("lucaslab:test")) {
                LOG.info("Creating Test Jan 8,2022 v3");

                World world = spawnSpot.getWorld();
                Mob newMob = (Mob) world.spawnEntity(spawnSpot, EntityType.SILVERFISH);
                LOG.info("Created a " + newMob.getType() + " with name " + newMob.getName());
                Bukkit.getMobGoals().removeAllGoals(newMob);
                Bukkit.getMobGoals().addGoal(newMob, 1, new SilverFishGatherGoal<Silverfish>(this, newMob, spawnSpot));
                LOG.info("Lucas Lab Test Complete");
            }
        }
        return false;
    }

    private void logSpot(Location spot) {
        LOG.info("Spot at [" + spot.getX() + "," + spot.getY() + "," + spot.getZ() + "]");
    }

    private void clearLocation(Location clearSpot) {
        Location clearLocation = clearSpot.clone();
        clearLocation.setX(clearSpot.getX() - 5);
        clearLocation.setZ(clearSpot.getZ() - 5);
        StuffMaker.createFilledBox(Material.AIR, clearLocation, 10, 10, 10);
    }
}
