package io.github.yarnesl.farmzone.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.PlotMine;
import io.github.yarnesl.farmzone.events.FZPlotMineFillEvent;

public class FZPlotMineFillListener implements Listener {
    
    FarmZone plugin;
    
    public FZPlotMineFillListener(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onFZPlotMineFill(FZPlotMineFillEvent e) {
        //Player p = (Player)e.getWho();
        PlotMine pm = e.getPlotMine();
        
        pm.fillPlotMine();
    }

}
