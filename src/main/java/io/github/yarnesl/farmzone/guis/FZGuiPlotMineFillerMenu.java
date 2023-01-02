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

public class FZGuiPlotMineFillerMenu extends FZGui implements Listener {
    
    private PlotMine pm;
    FarmZone plugin;
    
    public FZGuiPlotMineFillerMenu(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    public FZGuiPlotMineFillerMenu(FarmZone plugin, PlotMine pm) {
        this.plugin = plugin;
        this.pm = pm;
        this.createGui(18, "PlotMine Filler Block Menu");
        Material[] mat = new Material[1];
        mat[0] = pm.getFillMat();
        this.gui = enchantSelected(this.gui, mat);
    }

    @Override
    protected void populateItems() {
        gui.setItem(0, createBlockableGuiItem("plotmine_menu", pm, 3, Material.RED_STAINED_GLASS_PANE, Material.STONE, 1, "Set filler block to STONE"));
        gui.setItem(1, createBlockableGuiItem("plotmine_menu", pm, 4, Material.RED_STAINED_GLASS_PANE, Material.DIRT, 1, "Set filler block to DIRT"));
        gui.setItem(2, createBlockableGuiItem("plotmine_menu", pm, 5, Material.RED_STAINED_GLASS_PANE, Material.GRANITE, 1, "Set filler block to GRANITE"));
        
        gui.setItem(13, createGuiItem("Back", Material.BARRIER, 1, "Click to go back to", "the previous menu"));
    }
    
    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        
        if (e.getView().getTitle().equals("PlotMine Filler Block Menu")) {
            e.setCancelled(true);
            PlotMine pm;
            ArrayList<MetadataValue> mlist = new ArrayList<MetadataValue>(p.getMetadata("fzplotmine"));
            if (mlist.size() > 0) {
                FixedMetadataValue fmd = (FixedMetadataValue) mlist.get(0);
                if (fmd.value() instanceof PlotMine) {
                    pm = (PlotMine) fmd.value();
                    
                    if (item != null) {
                        switch(item.getType()) {
                            case STONE:
                                selectFillMaterial(inv, pm, p, 0, Material.STONE);
                                break;
                            case DIRT:
                                selectFillMaterial(inv, pm, p, 1, Material.DIRT);
                                break;
                            case GRANITE:
                                selectFillMaterial(inv, pm, p, 2, Material.GRANITE);
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
    
    private void selectFillMaterial(Inventory inv, PlotMine pm, Player p, int slot, Material mat) {
        Inventory tmpInv = inv;
        
        //Remove any other glow effects on other items in the inventory that aren't the selected item
        for (int i = 0; i < tmpInv.getSize(); i++) {
            if (tmpInv.getItem(i) != null)
                tmpInv.getItem(i).removeEnchantment(Enchantment.ARROW_INFINITE);
        }
        
        //Add glow effect to selected item
        ItemStack item = tmpInv.getItem(slot);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        
        
        tmpInv.setItem(slot, item);
        pm.setFillMat(mat);
        p.openInventory(tmpInv);
        
        p.sendMessage(ChatColor.DARK_AQUA + "Set PlotMine fill to " + mat.toString());
    }

}
