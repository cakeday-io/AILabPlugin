package com.lucaslab;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;


import java.util.EnumSet;
import java.util.logging.Logger;

public class GatherFoodGoal <T extends Mob> implements Goal {
    public static final Logger LOG = Logger.getLogger("GatherFoodGoal");
    private final Plugin plugin;
    private final Mob mob;
    private final Location nest;
    private Location woodLocation;
    private boolean hasWood = false;

    public GatherFoodGoal(Plugin plugin, Mob mob, Location nest) {
        this.plugin = plugin;
        this.mob = mob;
        this.nest = nest;
        this.woodLocation = null;
    }

    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public GoalKey getKey() {
        return GoalKey.of(Mob.class, new NamespacedKey(plugin, "GatherFoodGoal"));
    }

    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET, GoalType.MOVE);
    }

    @Override
    public void tick() {
        if(hasWood){
            mob.getPathfinder().moveTo(nest);
        } else {
            if(this.woodLocation == null) {
                woodLocation = lookAroundYouForFood(mob.getLocation(), 2);
            }else {
                mob.getPathfinder().moveTo(woodLocation);
            }

        }

    }

    /**
     * the bug will look for wood
     *
     * @return returns null if no food is found
     */
    private Location lookAroundYouForFood(Location myLoc, int range) {

        Location checkLoc = myLoc.clone();


        for(int x = 0; x < range; x++){
            for(int z = 0; z < range; z++){
                checkLoc.setX(checkLoc.getX() + x);
                checkLoc.setZ(checkLoc.getZ() + z);
                Block block = checkLoc.getBlock();
                if(isFood(block)) {
                    return checkLoc;
                }
            }
        }

        return null;
    }

    private boolean isFood(Block block){
        if(block.getBlockData().getMaterial().equals(Material.ACACIA_WOOD)
                || block.getBlockData().getMaterial().equals(Material.BIRCH_WOOD)
                || block.getBlockData().getMaterial().equals(Material.DARK_OAK_WOOD)
                || block.getBlockData().getMaterial().equals(Material.OAK_WOOD)
                || block.getBlockData().getMaterial().equals(Material.CHEST))  {
            return true;
        }
        return false;
    }
}
