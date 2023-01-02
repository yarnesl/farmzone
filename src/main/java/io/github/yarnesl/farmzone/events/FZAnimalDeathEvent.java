package io.github.yarnesl.farmzone.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.github.yarnesl.farmzone.FZPlayer;

public class FZAnimalDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    
    /**
     * The EntityType of the animal that was killed by a player
     */
    private EntityType animalKilled;
    
    /**
     * The FZPlayer who killed the animal
     */
    private FZPlayer fzp;
    
    private Entity entity;
    
    public FZAnimalDeathEvent(Entity who, FZPlayer player) {
        this.animalKilled = who.getType();
        this.fzp = player;
        this.entity = who;
    }
    
    public EntityType getTypeKilled() {
        return this.animalKilled;
    }
    
    public FZPlayer getFZPlayer() {
        return fzp;
    }
    
    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
