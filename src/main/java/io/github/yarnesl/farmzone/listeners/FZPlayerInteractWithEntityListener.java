package io.github.yarnesl.farmzone.listeners;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.PlotMine;

public class FZPlayerInteractWithEntityListener implements Listener {

    FarmZone plugin;
    
    public FZPlayerInteractWithEntityListener(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteractWithEntity(PlayerInteractEntityEvent e) {
        Entity ent = e.getRightClicked();
        
        if (ent.getType().equals(EntityType.VILLAGER)) {
            ArrayList<MetadataValue> mlist = new ArrayList<MetadataValue>();
            mlist.addAll(ent.getMetadata("associatedFZPlotmine"));
            if (mlist.size() > 0) {
                FixedMetadataValue fmd = (FixedMetadataValue) mlist.get(0);
                if (fmd.value() instanceof PlotMine) {
                    PlotMine pm = (PlotMine)fmd.value();
                    e.getPlayer().openInventory(pm.getInv());
                }
            }
        }
    }
    
}
