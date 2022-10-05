package io.github.yarnesl.farmzone.guis;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.yarnesl.farmzone.FZPlayer;
import io.github.yarnesl.farmzone.FarmZone;

public class FZGuiMain extends FZGui implements Listener {
    
    public FZGuiMain() {
        this.createGui(9, "Farmzone Main Menu");
    }
    
    @Override
    protected void populateItems() {
        gui.setItem(0, createGuiItem("Farming!", Material.WHEAT, 1, "Click to begin you farming", "adventure!"));
    }
    
    public static void onMenuClick(Inventory inv, ItemStack itemClicked, FZPlayer fzp) {
        if (itemClicked.getType().equals(Material.WHEAT)) {
            fzp.getPlayer().closeInventory();
            new FZGuiSources(fzp).openInventory(fzp.getPlayer());
        }
    }

}
