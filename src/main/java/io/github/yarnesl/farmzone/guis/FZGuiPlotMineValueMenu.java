package io.github.yarnesl.farmzone.guis;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.PlotMine;
import net.md_5.bungee.api.ChatColor;

public class FZGuiPlotMineValueMenu extends FZGui implements Listener {
    
    FarmZone plugin;
    PlotMine pm; 
    
    String menuName = "PlotMine Valuable Block Menu";
    
    public FZGuiPlotMineValueMenu(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    public FZGuiPlotMineValueMenu(FarmZone plugin, PlotMine pm) {
        this.plugin = plugin;
        this.pm = pm;
        this.createGui(18, menuName);
        this.gui = enchantSelected(this.gui, pm.getValuableMaterials());
    }
    
    public void populateItems() {
        gui.setItem(1, createBlockableGuiItem("plotmine_menu", pm, 3, 
                                              Material.RED_STAINED_GLASS_PANE, 
                                              Material.COAL_ORE, 1, 
                                              "Add COAL ORE to valueable", 
                                              "block spawn within PlotMine"));
        gui.setItem(2, createBlockableGuiItem("plotmine_menu", pm, 4, 
                                              Material.RED_STAINED_GLASS_PANE, 
                                              Material.IRON_ORE, 1, 
                                              "Add IRON ORE to valueable", 
                                              "block spawn within PlotMine"));
        gui.setItem(3, createBlockableGuiItem("plotmine_menu", pm, 5, 
                                              Material.RED_STAINED_GLASS_PANE, 
                                              Material.GOLD_ORE, 1, 
                                              "Add GOLD ORE to valueable", 
                                              "block spawn within PlotMine"));
        gui.setItem(4, createBlockableGuiItem("plotmine_menu", pm, 6, 
                                              Material.RED_STAINED_GLASS_PANE, 
                                              Material.DIAMOND_ORE, 1, 
                                              "Add DIAMOND ORE to valueable", 
                                              "block spawn within PlotMine"));
        gui.setItem(5, createBlockableGuiItem("plotmine_menu", pm, 7, 
                                              Material.RED_STAINED_GLASS_PANE, 
                                              Material.LAPIS_ORE, 1, 
                                              "Add LAPIS ORE to valueable", 
                                              "block spawn within PlotMine"));
        gui.setItem(6, createBlockableGuiItem("plotmine_menu", pm, 8, 
                                              Material.RED_STAINED_GLASS_PANE, 
                                              Material.EMERALD_ORE, 1, 
                                              "Add EMERALD ORE to valueable", 
                                              "block spawn within PlotMine"));
        gui.setItem(7, createBlockableGuiItem("plotmine_menu", pm, 9, 
                Material.RED_STAINED_GLASS_PANE, 
                Material.GLOWSTONE, 1, 
                "Add GLOWSTONE to valueable", 
                "block spawn within PlotMine"));
        gui.setItem(13, createGuiItem("Back", Material.BARRIER, 1, "Click to go back to", "the previous menu."));
        
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        
        if (e.getView().getTitle().equals(menuName)) {
            e.setCancelled(true);
            PlotMine pm;
            ArrayList<MetadataValue> mlist = new ArrayList<MetadataValue>(p.getMetadata("fzplotmine"));
            if (mlist.size() > 0) {
                FixedMetadataValue fmd = (FixedMetadataValue) mlist.get(0);
                if (fmd.value() instanceof PlotMine) {
                    pm = (PlotMine) fmd.value();
                    if (item != null) {
                        switch (item.getType()) {
                            case COAL_ORE:
                                selectValuableMaterial(inv, pm, p, Material.COAL_ORE, 1);
                                break;
                            case IRON_ORE:
                                selectValuableMaterial(inv, pm, p, Material.IRON_ORE, 2);
                                break;
                            case GOLD_ORE:
                                selectValuableMaterial(inv, pm, p, Material.GOLD_ORE, 3);
                                break;
                            case DIAMOND_ORE:
                                selectValuableMaterial(inv, pm, p, Material.DIAMOND_ORE, 4);
                                break;
                            case LAPIS_ORE:
                                selectValuableMaterial(inv, pm, p, Material.LAPIS_ORE, 5);
                                break;
                            case EMERALD_ORE:
                                selectValuableMaterial(inv, pm, p, Material.EMERALD_ORE, 6);
                                break;
                            case GLOWSTONE:
                                selectValuableMaterial(inv, pm, p, Material.GLOWSTONE, 7);
                                break;
                            case BARRIER:
                                p.openInventory(pm.getInv());
                            default:
                                
                        }
                    }
                }
            }
        }        
    }
    
    private Inventory enchantSelected(Inventory inv, Material[] mats) {
        Inventory tmpInv = inv;
        ItemStack item;
        for (int i = 0; i < inv.getSize(); i++) {
            item = tmpInv.getItem(i);
            if (item != null) {
                for (int j = 0; j < mats.length; j++) {
                    if (item.getType().equals(mats[j])) {
                        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
                        tmpInv.setItem(i, item);
                    }
                }
            }
        }
        return tmpInv;
    }
    
    private void selectValuableMaterial(Inventory inv, PlotMine pm, Player p, Material mat, int slot) {
        Inventory tmpInv = inv;
        ItemStack item = tmpInv.getItem(slot);
        
        //Check if clicked Material was previously selected
        boolean wasSelected = pm.containsValuableMaterial(mat);
        
        if (wasSelected) {
            item.removeEnchantment(Enchantment.ARROW_INFINITE);
            tmpInv.setItem(slot, item);
            pm.removeValuableMaterial(mat);
            p.sendMessage(ChatColor.DARK_AQUA + "Remove " + mat.toString() + " from Valuable spawns");
        } else {       
            //Add glow effect to selected item
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
            
            tmpInv.setItem(slot, item);
            pm.addValuableMaterial(mat);
            p.sendMessage(ChatColor.DARK_AQUA + "Add " + mat.toString() + " to Valuable spawns");
        }
        p.openInventory(tmpInv);        
    }

}
