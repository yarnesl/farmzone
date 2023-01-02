package io.github.yarnesl.farmzone.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.github.yarnesl.farmzone.FZPlayer;
import io.github.yarnesl.farmzone.FarmZone;

public class FZGuiMain extends FZGui implements Listener {
    
    FarmZone plugin;
    
    public FZGuiMain(FarmZone plugin) {
        this.plugin = plugin;
        this.createGui(9, "Farmzone Main Menu");
    }
    
    @Override
    protected void populateItems() {
        gui.setItem(0, createGuiItem("Farming!", Material.WHEAT, 1, "Click to begin you farming", "adventure!"));
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack itemClicked = e.getCurrentItem();
        FZPlayer fzp = plugin.extractPlayer(e.getWhoClicked().getUniqueId().toString());
        Player p = (Player) e.getWhoClicked();
        
        if (itemClicked != null && itemClicked.getType().equals(Material.WHEAT)) {
            e.setCancelled(true);
            p.closeInventory();
            p.openInventory(new FZGuiSources(fzp).getMenu());
        }
    }

}
