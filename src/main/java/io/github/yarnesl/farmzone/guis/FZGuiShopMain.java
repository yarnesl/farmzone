package io.github.yarnesl.farmzone.guis;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.yarnesl.farmzone.FarmZone;

public class FZGuiShopMain extends FZGui implements Listener {

    FarmZone plugin;
    
    public FZGuiShopMain(FarmZone plugin) {
        this.plugin = plugin;
        this.createGui(9, "Farmzone Shop Main");
    }
    
    @Override
    protected void populateItems() {
        gui.setItem(0, createGuiItem("1x STONE", Material.STONE, 1, "Click to purchase STONE"));
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {     
        if (e.getView().getTitle().equals("Farmzone Shop Main")) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage("Shop under construction");
        }
    }

}
