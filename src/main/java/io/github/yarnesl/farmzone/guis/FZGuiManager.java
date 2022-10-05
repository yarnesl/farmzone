package io.github.yarnesl.farmzone.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.yarnesl.farmzone.FZPlayer;
import io.github.yarnesl.farmzone.FarmZone;

public class FZGuiManager implements Listener {
    
    FarmZone plugin;
    public static enum FZGuis{
        MAIN;
    }
    
    public FZGuiManager(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        p.sendMessage("Clicked " + e.getCurrentItem().getType());
        FZPlayer fzp = plugin.extractPlayer(p.getUniqueId().toString());
        if (e.getView().getTitle().equals("Farmzone Main Menu")) {
            e.setCancelled(true);
            FZGuiMain.onMenuClick(e.getInventory(), e.getCurrentItem(), fzp);
        } else if (e.getView().getTitle().equals("Farming Tree")) {
            e.setCancelled(true);
            FZGuiSources.onMenuClick(e.getInventory(), e.getCurrentItem(), fzp);
        } else if(e.getView().getTitle().equals("Farmzone Shop Main")) {
            e.setCancelled(true);
            FZGuiShopMain.onMenuClick(e.getInventory(), e.getCurrentItem(), fzp);
        }
    }

}
