package io.github.yarnesl.farmzone.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FZAnimalSpawnEvent extends Event implements Cancellable {
    
    public static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    
    private Entity entity;
    
    public FZAnimalSpawnEvent(Entity who) {
        this.entity = who;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
