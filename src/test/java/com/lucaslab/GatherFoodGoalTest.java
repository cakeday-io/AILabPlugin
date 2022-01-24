package com.lucaslab;

import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;
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
//        when(myLocation.getX()).thenReturn((double) 0);
//        when(myLocation.getY()).thenReturn((double) 0);
//        when(myLocation.getZ()).thenReturn((double) 0);


 //       GatherFoodGoal<Silverfish> testGoal = new GatherFoodGoal<Silverfish>(plugin, mob, myLocation);
 //       Location loc = testGoal.lookAroundYouForFood(null, 0);
    }
}
