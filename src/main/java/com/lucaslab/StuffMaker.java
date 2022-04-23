package com.lucaslab;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.logging.Logger;

public class StuffMaker {
    public static final Logger LOG = Logger.getLogger("Minecraft");


    public static void createChickenSquare(Location spot, int squareWidth, int squareLength) {
        Location firstCorner = spot.clone();
        firstCorner.setX(spot.getX() - squareWidth/2);
        firstCorner.setZ(spot.getZ() - squareLength/2);
        firstCorner.setY(spot.getY() - 2);
        createFilledRectangle(Material.GLOWSTONE, firstCorner, squareWidth, squareLength);
    }

    public static void createFloor(Location towerSpot, int towerWidth, boolean createDoor, int towerHeight) {

        World world = towerSpot.getWorld();

        Location doorSpot = towerSpot.clone();
        doorSpot.setY(towerSpot.getY());
        doorSpot.setX(towerSpot.getX() + towerWidth/2);
        doorSpot.setZ(towerSpot.getZ());

        Location chestSpot = towerSpot.clone();
        chestSpot.setY(chestSpot.getY());
        chestSpot.setX(chestSpot.getX() + towerWidth/2);
        chestSpot.setZ(chestSpot.getZ() + towerWidth - 2);

        Location ladderSpot = towerSpot.clone();
        ladderSpot.setY(chestSpot.getY());
        ladderSpot.setX(chestSpot.getX() -1);
        ladderSpot.setZ(chestSpot.getZ());

        //LOG.info("Tower Location = " + towerSpot + " Door Stop " + doorSpot);
        for(int towerY = 0; towerY < towerHeight; towerY++) {
            if(towerY % 2 == 0 ) {
                createRectangleFrame(Material.PUMPKIN, towerSpot, towerWidth, towerWidth);
            } else {
                createRectangleFrame(Material.PURPLE_STAINED_GLASS, towerSpot, towerWidth, towerWidth);
            }
            towerSpot.setY(towerSpot.getY() + 1);
        }

        createFilledBox(Material.PUMPKIN, towerSpot, towerWidth, 1, towerWidth);
        createRectangleFrame(Material.JACK_O_LANTERN, towerSpot.clone().set(towerSpot.getX() - 1, towerSpot.getY(), towerSpot.getZ() - 1), towerWidth + 2, towerWidth + 2);
        towerSpot.setY(towerSpot.getY() + 1);
        createRectangleFrame(Material.TORCH, towerSpot, towerWidth, towerWidth);


        Random rand = new Random();
        int type = rand.nextInt(12);
        ChestMaker.makeRandomChests(chestSpot, type);

        for(int i = 0; i <= towerHeight ; i++) {
            ladderSpot.getWorld().getBlockAt(ladderSpot.getBlockX(), ladderSpot.getBlockY() + i, ladderSpot.getBlockZ())
                    .setType(Material.ACACIA_PLANKS);
//            ladderSpot.getWorld().getBlockAt(ladderSpot.getBlockX(), ladderSpot.getBlockY() + i, ladderSpot.getBlockZ() -1)
//                    .setType(Material.AIR);
            String blockData = "minecraft:ladder[facing=north,waterlogged=false]";
            BlockData ladderData = Bukkit.createBlockData(blockData);
            ladderSpot.getWorld().getBlockAt(ladderSpot.getBlockX(), ladderSpot.getBlockY() + i, ladderSpot.getBlockZ()).setBlockData(ladderData);
        }

        if(createDoor) {
            putDoor(world, doorSpot, Material.LEGACY_ACACIA_DOOR);
            Location vSpot = doorSpot.clone();
            vSpot.setX( vSpot.getX() -1);
            world.spawnEntity(vSpot, EntityType.VILLAGER);
        }
    }

