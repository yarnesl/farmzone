package io.github.yarnesl.farmzone.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.yarnesl.farmzone.FZPlayer;

public class FZGuiShopMain extends FZGui{

    public FZGuiShopMain() {
        this.createGui(9, "Farmzone Shop Main");
    }
    
    @Override
    protected void populateItems() {
        gui.setItem(0, createGuiItem("Test shop item", Material.STONE, 1, "This is a test item"));
    }
    
    public static void onMenuClick(Inventory inv, ItemStack itemClicked, FZPlayer fzp) {
        if (itemClicked.getItemMeta().getDisplayName().equals("Test shop item")) {
            fzp.getPlayer().sendMessage("Shop under construction bro");
        }
    }

}
