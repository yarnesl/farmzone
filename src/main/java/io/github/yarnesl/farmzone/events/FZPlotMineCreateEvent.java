package io.github.yarnesl.farmzone.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FZPlotMineCreateEvent extends Event {
    
    public static final HandlerList handlers = new HandlerList();
    
    Entity who;
    
    public FZPlotMineCreateEvent(Entity who) {
        this.who = who;
    }
    
    public Entity getWho() {
        return who;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