    public static void putDoor(World world, Location location, Material material) {
        final Block bottom = world.getBlockAt(location);
        final Block top = bottom.getRelative(BlockFace.UP, 1);
        LOG.info("Putting a door at " + bottom.getLocation() + " and " + top.getLocation());
        top.setType(Material.AIR);
        bottom.setType(Material.AIR);
        //String dataString = "minecraft:chest[waterlogged=true]"
        String dataStringBottom = "minecraft:acacia_door[facing=south,half=lower,hinge=right,open=false,powered=false]";
        BlockData dataBottom = Bukkit.createBlockData(dataStringBottom);
        bottom.setBlockData(dataBottom);

        String dataStringTop = "minecraft:acacia_door[facing=south,half=upper,hinge=right,open=false,powered=false]";
        BlockData dataTop = Bukkit.createBlockData(dataStringTop);
        top.setBlockData(dataTop);


//        top.setTypeIdAndData(64, (byte) 0x8, true);
//        bottom.setTypeIdAndData(64, (byte) 0x4, true);
    }

    public static void createFilledBox(Material material, Location startingSpot, int xmax, int ymax, int zmax) {
        World world = startingSpot.getWorld();
        for (int x = 0; x < xmax; x++) {
            for (int y = 0; y < ymax; y++) {
                for (int z = 0; z < zmax; z++) {
                    Location newLocation = startingSpot.clone();
                    newLocation.setX(startingSpot.getX() + x);
                    newLocation.setY(startingSpot.getY() + y);
                    newLocation.setZ(startingSpot.getZ() + z);
                    Block block = world.getBlockAt(newLocation);
                    block.setType(material);
                }
            }
        }
    }

    public static void createRectangleFrame(Material material, Location startingSpot, int xmax, int zmax) {
        World world = startingSpot.getWorld();
        if (world == null) {
            throw new IllegalStateException("World is null");
        }
        //base - at starting z
        for (int x = 0; x < xmax; x++) {
            Location newLocation = startingSpot.clone();
            newLocation.setX(startingSpot.getX() + x);
            // same z location
            Block block = world.getBlockAt(newLocation);
            block.setType(material);
        }
        //right side
        for (int z = 0; z < zmax; z++) {
            Location newLocation = startingSpot.clone();
            // same x location
            newLocation.setZ(startingSpot.getZ() + z);
            Block block = world.getBlockAt(newLocation);
            block.setType(material);
        }
        //left side
        for (int z = 0; z < zmax; z++) {
            Location newLocation = startingSpot.clone();
            // max x side
            newLocation.setX(startingSpot.getX() + xmax - 1);
            newLocation.setZ(startingSpot.getZ() + z);
            Block block = world.getBlockAt(newLocation);
            block.setType(material);
        }
        //top -- at zmax
        for (int x = 0; x < xmax; x++) {
            Location newLocation = startingSpot.clone();
            newLocation.setX(startingSpot.getX() + x);
            newLocation.setZ(startingSpot.getZ() + zmax - 1);
            Block block = world.getBlockAt(newLocation);
            block.setType(material);
        }

    }

    public static void createFilledRectangle(Material material, Location startingSpot, int xmax, int zmax) {
        World world = startingSpot.getWorld();
        for (int x = 0; x < xmax; x++) {
            for (int z = 0; z < zmax; z++) {
                Location newLocation = startingSpot.clone();
                newLocation.setX(startingSpot.getX() + x);
                newLocation.setZ(startingSpot.getZ() + z);
                Block block = world.getBlockAt(newLocation);
                block.setType(material);
            }
        }
    }


    public static void createTower(Location towerSpot, int totalFloors, int towerWidth, int towerHeight) {
        for(int i = 0; i < totalFloors; i++) {
            if(i == 0) {
                StuffMaker.createFloor( towerSpot, towerWidth, true, towerHeight);
            } else {
                StuffMaker.createFloor( towerSpot, towerWidth, false, towerHeight);
            }
        }
    }
    public static void createCherryBlossom(Location treeSpot, int totalBranches) {

        World world = treeSpot.getWorld();
        Block block = world.getBlockAt(treeSpot);
        block.setType(Material.SPRUCE_WOOD  );
        Random rand = new Random();
        Location lastSpot = treeSpot.clone();
        for(int i = 0; i < totalBranches; i++) {
            lastSpot = createCherryBranch(lastSpot,3);


            
            int xpos = rand.nextInt(3);
            int zpos = rand.nextInt(3);
            int ypos = rand.nextInt(3);

            lastSpot.setX(lastSpot.getX()+xpos-1);
            lastSpot.setZ(lastSpot.getZ()+zpos-1);
            lastSpot.setY(lastSpot.getY()+ypos-2);
        }
    }

