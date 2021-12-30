package com.lucaslab;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

import java.util.EnumSet;

public class BoxyAttackGoal<T extends Mob> implements Goal {

    private final Plugin plugin;
    private final Mob mob;
    private final LivingEntity target;

    public BoxyAttackGoal(Plugin plugin, Mob mob, LivingEntity target) {
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
        return GoalKey.of(Mob.class, new NamespacedKey(plugin, "BoxyAttackGoal"));
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
