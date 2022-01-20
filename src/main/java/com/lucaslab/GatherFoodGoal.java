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
                woodLocation = lookAroundYouForFood(mob.getLocation(), 1);
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
    public Location lookAroundYouForFood(Location myLoc, int range) {
        LOG.info("Looking around ["+myLoc.getX()+" , "+ myLoc.getY()+" , "+myLoc.getZ()+"]");
        Location checkLoc = myLoc.clone();
        checkLoc.setY(checkLoc.getY() + 1);
        int search = range * 2;
        for(int x = 0; x <= search; x++){
            checkLoc.setX(checkLoc.getX() + x - range);
            for(int z = 0; z <= search; z++){
                checkLoc.setZ(checkLoc.getZ() + z - range);
                LOG.info("checking ["+x+" , "+z+"]");
                Block block = checkLoc.getBlock();
                //LOG.info("checking block at " + x +","+z);
                if(isFood(block)) {
                    LOG.info("food found at " + x +","+z);
                    return checkLoc;
                }
            }
            checkLoc.setZ(myLoc.getZ());
        }

        return null;
    }

    public boolean isFood(Block block){
        //LOG.info("Do I eat " + block.getBlockData().getMaterial() + "? at["+block.getX()+" , "+ block.getY()+" , "+block.getZ()+"]");
        if(block.getBlockData().getMaterial().equals(Material.ACACIA_WOOD)
                || block.getBlockData().getMaterial().equals(Material.BIRCH_WOOD)
                || block.getBlockData().getMaterial().equals(Material.SPRUCE_LOG)
                || block.getBlockData().getMaterial().equals(Material.BEEF)
                || block.getBlockData().getMaterial().equals(Material.DARK_OAK_LOG)
                || block.getBlockData().getMaterial().equals(Material.BIRCH_LOG)
                || block.getBlockData().getMaterial().equals(Material.OAK_LOG)
                || block.getBlockData().getMaterial().equals(Material.DARK_OAK_WOOD)
                || block.getBlockData().getMaterial().equals(Material.OAK_WOOD)
                || block.getBlockData().getMaterial().equals(Material.CHEST))  {
            LOG.info("Yummm" + block.getBlockData().getMaterial() + "!");
            return true;
        }
        return false;
    }
}