    public static Location createCherryBranch(Location branchSpot, int totalBlocks){
        World world = branchSpot.getWorld();
        Block block = world.getBlockAt(branchSpot);
        block.setType(Material.SPRUCE_WOOD  );
        for(int i = 0; i < totalBlocks; i++) {
            branchSpot.setY(branchSpot.getY()+1);
            world.getBlockAt(branchSpot).setType(Material.SPRUCE_WOOD  );
        }
        return branchSpot;
    }
    public static void createSprayerCannon(Location cannonSpot, String direction) {
        World world = cannonSpot.getWorld();
        LOG.info("Building a cannon in the direction [" + direction +"]");
        if(direction.equals("E")) {
            createFilledRectangle(Material.BASALT, cannonSpot, 10, 7);
            BlockData leverData = Bukkit.createBlockData("minecraft:lever[face=floor,facing=east,powered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(leverData);

            BlockData comparitorData = Bukkit.createBlockData("minecraft:comparator[facing=west,mode=subtract,powered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+1, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(comparitorData);

            BlockData dispenser1Data = Bukkit.createBlockData("minecraft:dispenser[facing=east,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(dispenser1Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).getState());

            BlockData dispenser2Data = Bukkit.createBlockData("minecraft:dispenser[facing=north,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(dispenser2Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).getState());

            BlockData dispenser3Data = Bukkit.createBlockData("minecraft:dispenser[facing=south,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(dispenser3Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).getState());

            world.getBlockAt(cannonSpot.getBlockX()+8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+9, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+9, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setType(Material.OBSIDIAN);

            world.getBlockAt(cannonSpot.getBlockX()+9, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setType(Material.BLACKSTONE_SLAB);
            world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setType(Material.WATER);

            BlockData redstoneData = Bukkit.createBlockData("minecraft:redstone_wire[east=side,north=side,power=0,south=side,west=side]");
            world.getBlockAt(cannonSpot.getBlockX()+1, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+1).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+1).setBlockData(redstoneData);

            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+5).setBlockData(redstoneData);

        } else if(direction.equals("W")) {
            Location newCannonSpot = cannonSpot.clone();
            newCannonSpot.setX(cannonSpot.getBlockX() - 10);
            createFilledRectangle(Material.BASALT, newCannonSpot, 10, 7);

            BlockData leverData = Bukkit.createBlockData("minecraft:lever[face=floor,facing=west,powered=false]");
            world.getBlockAt(cannonSpot.getBlockX()-1, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(leverData);

            BlockData comparitorData = Bukkit.createBlockData("minecraft:comparator[facing=east,mode=subtract,powered=false]");
            world.getBlockAt(cannonSpot.getBlockX()-2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(comparitorData);

            BlockData dispenser1Data = Bukkit.createBlockData("minecraft:dispenser[facing=west,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()-7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(dispenser1Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()-7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).getState());

            BlockData dispenser2Data = Bukkit.createBlockData("minecraft:dispenser[facing=north,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(dispenser2Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).getState());

            BlockData dispenser3Data = Bukkit.createBlockData("minecraft:dispenser[facing=south,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(dispenser3Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).getState());

            world.getBlockAt(cannonSpot.getBlockX()-9, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()-10, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()-9, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()-10, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setType(Material.OBSIDIAN);

            world.getBlockAt(cannonSpot.getBlockX()-10, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setType(Material.BLACKSTONE_SLAB);
            world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setType(Material.WATER);

            BlockData redstoneData = Bukkit.createBlockData("minecraft:redstone_wire[east=side,north=side,power=0,south=side,west=side]");
            world.getBlockAt(cannonSpot.getBlockX()-2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+1).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+1).setBlockData(redstoneData);

            world.getBlockAt(cannonSpot.getBlockX()-5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-7, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()-8, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+5).setBlockData(redstoneData);

        } else if(direction.equals("N")) {
            Location newCannonSpot = cannonSpot.clone();
            newCannonSpot.setZ(cannonSpot.getBlockZ() - 10);
            createFilledRectangle(Material.BASALT, newCannonSpot, 7, 10);

            BlockData leverData = Bukkit.createBlockData("minecraft:lever[face=floor,facing=north,powered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-1).setBlockData(leverData);

            BlockData comparitorData = Bukkit.createBlockData("minecraft:comparator[facing=south,mode=subtract,powered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-2).setBlockData(comparitorData);

            BlockData dispenser1Data = Bukkit.createBlockData("minecraft:dispenser[facing=north,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-7).setBlockData(dispenser1Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-7).getState());

            BlockData dispenser2Data = Bukkit.createBlockData("minecraft:dispenser[facing=west,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).setBlockData(dispenser2Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).getState());

            BlockData dispenser3Data = Bukkit.createBlockData("minecraft:dispenser[facing=east,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).setBlockData(dispenser3Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).getState());

            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-9).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-10).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-9).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-10).setType(Material.OBSIDIAN);

            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-10).setType(Material.BLACKSTONE_SLAB);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).setType(Material.WATER);

            BlockData redstoneData = Bukkit.createBlockData("minecraft:redstone_wire[east=side,north=side,power=0,south=side,west=side]");
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+1, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-7).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+1, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).setBlockData(redstoneData);

            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-7).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()-8).setBlockData(redstoneData);


        } else if(direction.equals("S")) {
            Location newCannonSpot = cannonSpot.clone();
            createFilledRectangle(Material.BASALT, newCannonSpot, 7, 10);

            BlockData leverData = Bukkit.createBlockData("minecraft:lever[face=floor,facing=south,powered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+0).setBlockData(leverData);

            BlockData comparitorData = Bukkit.createBlockData("minecraft:comparator[facing=north,mode=subtract,powered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+1).setBlockData(comparitorData);

            BlockData dispenser1Data = Bukkit.createBlockData("minecraft:dispenser[facing=south,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(dispenser1Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).getState());

            BlockData dispenser2Data = Bukkit.createBlockData("minecraft:dispenser[facing=west,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).setBlockData(dispenser2Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).getState());

            BlockData dispenser3Data = Bukkit.createBlockData("minecraft:dispenser[facing=east,triggered=false]");
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).setBlockData(dispenser3Data);
            loadDispenser((Dispenser)world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).getState());

            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+8).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+9).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+8).setType(Material.OBSIDIAN);
            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+9).setType(Material.OBSIDIAN);

            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+9).setType(Material.BLACKSTONE_SLAB);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).setType(Material.WATER);

            BlockData redstoneData = Bukkit.createBlockData("minecraft:redstone_wire[east=side,north=side,power=0,south=side,west=side]");
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+1).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+2).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+3).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+3, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+2, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+1, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+0, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+1, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).setBlockData(redstoneData);

