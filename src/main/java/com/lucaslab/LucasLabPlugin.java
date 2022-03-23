package com.lucaslab;

import com.ticxo.modelengine.ModelEngine;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.logging.Logger;

public class LucasLabPlugin extends JavaPlugin implements Listener {
    public static final Logger LOG = Logger.getLogger("LucasLabPlugin");

    private int taskID = 0;

    public static ArrayList<Projectile> arrows = new ArrayList<>();

    @Override
    public void onDisable() {
        // Don't log disabling, Spigot does that for you automatically!
    }

    @Override
    public void onEnable() {
        // Don't log enabling, Spigot does that for you automatically!
        PluginManager pluginManager = getServer().getPluginManager();

        //Do all setup in here
        Bukkit.getPluginManager().registerEvents(this, this);
        arrowTrace();
    }
    public void arrowTrace() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

            @Override
            public void run() {
                for(Projectile arrow : arrows){
                    Location loc = arrow.getLocation();
                    arrow.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0);
                }
            }

        }, 0, 1);
    }

    @EventHandler
    public void click(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().getType() == Material.NETHERITE_SWORD) {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                p.sendMessage("You right-clicked!");
                Vector v = p.getLocation().getDirection().multiply(2D);
                Arrow a = (Arrow) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.ARROW);
                a.setDamage(10);
                a.setShooter(p);
                a.setVelocity(v);
                arrows.add(a);


                ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
                if(meta instanceof Damageable) {
                    Damageable damageableMeta = (Damageable)meta;
                    damageableMeta.setHealth(damageableMeta.getHealth() + 10);
                    

                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        LOG.info("Projectile hit: " + event.getEventName());
        if (event.getEntity() instanceof Arrow) {
            arrows.remove(event.getEntity());
            if (event.getEntity().getMetadata("lucasarrow").size() != 0)  {
                event.getEntity().getWorld().createExplosion(event.getEntity(), 10,true);
            }

        }
    }
    public boolean onCommand(CommandSender sender,
                             Command command, String label, String[] arguments) {

        LOG.info("Command Called: " + label);
        if (sender instanceof Player) {
            Player thisPlayer = (Player) sender;


            Location spot = thisPlayer.getLocation().clone();

            Location spawnSpot = spot.clone();
//            spawnSpot.setX(spawnSpot.getX() + 0);
//            clearLocation(spawnSpot);
//            Location goSpot = spot.clone();
//            goSpot.setX(spawnSpot.getX() + 100);
//            clearLocation(goSpot);
//            logSpot(spot);


            if (label.equalsIgnoreCase("lucaslab:test")) {
                LOG.info("Creating Test Jan 17,2022 v1");

                World world = spawnSpot.getWorld();

                //This code loads the blueprint and tells you all the bones
//                ModelBlueprint blueprint = ModelEngine.getModelBlueprint("termite");
//
//                //ModelEngineAPI.getModelManager().getModelRegistry()
//                if(blueprint != null) {
//                    Map<String, BlueprintBone> bones = blueprint.getBones();
//                    Iterator iter = bones.keySet().iterator();
//                    while(iter.hasNext()) {
//                        Object key = iter.next();
//                        Object value = bones.get(key);
//                        LOG.info("Got Bone Key[" + key + "] Value [" + value.toString() + "]");
//                    }
//                }

                ActiveModel activeModel = ModelEngine.createActiveModel("termite");
                if(activeModel != null) {
                    LOG.info("Loaded active model [" + activeModel.getModelId() + "]");
                } else {
                    LOG.info("Model was null, no termite.bbmodel file in the /blueprints folder");
                }

                Mob newMob = (Mob) world.spawnEntity(spawnSpot, EntityType.SILVERFISH);
                ModeledEntity modeledEntity = ModelEngine.createModeledEntity(newMob);
                modeledEntity.addActiveModel(activeModel);

                LOG.info("Created a " + newMob.getType() + " with name " + newMob.getName());
                Bukkit.getMobGoals().removeAllGoals(newMob);
                Bukkit.getMobGoals().addGoal(newMob, 1, new AttackGoal<Silverfish>(this, newMob, spawnSpot));
                Bukkit.getMobGoals().addGoal(newMob, 2, new GatherFoodGoal<Silverfish>(this, newMob, spawnSpot));
                Bukkit.getMobGoals().addGoal(newMob, 3, new MakeNestGoal<Silverfish>(this, newMob, spawnSpot));
                LOG.info("Lucas Lab Test Complete");
            }
            if (label.equalsIgnoreCase("lucaslab:arrow2")) {
                Arrow arrow = thisPlayer.launchProjectile(Arrow.class);
                arrow.setGravity(false);
                //arrow.addPassenger(thisPlayer);
                arrow.setVelocity(arrow.getVelocity().multiply(1.5));
                arrow.setKnockbackStrength(10);
                //arrow.setBounce(true);
                arrow.setDamage(100);
                arrow.setCustomName("apple pants");
                arrow.setFallDistance(100);
                arrow.addCustomEffect(PotionEffectType.BLINDNESS.createEffect(15,1) , true);
                arrow.addCustomEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(20,1) , true);
                arrow.addCustomEffect(PotionEffectType.HARM.createEffect(10,1) , true);
                arrow.addCustomEffect(PotionEffectType.BAD_OMEN.createEffect(1000000,1) , true);
                arrow.addCustomEffect(PotionEffectType.LEVITATION.createEffect(100,1) , true);
                arrow.addCustomEffect(PotionEffectType.UNLUCK.createEffect(50,1) , true);
                

                MetadataValue metadataValue = new FixedMetadataValue(this , "lucasarrow");
                arrow.setMetadata("lucasarrow", metadataValue);


            }
            if (label.equalsIgnoreCase("lucaslab:arrow")) {
//                for(int i = 0; i <= 100d ; i++) {
                    this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                        @Override
                        public void run() {
                            Arrow arrow = thisPlayer.launchProjectile(Arrow.class);
                            Location loc = arrow.getLocation();

                            arrow.getWorld().playEffect(loc, Effect.SMOKE, 10);
                        }
                    }, 0L, 1L); //20 Tick (1 Second) delay before run() is called

///                   arrow.getMetadata();
///                    Arrow arrow = thisPlayer.getWorld().spawnArrow(thisPlayer.getLocation(),thisPlayer.getLocation().getDirection(),100, 0);

           //     }
            }
            if (label.equalsIgnoreCase("lucaslab:off_arrow")) {
                Bukkit.getServer().getScheduler().cancelTask(taskID);

            }
        }
        return false;
    }

    private void logSpot(Location spot) {
        LOG.info("Spot at [" + spot.getX() + "," + spot.getY() + "," + spot.getZ() + "]");
    }

    private void clearLocation(Location clearSpot) {
        Location clearLocation = clearSpot.clone();
        clearLocation.setX(clearSpot.getX() - 5);
        clearLocation.setZ(clearSpot.getZ() - 5);
        StuffMaker.createFilledBox(Material.AIR, clearLocation, 10, 10, 10);
    }
}
