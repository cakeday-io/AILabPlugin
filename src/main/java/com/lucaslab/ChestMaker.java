package com.lucaslab;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Random;
import java.util.logging.Logger;

public class ChestMaker {

    public static final Logger LOG = Logger.getLogger("ChestMaker");

    public static void makeRandomChests( Location spot, int type) {

        switch (type) {
            case 0: {
                makeUtilityChest(spot.getBlock());
                break;
            }
            case 1: {
                makeBrewingChest(spot.getBlock());
                break;
            }
            case 2: {
                makeWeaponChest(spot.getBlock());
                break;
            }
            case 3: {
                makeChestOfBooks(spot.getBlock(), Enchantment.ARROW_DAMAGE);
                break;
            }
            case 4: {
                makeChestOfBooks(spot.getBlock(), Enchantment.PROTECTION_ENVIRONMENTAL);
                break;
            }
            case 5: {
                makePorkChest(spot.getBlock());
                break;
            }
            case 6: {
                makeWeaponChest(spot.getBlock());
                break;
            }
            case 7: {
                makeDiamondChest(spot.getBlock());
                break;
            }
            case 8: {
                makeExperienceChest(spot.getBlock());
                break;
            }
            case 9: {
                makeRandomChestOfBooks(spot.getBlock());
                break;
            }
            case 10: {
                makeLapusChest(spot.getBlock());
                break;
            }
            case 11: {
                makeBuilderChest(spot.getBlock());
                break;
            }
        }
    }


    public static void makeChestSquare(Location spot, int squareWidth, int squareLength) {
        Location firstCorner = spot.clone();
        firstCorner.setX(spot.getX() - squareWidth/2);
        firstCorner.setZ(spot.getZ() - squareLength/2);
        firstCorner.setY(spot.getY() - 1);

        Random rand = new Random();
        int xmax = squareWidth;
        int zmax = squareLength;
        World world = firstCorner.getWorld();
        if (world == null) {
            throw new IllegalStateException("World is null");
        }
        //base - at starting z
        for (int x = 0; x < xmax; x++) {
            Location newLocation = firstCorner.clone();
            newLocation.setX(firstCorner.getX() + x);
            // same z location
            ChestMaker.makeRandomChests(newLocation, rand.nextInt(12));
        }
        //right side
        for (int z = 0; z < zmax; z++) {
            Location newLocation = firstCorner.clone();
            // same x location
            newLocation.setZ(firstCorner.getZ() + z);
            ChestMaker.makeRandomChests(newLocation, rand.nextInt(12));
        }
        //left side
        for (int z = 0; z < zmax; z++) {
            Location newLocation = firstCorner.clone();
            // max x side
            newLocation.setX(firstCorner.getX() + xmax - 1);
            newLocation.setZ(firstCorner.getZ() + z);
            ChestMaker.makeRandomChests(newLocation, rand.nextInt(12));
        }
        //top -- at zmax
        for (int x = 0; x < xmax; x++) {
            Location newLocation = firstCorner.clone();
            newLocation.setX(firstCorner.getX() + x);
            newLocation.setZ(firstCorner.getZ() + zmax - 1);
            ChestMaker.makeRandomChests(newLocation, rand.nextInt(12));
        }

    }

    public static void makeChickenChests(Block block, World world, Location spot) {
        double xSpot = spot.getX() + 16;
        double ySpot = spot.getY() + 1;
        double zSpot = spot.getZ() + 16;
        makeChest(new Location(world, xSpot + 1, ySpot, zSpot).getBlock(),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND_SWORD, 24)
        );
        //under farm
        makeChest(new Location(world, xSpot + 2, ySpot, zSpot).getBlock(),
                new ItemStack(Material.GOLDEN_APPLE, 64),
                new ItemStack(Material.GOLDEN_APPLE, 64),
                new ItemStack(Material.GOLDEN_APPLE, 64),
                new ItemStack(Material.GOLDEN_APPLE, 64),
                new ItemStack(Material.GOLDEN_APPLE, 64)
        );
        //mountain hook
        makeChest(new Location(world, xSpot + 3, ySpot, zSpot).getBlock(),
                new ItemStack(Material.BLAZE_ROD, 64),
                new ItemStack(Material.STONE, 64),
                new ItemStack(Material.NETHER_WART, 64),
                new ItemStack(Material.SUGAR, 64),
                new ItemStack(Material.GLOWSTONE, 64),
                new ItemStack(Material.GLISTERING_MELON_SLICE, 64),
                new ItemStack(Material.NETHER_WART, 64),
                new ItemStack(Material.SUGAR, 64),
                new ItemStack(Material.GLOWSTONE, 64),
                new ItemStack(Material.JUNGLE_BOAT, 64),
                new ItemStack(Material.GLISTERING_MELON_SLICE, 64)
        );

