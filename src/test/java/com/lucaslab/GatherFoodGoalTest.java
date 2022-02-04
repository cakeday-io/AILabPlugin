package com.lucaslab;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Silverfish;
import org.bukkit.plugin.Plugin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GatherFoodGoalTest {

    @Mock
    Location myLocation;

    @Mock
    Location checkLocation;

    @Mock
    BlockData blockData;

    @Mock
    Block block;

    @Mock
    Plugin plugin;

    @Mock
    Mob mob;

    @Test
    void testLookAroundYou() {
        // define return value for a starting location
        when(myLocation.getX()).thenReturn((double) 0);
        when(myLocation.getY()).thenReturn((double) 0);
        when(myLocation.getZ()).thenReturn((double) 0);

        when(checkLocation.getX()).thenReturn((double) 0);
        when(checkLocation.getY()).thenReturn((double) 0);
        when(checkLocation.getZ()).thenReturn((double) 0);


        when(blockData.getMaterial()).thenReturn(Material.ACACIA_WOOD);
        when(block.getBlockData()).thenReturn(blockData);


        when(myLocation.clone()).thenReturn(checkLocation);
        when(checkLocation.getBlock()).thenReturn(block);

        GatherFoodGoal<Silverfish> testGoal = new GatherFoodGoal<Silverfish>(plugin, mob, myLocation);
        Location loc = testGoal.lookAroundYouForFood(myLocation, 1);
        assertTrue(true);
    }
}
