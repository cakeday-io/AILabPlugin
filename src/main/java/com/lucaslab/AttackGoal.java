package com.lucaslab;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class AttackGoal<T extends Mob> implements Goal {
    public static final Logger LOG = Logger.getLogger("AttackGoal");
    private final Plugin plugin;
    private final Mob mob;
    private final Location nest;


    public AttackGoal(Plugin plugin, Mob mob, Location nest) {
        this.plugin = plugin;
        this.mob = mob;
        this.nest = nest;

    }

    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public GoalKey getKey() {
        return GoalKey.of(Mob.class, new NamespacedKey(plugin, "AttackGoal"));
    }

    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET, GoalType.MOVE);
    }

    @Override
    public void tick() {

        Location myLoc = this.mob.getLocation();
        List<Entity> entities = this.mob.getNearbyEntities(myLoc.getX(), myLoc.getY(), myLoc.getZ());
        if (entities.size() > 0) {
            Entity badGuy = entities.get(0);
            this.mob.attack(badGuy);
            LOG.info("Attacking bad guy" + "!");
        }

    }


}