            world.getBlockAt(cannonSpot.getBlockX()+4, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+4).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+5).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+6).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+6, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).setBlockData(redstoneData);
            world.getBlockAt(cannonSpot.getBlockX()+5, cannonSpot.getBlockY()+1, cannonSpot.getBlockZ()+7).setBlockData(redstoneData);

        }

    }

    public static void loadDispenser(Dispenser dispenser) {
        Inventory inv = dispenser.getInventory();
        for (int i = 0; i < 1; i++ ) {
            inv.addItem(new ItemStack(Material.TNT, 64));
        }
    }

    public static void createWall(Material material, Location wallSpot, int wallWidth, int wallHeight, boolean eastWest) {
        World world = wallSpot.getWorld();
        if (world == null) {
            throw new IllegalStateException("World is null");
        }
        //base - at starting z
        for(int y = 0; y < wallHeight; y++ ) {
            if(eastWest) {
                for (int x = 0; x < wallWidth; x++) {
                    Location newLocation = wallSpot.clone();
                    newLocation.setX(wallSpot.getX() + x);
                    newLocation.setY(wallSpot.getY() + y);
                    // same z location
                    Block block = world.getBlockAt(newLocation);
                    block.setType(material);
                }
            } else {
                for (int z = 0; z < wallWidth; z++) {
                    Location newLocation = wallSpot.clone();
                    newLocation.setZ(wallSpot.getZ() + z);
                    newLocation.setY(wallSpot.getY() + y);
                    // same z location
                    Block block = world.getBlockAt(newLocation);
                    block.setType(material);
                }
            }
        }
    }
}
