package io.github.yarnesl.farmzone.guis;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.yarnesl.farmzone.FZPlayer;

public abstract class FZGui {
    
    protected Inventory gui;
    
    protected abstract void populateItems();
    //public abstract void onMenuClick(Inventory inventory, ItemStack itemStack, HumanEntity humanEntity);
    
    protected void createGui(int size, String name) {
        gui = Bukkit.createInventory(null, size, name);
        populateItems();
    }
    
    protected ItemStack createGuiItem(Material mat, int amt, String...lore) {
        ItemStack item = new ItemStack(mat, amt);
        ItemMeta meta = item.getItemMeta();
        
        meta.setLore(Arrays.asList(lore));
        
        item.setItemMeta(meta);    
        
        return item;
    }
    
    protected ItemStack createGuiItem(String itemName, Material mat, int amt, String...lore) {
        ItemStack item = new ItemStack(mat, amt);
        ItemMeta meta = item.getItemMeta();
        
        meta.setLore(Arrays.asList(lore));
        meta.setDisplayName(itemName);
        
        item.setItemMeta(meta);    
        
        return item;
    }
    
    protected ItemStack createBlockableGuiItem(FZPlayer fzp, int lvlRestriction, Material lockedMat, Material unlockedMat, int amt, String...lore) {
        
        ItemStack item;
        ItemMeta meta;
        if (fzp.getLevel() >= lvlRestriction) {
            item = new ItemStack(unlockedMat, amt);
            meta = item.getItemMeta();
            meta.setLore(Arrays.asList(lore));
        } else {
            item = new ItemStack(lockedMat, amt);
            meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Unlock at Lvl " + lvlRestriction);
        }
        
        item.setItemMeta(meta);    
        
        return item;
    }
    
    public void openInventory(HumanEntity ent) {
        ent.openInventory(gui);
    }
    
    
    
}
