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
import java.util.Random;
import java.util.logging.Logger;

public class SilverFishGatherGoal<T extends Mob> implements Goal {
    public static final Logger LOG = Logger.getLogger("SilverFishGatherGoal");
    private final Plugin plugin;
    private final Mob mob;
    private final Location nest;
    private Location target;
    private Location lastLocation;
    private int stuckCounter = 0;
    public static final int WANDER_DIST = 20;
    public static final int JUMP_DIST = 6;

    boolean hasDirt = false;

    public SilverFishGatherGoal(Plugin plugin, Mob mob, Location nest) {
        this.plugin =plugin;
        this.mob=mob;
        this.nest=nest;
    }
    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public GoalKey getKey() {
        return GoalKey.of(Mob.class, new NamespacedKey(plugin, "BoxyMoveGoal"));
    }

    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET, GoalType.MOVE);
    }

    @Override
    public void tick() {
        if(hasDirt) {
            mob.getPathfinder().moveTo(nest);
            if(closeEnough(mob.getLocation(), nest)) {
                dropDirt();
            }
        } else {
            //Check for a target location
            if(target == null) {
                LOG.info("Picking new target");
                //Pick a random location to go to
                Random rand = new Random();
                target = nest.clone();
                target.setX(target.getX() - WANDER_DIST/2 + rand.nextInt(WANDER_DIST));
                target.setZ(target.getZ() - WANDER_DIST/2 + rand.nextInt(WANDER_DIST));
            }

            if(onDirt(mob.getLocation().clone())) {
                //Take the dirt
                LOG.info("Getting Dirt");
                getDirt();
                this.target = null;

            } else {
                mob.getPathfinder().moveTo(target);
                if(closeEnough(mob.getLocation(), target)) {
                    LOG.info("Nothing here, need new spot");
                    target = null;
                }
            }
        }
        if(stuck(mob.getLocation())) {
            LOG.info("I'm stuck!");
            mob.getLocation().setY(mob.getLocation().getY() + 10);
        } else {
            lastLocation = null;
            stuckCounter =0;
        }
    }
    private void getDirt() {
        Location loc = mob.getLocation();
        loc.setY(loc.getY()-1);
        loc.getBlock().setType(Material.AIR);
        this.hasDirt = true;
    }

    private void dropDirt () {
        LOG.info("Dropping Dirt");
        Location mobLoc = mob.getLocation();
        Location loc = mobLoc.clone();
        loc.setY(loc.getY()+1);
        loc.getBlock().setType(Material.DIRT);
        this.hasDirt = false;

        //Move the bug
        Random rand = new Random();
        mobLoc.setX(mobLoc.getX() - JUMP_DIST/2 + rand.nextInt(JUMP_DIST));
        mobLoc.setZ(mobLoc.getZ() - JUMP_DIST/2 + rand.nextInt(JUMP_DIST));
        mobLoc.setY(mobLoc.getY() + JUMP_DIST/2);
    }

    private boolean closeEnough(Location current, Location target) {
        double distance = current.distance(target);
        if((1/distance) > Math.random()) return true;
        return false;
    }

    private boolean onDirt(Location ground) {
        //Check if it is standing on dirt
        ground.setY(ground.getY() - 1);
        Block block = ground.getBlock();
        LOG.info("On top of " + block.getBlockData().getMaterial().name());
        if(block.getBlockData().getMaterial().equals(Material.DIRT) || block.getBlockData().getMaterial().equals(Material.GRASS_BLOCK) ) {
            return true;
        }
        return false;
    }

    private boolean stuck(Location location) {

        if(lastLocation == null) {
            lastLocation = location;
        } else {
            if(lastLocation.distance(location) == 0) {
                stuckCounter++;
            }
        }
        if(stuckCounter > 10) {
            return true;
        }
        return false;
    }

}
