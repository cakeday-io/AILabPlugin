package com.lucaslab;

import com.ticxo.modelengine.ModelEngine;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.generator.blueprint.BlueprintBone;
import com.ticxo.modelengine.api.generator.blueprint.ModelBlueprint;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
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


import java.util.Iterator;
import java.util.Map;
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
                LOG.info("Creating Test Jan 17,2022 v1");

                World world = spawnSpot.getWorld();

                //This code loads the blueprint and tells you all the bones
//                ModelBlueprint blueprint = ModelEngine.getModelBlueprint("termite");
//
//                //ModelEngineAPI.getModelManager().getModelRegistry()
//                if(blueprint != null) {
//                    Map<String, BlueprintBone> bones = blueprint.getBones();
//                    Iterator iter = bones.keySet().iterator();
//                    while(iter.hasNext()) {
//                        Object key = iter.next();
//                        Object value = bones.get(key);
//                        LOG.info("Got Bone Key[" + key + "] Value [" + value.toString() + "]");
//                    }
//                }

                ActiveModel activeModel = ModelEngine.createActiveModel("termite");
                if(activeModel != null) {
                    LOG.info("Loaded active model [" + activeModel.getModelId() + "]");
                } else {
                    LOG.info("Model was null, no termite.bbmodel file in the /blueprints folder");
                }

                Mob newMob = (Mob) world.spawnEntity(spawnSpot, EntityType.SILVERFISH);
                ModeledEntity modeledEntity = ModelEngine.createModeledEntity(newMob);
                modeledEntity.addActiveModel(activeModel);

                LOG.info("Created a " + newMob.getType() + " with name " + newMob.getName());
                Bukkit.getMobGoals().removeAllGoals(newMob);
                Bukkit.getMobGoals().addGoal(newMob, 1, new AttackGoal<Silverfish>(this, newMob, spawnSpot));
                Bukkit.getMobGoals().addGoal(newMob, 2, new GatherFoodGoal<Silverfish>(this, newMob, spawnSpot));
                Bukkit.getMobGoals().addGoal(newMob, 3, new MakeNestGoal<Silverfish>(this, newMob, spawnSpot));
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
