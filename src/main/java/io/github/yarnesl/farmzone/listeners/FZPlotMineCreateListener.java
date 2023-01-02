package io.github.yarnesl.farmzone.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.PlotMine;
import io.github.yarnesl.farmzone.PlotMineController;
import io.github.yarnesl.farmzone.events.FZPlotMineCreateEvent;

public class FZPlotMineCreateListener implements Listener {
    
    FarmZone plugin;
    
    public FZPlotMineCreateListener(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onFZPlotMineCreate(FZPlotMineCreateEvent e) {
        Player p = (Player)e.getWho();
        PlotMine pm = new PlotMine(p.getLocation(), plugin, p.getUniqueId());
        PlotMineController.addPlotMine(pm.getUuid(), pm);
        if(!pm.createPlotMine()) {
            p.sendMessage(ChatColor.RED + "Could not create plotmine!");
            p.sendMessage(ChatColor.RED + pm.getErrorMessage());
        } else {
            p.setMetadata("fzplotmine", new FixedMetadataValue(plugin, pm));
        }
    }

}
