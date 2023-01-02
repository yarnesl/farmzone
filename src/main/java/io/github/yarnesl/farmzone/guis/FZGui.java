package io.github.yarnesl.farmzone.guis;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.yarnesl.farmzone.FZPlayer;
import io.github.yarnesl.farmzone.PlotMine;

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
    
    protected ItemStack createBlockableGuiItem(String blockType, Object obj, int lvlRestriction, Material lockedMat, Material unlockedMat, int amt, String...lore) {
        
        ItemStack item;
        item = null;
        ItemMeta meta;
        if (blockType.equals("sources")) {
            FZPlayer fzp;
            if (obj instanceof FZPlayer) {
                fzp = (FZPlayer) obj;
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
            }               
        } else if (blockType.equals("plotmine_menu")) {
            PlotMine pm;
            if (obj instanceof PlotMine) {
                pm = (PlotMine) obj;
                if (pm.getSize() >= lvlRestriction) {
                    item = new ItemStack(unlockedMat, amt);
                    meta = item.getItemMeta();
                    meta.setLore(Arrays.asList(lore));
                } else {
                    item = new ItemStack(lockedMat, amt);
                    meta = item.getItemMeta();
                    meta.setLore(Arrays.asList(lore));
                }  
                item.setItemMeta(meta);
            }
        } else {
            Bukkit.getLogger().info("Invalid blocking type passed to createBlockableGuiItem");
            return null;
        }   
        return item;
    }
    
    public Inventory getMenu() {
        return gui;
    }
    
    
    
}
