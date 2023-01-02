package io.github.yarnesl.farmzone.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.github.yarnesl.farmzone.PlotMine;

public class FZPlotMineRemoveEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();
    
    PlotMine pm;
    Entity who;
    
    public FZPlotMineRemoveEvent(PlotMine pm, Entity who) {
        this.pm = pm;
        this.who = who;
    }
    
    public Entity getWho() {
        return this.who;
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
