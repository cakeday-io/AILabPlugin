package com.lucaslab;


import static org.mockito.Mockito.*;
import com.lucaslab.GatherFoodGoal;
//import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

import org.bukkit.entity.Silverfish;
import org.mockito.MockitoAnnotations;

import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GatherFoodGoalTest {

    @Mock
    Location myLocation;

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


        GatherFoodGoal<Silverfish> testGoal = new GatherFoodGoal<Silverfish>(plugin, mob, myLocation);
        Location loc = testGoal.lookAroundYouForFood(null, 0);
    }
}
