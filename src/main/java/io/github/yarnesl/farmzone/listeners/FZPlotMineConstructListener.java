package io.github.yarnesl.farmzone.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.PlotMine;
import io.github.yarnesl.farmzone.events.FZPlotMineConstructEvent;

public class FZPlotMineConstructListener implements Listener {
    
    FarmZone plugin;
    
    public FZPlotMineConstructListener(FarmZone plugin) {
        this.plugin = plugin;        
    }
    
    @EventHandler
    public void onPlotMineConstruct(FZPlotMineConstructEvent e) {
        PlotMine pm = e.getPlotMine();
        pm.constructPhysicalPlotMine();
        
        
    }
}
