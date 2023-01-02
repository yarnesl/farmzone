package io.github.yarnesl.farmzone.listeners;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import io.github.yarnesl.farmzone.FZAnimal;
import io.github.yarnesl.farmzone.FZPlayer;
import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.GlobalVariables;
import io.github.yarnesl.farmzone.events.FZAnimalDeathEvent;
import io.github.yarnesl.farmzone.events.FZAnimalSpawnEvent;

public class FZAnimalListener implements Listener {
    
    FarmZone plugin;
    
    public FZAnimalListener(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onSpawnEvent(CreatureSpawnEvent e) {
        if (e.getSpawnReason().equals(SpawnReason.SPAWNER)) {
            EntityType type = e.getEntityType();
            if (type.equals(EntityType.CHICKEN) ||
                type.equals(EntityType.PIG) ||
                type.equals(EntityType.SHEEP) ||
                type.equals(EntityType.COW) ||
                type.equals(EntityType.MUSHROOM_COW)) {
             
                e.setCancelled(true);
                Location loc = e.getLocation();
                Predicate<Entity> isTargetAnimal = (wantedType) -> (wantedType.getType().equals(type));
                ArrayList<Entity> nearbyMobs = (ArrayList<Entity>) loc.getWorld().getNearbyEntities(loc, 6, 1, 6, isTargetAnimal);
                //If we detect a mob near the spawner, push all newly spawned mobs into existing one
                if (nearbyMobs.size() > 0) {
                    ArrayList<MetadataValue> list = new ArrayList<MetadataValue>();
                    list.addAll(nearbyMobs.get(0).getMetadata("FZAnimalIdentity"));
                    if (list.size() > 0) {
                        FixedMetadataValue fmd = (FixedMetadataValue)list.get(0);
                        if (fmd.asInt() < GlobalVariables.MOB_CAP) {
                            //int newNum = fmd.asInt() + 1;
                            FZAnimal animal = (FZAnimal)fmd.value();
                            animal.addToStack(1);
                        }
                    }
                } else {
                    Entity newSpawn = loc.getWorld().spawnEntity(loc.add(0, 1, 0), type);
                    FZAnimalSpawnEvent fzAnimalSpawnEvent = new FZAnimalSpawnEvent(newSpawn);
                    Bukkit.getPluginManager().callEvent(fzAnimalSpawnEvent);
                }
            }
        }
    }
    
    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent e) {
        ArrayList<MetadataValue> mlist = new ArrayList<MetadataValue>();
        mlist.addAll(e.getEntity().getMetadata("FZAnimalIdentity"));
        if (mlist.size() > 0) {
            //System.out.println("custom mob death");
            e.getDrops().clear();
        }
    }
    
    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent e) {
        
        //Check if mob affected is a FZ animal
        ArrayList<MetadataValue> mlist = new ArrayList<MetadataValue>();
        mlist.addAll(e.getEntity().getMetadata("FZAnimalIdentity"));
        if (mlist.size() > 0) {
            LivingEntity affected = (LivingEntity)e.getEntity();
            FixedMetadataValue fmd = (FixedMetadataValue) mlist.get(0);
            FZAnimal animal = (FZAnimal) fmd.value();
            if (e.getDamage() >= affected.getHealth()) {
                int newNum = animal.decrementStack();
                if (newNum != 0) {
                    e.setCancelled(true);
                    affected.setHealth(((Attributable)e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                    affected.getWorld().spawnParticle(Particle.ASH, affected.getLocation(), (int)Math.random()*3);
                    affected.getWorld().playSound(affected.getLocation(), affected.getDeathSound(), .2f, 1);
                    animal.updateHealthString(affected.getHealth());
                } else {
                    animal.killPassengers();
                }
                FZPlayer fzp = plugin.extractPlayer(e.getDamager().getUniqueId().toString());
                FZAnimalDeathEvent fzAnimalDeathEvent = new FZAnimalDeathEvent(e.getEntity(), fzp);
                Bukkit.getServer().getPluginManager().callEvent(fzAnimalDeathEvent);
            } else {
                animal.updateHealthString(affected.getHealth());
            }
        }        
    }
}
