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
 //           mob.getPathfinder().moveTo(nest);
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


            Block block = blockInFront(mob.getLocation());
            if(block.getBlockData().getMaterial().equals(Material.DIRT) || block.getBlockData().getMaterial().equals(Material.GRASS_BLOCK) ) {
                //Take the dirt
                LOG.info("Getting Dirt");
                block.setType(Material.AIR);
                this.hasDirt = true;
                this.target = null;
//            } else {
//                mob.getPathfinder().moveTo(target);
//                if(closeEnough(mob.getLocation(), target)) {
//                    LOG.info("Nothing here, need new spot");
//                    target = null;
//                }
            }


//            if(onDirt(mob.getLocation().clone())) {
//                if(farEnough(mob.getLocation(), nest)) {
//                    //Take the dirt
//                    LOG.info("Getting Dirt");
//                    Location loc = mob.getLocation();
//                    getDirt(loc.clone());
//
//                    this.target = null;
//                }
//            } else {
//                if(onStone(mob.getLocation().clone())) {
//                    if(dirtInFront(mob.getLocation())) {
//                        LOG.info("Dirt in front");
//                    }
//                } else if(onWater(mob.getLocation().clone())) {
//                    //give it more life
//                    LOG.info("Drinking Water");
//                    mob.setHealth(8);
//                } else {
//                    mob.getPathfinder().moveTo(target);
//                    if(closeEnough(mob.getLocation(), target)) {
//                        LOG.info("Nothing here, need new spot");
//                        target = null;
//                    }
//                }
//            }
        }
        //This causes the mob to teleport and hurt itself
//        if(stuck(mob.getLocation())) {
//            LOG.info("I'm stuck!");
//            climbOut();
//        }
       // LOG.info("Health At " + mob.getHealth());
    }
    private void getDirtUnder(Location loc) {
        loc.setY(loc.getY()-1);
        loc.getBlock().setType(Material.AIR);
        this.hasDirt = true;
    }



    private void dropDirt () {
        LOG.info("Dropping Dirt");
        Location mobLoc = mob.getLocation();
        Location loc = mobLoc.toHighestLocation();
        if(loc.getY() + 1 == mobLoc.getY()) {
            LOG.info("On Top of Mob");
            loc.setY(loc.getY() + 2);
        } else {
            loc.setY(loc.getY() + 1);
        }

        loc.getBlock().setType(Material.DIRT);
        this.hasDirt = false;
    }

    private void climbOut() {
        Location loc = mob.getLocation().toHighestLocation();
        //Move the bug
        boolean stillStuck = true;
        while(stillStuck == true) {
            Random rand = new Random();
            loc.setX(loc.getX() - JUMP_DIST/2 + rand.nextInt(JUMP_DIST));
            loc.setZ(loc.getZ() - JUMP_DIST/2 + rand.nextInt(JUMP_DIST));
//            loc.setY(loc.getY() + JUMP_DIST/2);
            if(loc.getBlock().getBlockData().getMaterial().equals(Material.AIR)) {
                mob.teleport(loc);
                stillStuck = false;
            }
        }
    }

    private boolean closeEnough(Location current, Location target) {
        double distance = current.distance(target);
        if((1/distance) > Math.random()) return true;
        return false;
    }

    private boolean farEnough(Location current, Location target) {
        double distance = current.distance(target);
        if((1/distance) < Math.random()) return true;
        return false;
    }



    private boolean onStone(Location ground) {
        //Check if it is standing on dirt
        ground.setY(ground.getY() - 1);
        Block block = ground.getBlock();
//        LOG.info("On top of " + block.getBlockData().getMaterial().name());
        if(block.getBlockData().getMaterial().equals(Material.STONE) ) {
            return true;
        }
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

    private boolean onWater(Location ground) {
        //Check if it is standing on dirt
        ground.setY(ground.getY() - 1);
        Block block = ground.getBlock();
//        LOG.info("On top of " + block.getBlockData().getMaterial().name());
        if(block.getBlockData().getMaterial().equals(Material.WATER)) {
            return true;
        }
        return false;
    }

    private Block blockInFront(Location loc) {
        //North is toward -Z
        //West is toward -X
        //South is toward Z
        //East is toward X

//        for(int i)
        BlockFacing facing = BlockFacing.yawToFace(loc.getYaw(), false);
        LOG.info("Direction =" + facing);
        Location locFront = loc.clone();
        if(facing.equals(BlockFacing.NORTH)) {
            locFront.setZ(loc.getZ() - 1);
        } else if (facing.equals(BlockFacing.EAST)) {
            locFront.setX(loc.getX() + 1 );
        } else if (facing.equals(BlockFacing.SOUTH)) {
            locFront.setZ(loc.getZ() + 1 );
        } else if (facing.equals(BlockFacing.WEST)) {
            locFront.setX(loc.getX() - 1 );
        }

        Block block = locFront.getBlock();
        LOG.info("Block in front is a  =" + block.getBlockData().getMaterial());
        return block;
//        if(block.getBlockData().getMaterial().equals(Material.DIRT) || block.getBlockData().getMaterial().equals(Material.GRASS_BLOCK) ) {
//            return true;
//        }
//        return false;
    }

    private boolean stuck(Location location) {

        if(lastLocation == null) {
            lastLocation = location.clone();
//            LOG.info("Stuck : No last location =" + lastLocation.distance(location));
        } else {
//            LOG.info("Stuck Distance =" + lastLocation.distance(location));
            if(lastLocation.distance(location) == 0) {
                stuckCounter++;
            } else {
                //not stuck
                lastLocation = location.clone();
                stuckCounter = 0;
            }
        }
        if(stuckCounter > 3) {
            stuckCounter = 0;
            return true;
//        } else {
//            LOG.info("Stuck Count =" + stuckCounter);
        }
        return false;
    }

}
