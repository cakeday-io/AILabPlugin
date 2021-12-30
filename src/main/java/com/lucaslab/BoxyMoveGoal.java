package com.lucaslab;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

import java.util.EnumSet;

public class BoxyMoveGoal<T extends Mob> implements Goal {

    private final Plugin plugin;
    private final Mob mob;
    private final Location target;

    public BoxyMoveGoal(Plugin plugin, Mob mob, Location target) {
        this.plugin =plugin;
        this.mob=mob;
        this.target=target;
    }
    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public GoalKey getKey() {
        return GoalKey.of(org.bukkit.entity.Mob.class, new NamespacedKey(plugin, "BoxyMoveGoal"));
    }

    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET, GoalType.MOVE);
    }

    @Override
    public void tick() {
//        Pathfinder.PathResult pathResult = mob.getPathfinder().findPath(target);
//        mob.getPathfinder().
//         mob.setTarget(target);
        mob.getPathfinder().moveTo(target);
    }
}
