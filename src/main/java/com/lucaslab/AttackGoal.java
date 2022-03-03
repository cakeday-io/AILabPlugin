package com.lucaslab;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

import java.util.*;
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
        Collection<Entity> entities = myLoc.getNearbyEntities(5, 3, 5);
        if (entities.size() > 0) {
            Iterator<Entity> iter = entities.iterator();


            while (iter.hasNext()) {
                Entity badGuy = iter.next();
                EntityType type = badGuy.getType();
                LOG.info("found type " + type);
                if(!type.equals(EntityType.SILVERFISH)) {
                    mob.getPathfinder().moveTo(badGuy.getLocation());
                    this.mob.attack(badGuy);
                    LOG.info("Attacking bad guy" + badGuy.getType().name());
                }
            }
        }
    }


}
