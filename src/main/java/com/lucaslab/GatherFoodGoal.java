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

public class GatherFoodGoal <T extends Mob> implements Goal {
    public static final Logger LOG = Logger.getLogger("GatherFoodGoal");
    private final Plugin plugin;
    private final Mob mob;
    private final Location nest;
    private Location woodLocation;
    private Location wanderLocation;
    private boolean hasWood = false;

    private Location lastLocation;
    private int stuckCounter = 0;

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
                if(woodLocation == null){
                    woodLocation = lookAroundYouForFood(mob.getLocation(), 2);
                }
                if(woodLocation == null){
                    woodLocation = lookAroundYouForFood(mob.getLocation(), 3);
                }
                if(woodLocation == null ){
                    if(wanderLocation == null) {
                        wanderLocation = newWanderSpot(mob.getLocation(),10);
                    } else {
                        mob.getPathfinder().moveTo(wanderLocation);
                    }
                }

            }else {
                mob.getPathfinder().moveTo(woodLocation);
                Block frontBlock = this.blockInFront(mob.getLocation());
                if(isFood(frontBlock)){
                    frontBlock.setType(Material.AIR);
                    this.woodLocation = null;
                }
                Location underBlock = mob.getLocation().clone();
                underBlock.setY(underBlock.getY()-1);
                if(isFood(underBlock.getBlock())){
                    underBlock.getBlock().setType(Material.AIR);
                    this.woodLocation = null;
                }
            }
            if(stuck(mob.getLocation())) {
//                LOG.info("I'm stuck!");
                //Just eat it
                if(this.woodLocation != null){
                    this.woodLocation.getBlock().setType(Material.AIR);
                }

                this.woodLocation = null;
                this.wanderLocation = null;
            }
        }
    }

    /**
     * Gives the bug a place to wander to
     */
    public Location newWanderSpot(Location start, int wanderDist) {
        Location newLocation = start.clone();
        Random rand = new Random();
        newLocation.setX(newLocation.getX() - wanderDist/2 + rand.nextInt(wanderDist));
        newLocation.setZ(newLocation.getZ() - wanderDist/2 + rand.nextInt(wanderDist));

        newLocation = newLocation.toHighestLocation();
        return newLocation;
    }

    /**
     * the bug will look for wood
     *
     * @return returns null if no food is found
     */
    public Location lookAroundYouForFood(Location myLoc, int range) {
//        LOG.info("Looking around ["+myLoc.getX()+" , "+ myLoc.getY()+" , "+myLoc.getZ()+"]");
        Location checkLoc = myLoc.clone();
        checkLoc.setY(checkLoc.getY() - 1);
        int search = range * 2;
        for (int y = 0; y <= 1; y++) {

            for (int x = 0; x <= search; x++) {
                checkLoc.setX(myLoc.getX() + x - range);
                for (int z = 0; z <= search; z++) {
                    checkLoc.setZ(myLoc.getZ() + z - range);
                   // LOG.info("checking [" + x + " , " + z + "] world coordinate [" + checkLoc.getX() + " , " + checkLoc.getZ() + "] y" + checkLoc.getY());
                    Block block = checkLoc.getBlock();
                    //LOG.info("checking block at " + x +","+z);
                    if (isFood(block)) {
                        LOG.info("food found at " + x + "," + z);
                        return checkLoc;
                    }
                }
                checkLoc.setZ(myLoc.getZ());

            }
            checkLoc.setY(checkLoc.getY());
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
                || block.getBlockData().getMaterial().equals(Material.CHEST)
                || block.getBlockData().getMaterial().equals(Material.DARK_OAK_LEAVES)
                || block.getBlockData().getMaterial().equals(Material.JUNGLE_LEAVES)
                || block.getBlockData().getMaterial().equals(Material.OAK_LEAVES)
                || block.getBlockData().getMaterial().equals(Material.SPRUCE_LEAVES)){
            LOG.info("Yummm" + block.getBlockData().getMaterial() + "!");
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
//        LOG.info("Direction =" + facing);
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
        // LOG.info("Block in front is a  =" + block.getBlockData().getMaterial());
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