        //cave to the north
        makeChest(new Location(world, xSpot + 4, ySpot, zSpot
                ).getBlock(),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64)
        );

        //mountaintop
        makeChest(new Location(world, xSpot + 5, ySpot, zSpot
                ).getBlock(),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.CROSSBOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1)
        );

        //pillager desert outpost
        makeChest(new Location(world, xSpot + 6, ySpot, zSpot
                ).getBlock(),
                new ItemStack(Material.ENCHANTING_TABLE, 1),
                new ItemStack(Material.ENCHANTING_TABLE, 1),
                new ItemStack(Material.ENCHANTING_TABLE, 1),
                new ItemStack(Material.BREWING_STAND, 1),
                new ItemStack(Material.BREWING_STAND, 1),
                new ItemStack(Material.BREWING_STAND, 1),
                new ItemStack(Material.CRAFTING_TABLE, 1),
                new ItemStack(Material.CRAFTING_TABLE, 1),
                new ItemStack(Material.CRAFTING_TABLE, 1),
                new ItemStack(Material.ANVIL, 1),
                new ItemStack(Material.ANVIL, 1),
                new ItemStack(Material.ANVIL, 1)
        );

        //instant walls
        makeChest(new Location(world, xSpot + 7, ySpot, zSpot
                ).getBlock(),
                new ItemStack(Material.RED_GLAZED_TERRACOTTA, 64),
                new ItemStack(Material.BLUE_GLAZED_TERRACOTTA, 64),
                new ItemStack(Material.YELLOW_GLAZED_TERRACOTTA, 64),
                new ItemStack(Material.GREEN_GLAZED_TERRACOTTA, 64)
        );

        makeChestOfBooks(new Location(world, xSpot, ySpot, zSpot + 1).getBlock(), Enchantment.DAMAGE_ALL);

        makeChestOfBooks(new Location(world, xSpot, ySpot, zSpot + 2).getBlock(), Enchantment.ARROW_DAMAGE);

        makeChestOfBooks(new Location(world, xSpot, ySpot, zSpot + 3).getBlock(), Enchantment.PROTECTION_ENVIRONMENTAL);

        //village food locations
        //apples
        makeChest(new Location(world, xSpot, ySpot, zSpot + 4
                ).getBlock(),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64)
        );

        //pork
        makeChest(new Location(world, xSpot, ySpot, zSpot + 5
                ).getBlock(),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64));
        //beef
        makeChest(new Location(world, xSpot, ySpot, zSpot + 6
                ).getBlock(),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64));
        //Lapis
        makeChest(new Location(world, xSpot, ySpot, zSpot + 7
                ).getBlock(),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64));

    }

    public static void makeHiddenChests(Block block, World world) {
        makeChest(new Location(world, -229, 70, 300).getBlock(),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND_SWORD, 24)
        );
        //under farm
        makeChest(new Location(world, -241, 69, 279).getBlock(),
                new ItemStack(Material.GOLDEN_APPLE, 64),
                new ItemStack(Material.GOLDEN_APPLE, 64),
                new ItemStack(Material.GOLDEN_APPLE, 64),
                new ItemStack(Material.GOLDEN_APPLE, 64),
                new ItemStack(Material.GOLDEN_APPLE, 64)
        );
        //mountain hook
        makeChest(new Location(world, -321, 85, -161).getBlock(),
                new ItemStack(Material.BLAZE_ROD, 64),
                new ItemStack(Material.STONE, 64),
                new ItemStack(Material.NETHER_WART, 64),
                new ItemStack(Material.SUGAR, 64),
                new ItemStack(Material.GLOWSTONE, 64),
                new ItemStack(Material.GLISTERING_MELON_SLICE, 64),
                new ItemStack(Material.NETHER_WART, 64),
                new ItemStack(Material.SUGAR, 64),
                new ItemStack(Material.GLOWSTONE, 64),
                new ItemStack(Material.GLISTERING_MELON_SLICE, 64)
        );

        //cave to the north
        makeChest(new Location(world, -121, 51, -4
                ).getBlock(),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.IRON_INGOT, 64)
        );

        //mountaintop
        makeChest(new Location(world, -81, 116, 20
                ).getBlock(),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64)
        );

        //pillager desert outpost
        makeChest(new Location(world, 104, 69, 328
                ).getBlock(),
                new ItemStack(Material.ENCHANTING_TABLE, 10)
        );

        makeChestOfBooks(new Location(world, 103, 69, 328).getBlock(), Enchantment.DAMAGE_ALL);

        makeChestOfBooks(new Location(world, 103, 69, 327).getBlock(), Enchantment.ARROW_DAMAGE);

        makeChestOfBooks(new Location(world, 104, 69, 327).getBlock(), Enchantment.PROTECTION_ENVIRONMENTAL);

        //village food locations
        //apples
        makeChest(new Location(world, -233, 72, 320
                ).getBlock(),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64),
                new ItemStack(Material.APPLE, 64)
        );

        //pork
        makeChest(new Location(world, -235, 72, 318
                ).getBlock(),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64));
        //beef
        makeChest(new Location(world, -214, 74, 305
                ).getBlock(),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.COOKED_BEEF, 64));

    }

    private static void makeRandomChestOfBooks(Block block) {
        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();
        Inventory inv = chest.getInventory();


        for (int i=0; i<27; i++) {
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
            Random rand = new Random();
            int type = rand.nextInt(37);
            Enchantment enchantment = Enchantment.values()[type];
            meta.addStoredEnchant(enchantment, enchantment.getMaxLevel(), false);
            book.setItemMeta(meta);
            inv.addItem(book);
        }
    }
    private static void makeChestOfBooks(Block block, Enchantment enchantment) {
        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();
        Inventory inv = chest.getInventory();

        for (int i=0; i<27; i++) {
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
            meta.addStoredEnchant(enchantment, 3, false);
            book.setItemMeta(meta);
            inv.addItem(book);
        }
    }

    private static void makeChest(Block block, ItemStack... itemStacks) {
        LOG.info("making chest at " + block.getLocation());
        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();
        Inventory inv = chest.getInventory();
        for (ItemStack stack : itemStacks) {
            inv.addItem(stack);
        }
    }

    private static void makeExperienceChest(Block block) {
        makeChest(block,
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64),
                new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
    }
    private static void makeUtilityChest(Block block) {
        makeChest(block,
                new ItemStack(Material.ENCHANTING_TABLE, 1),
                new ItemStack(Material.ENCHANTING_TABLE, 1),
                new ItemStack(Material.ENCHANTING_TABLE, 1),
                new ItemStack(Material.BREWING_STAND, 1),
                new ItemStack(Material.BREWING_STAND, 1),
                new ItemStack(Material.BREWING_STAND, 1),
                new ItemStack(Material.CRAFTING_TABLE, 1),
                new ItemStack(Material.CRAFTING_TABLE, 1),
                new ItemStack(Material.CRAFTING_TABLE, 1),
                new ItemStack(Material.ANVIL, 1),
                new ItemStack(Material.ANVIL, 1),
                new ItemStack(Material.ANVIL, 1));
    }

    private static void makeDiamondChest(Block block) {
        makeChest(block,
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND, 64),
                new ItemStack(Material.DIAMOND_SWORD, 1),
                new ItemStack(Material.DIAMOND_SWORD, 1),
                new ItemStack(Material.DIAMOND_SWORD, 1),
                new ItemStack(Material.DIAMOND_SWORD, 1),
                new ItemStack(Material.DIAMOND_SWORD, 1),
                new ItemStack(Material.DIAMOND_SWORD, 1),
                new ItemStack(Material.DIAMOND_SWORD, 1),
                new ItemStack(Material.DIAMOND_SWORD, 1),
                new ItemStack(Material.DIAMOND_PICKAXE, 1),
                new ItemStack(Material.DIAMOND_PICKAXE, 1),
                new ItemStack(Material.DIAMOND_PICKAXE, 1),
                new ItemStack(Material.DIAMOND_PICKAXE, 1),
                new ItemStack(Material.DIAMOND_SWORD, 1)
                );
    }
    private static void makeBrewingChest(Block block) {
        makeChest(block,
                new ItemStack(Material.BLAZE_ROD, 64),
                new ItemStack(Material.STONE, 64),
                new ItemStack(Material.NETHER_WART, 64),
                new ItemStack(Material.SUGAR, 64),
                new ItemStack(Material.GLOWSTONE, 64),
                new ItemStack(Material.GLISTERING_MELON_SLICE, 64),
                new ItemStack(Material.NETHER_WART, 64),
                new ItemStack(Material.SUGAR, 64),
                new ItemStack(Material.GLOWSTONE, 64),
                new ItemStack(Material.GLASS_BOTTLE, 64),
                new ItemStack(Material.WATER_BUCKET, 1),
                new ItemStack(Material.WATER_BUCKET, 1),
                new ItemStack(Material.WATER_BUCKET, 1),
                new ItemStack(Material.WATER_BUCKET, 1),
                new ItemStack(Material.GLISTERING_MELON_SLICE, 64));
    }
    private static void makeWeaponChest(Block block) {
        makeChest(block,
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.CROSSBOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.BOW, 1),
                new ItemStack(Material.TRIDENT, 1),
                new ItemStack(Material.TRIDENT, 1),
                new ItemStack(Material.TRIDENT, 1),
                new ItemStack(Material.BOW, 1));
    }

    private static void makePorkChest(Block block) {
        makeChest(block,
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64),
                new ItemStack(Material.COOKED_PORKCHOP, 64));
    }

    private static void makeBuilderChest(Block block) {
        makeChest(block,
                new ItemStack(Material.COBBLESTONE, 64),
                new ItemStack(Material.COBBLESTONE, 64),
                new ItemStack(Material.COBBLESTONE, 64),
                new ItemStack(Material.ACACIA_PLANKS, 64),
                new ItemStack(Material.DIRT, 64),
                new ItemStack(Material.STICK, 64),
                new ItemStack(Material.SMOOTH_QUARTZ, 64),
                new ItemStack(Material.SAND, 64));
    }

    private static void makeLapusChest(Block block) {
        makeChest(block,
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.LAPIS_LAZULI, 64));
    }


}
