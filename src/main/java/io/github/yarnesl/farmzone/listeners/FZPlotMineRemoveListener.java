package io.github.yarnesl.farmzone.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.PlotMine;
import io.github.yarnesl.farmzone.events.FZPlotMineRemoveEvent;

public class FZPlotMineRemoveListener implements Listener {
    
    FarmZone plugin;
    
    public FZPlotMineRemoveListener(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onFZPlotMineRemove(FZPlotMineRemoveEvent e) {
        Player p = (Player)e.getWho();
        PlotMine pm = e.getPlotMine();
        p.removeMetadata("fzplotmine", plugin);
        pm.deconstruct();
    }

}
