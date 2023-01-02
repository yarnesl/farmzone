package io.github.yarnesl.farmzone.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.github.yarnesl.farmzone.PlotMine;

public class FZPlotMineFillEvent extends Event {
    
    public static final HandlerList handlers = new HandlerList();
    
    Entity who;
    PlotMine pm;
    
    public FZPlotMineFillEvent(Entity who, PlotMine pm) {
        this.who = who;
        this.pm = pm;
    }
    
    public Entity getWho() {
        return who;
    }
    
    public PlotMine getPlotMine() {
        return this.pm;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
