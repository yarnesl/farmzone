package io.github.yarnesl.farmzone.guis;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.PlotMine;
import io.github.yarnesl.farmzone.PlotMineController;
import io.github.yarnesl.farmzone.events.FZPlotMineConstructEvent;
import io.github.yarnesl.farmzone.events.FZPlotMineFillEvent;
import io.github.yarnesl.farmzone.events.FZPlotMineRemoveEvent;
import net.md_5.bungee.api.ChatColor;

public class FZGuiPlotMine implements Listener {
    
    public FarmZone plugin;
    
    public FZGuiPlotMine(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    private static ItemStack createGuiItem(String itemName, Material mat, int amt, String...lore) {
        ItemStack item = new ItemStack(mat, amt);
        ItemMeta meta = item.getItemMeta();
        
        meta.setLore(Arrays.asList(lore));
        meta.setDisplayName(itemName);
        
        item.setItemMeta(meta);
        
        return item;
    }
    
    private static void populateItems(Inventory inv, int size, String arg) {
        if (arg.equalsIgnoreCase("villager")) {
            inv.setItem(0, createGuiItem("Remove PlotMine", 
                                         Material.REDSTONE_BLOCK, 1, 
                                         "Click to cancel the process of", 
                                         "creating a new PlotMine"));
            inv.setItem(4, createGuiItem("Toggle Preview Particles", Material.REDSTONE, 1));
            inv.setItem(8, createGuiItem("Confirm Selection", 
                                         Material.EMERALD_BLOCK, 1, 
                                         "Upon clicking, the selected", 
                                         "plotmine will be constructed."));
        } else if (arg.equalsIgnoreCase("chest")) {
            inv.setItem(0, createGuiItem("Reset",
                                         Material.EMERALD_BLOCK, 1, 
                                         "Fill plotmine with blocks based",
                                         "on current settings."));
            inv.setItem(3, createGuiItem("Filler Blocks",
                                         Material.STONE, 1,
                                         "Open filler block menu"));
            inv.setItem(5, createGuiItem("Valuable Blocks",
                                         Material.DIAMOND_ORE, 1,
                                         "Open valueable block menu"));
            inv.setItem(8, createGuiItem("Upgrade!",
                                         Material.NETHER_STAR, 1,
                                         "Upgrade PlotMine to the next tier!",
                                         "Increases size and unlocks more options",
                                         "for blocks to spawn within."));
        } else {
            System.out.println("Invalid arg for inventory populator");
        }
    }
    
    public static void addNewPlotMine(UUID uuid, PlotMine pm) {
        String invName = "PlotMine Configurator";
        Inventory inv = Bukkit.createInventory(null, 9, invName);
        if (pm.isPhysicalPlotMine()) {
            populateItems(inv, pm.getSize(), "chest");
        } else {
            populateItems(inv, pm.getSize(), "villager");    
        }
        pm.setInv(inv);
    }
    
    public static void changePlotMineInventory(String newInvType, PlotMine pm) {
        if (newInvType.equals("chest")) {
            Inventory inv = Bukkit.createInventory(null, 9, "PlotMine Configurator");
            populateItems(inv, pm.getSize(), newInvType);
            pm.setInv(inv);
        }
    }
    
    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("PlotMine Configurator")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked != null) {
                Player p = (Player)e.getWhoClicked();
                PlotMine curPM = PlotMineController.getPlotMine(p.getUniqueId());
                if (curPM != null) {
                    if (curPM.isPhysicalPlotMine()) {
                      if (clicked.getType().equals(Material.EMERALD_BLOCK)) {
                          if (curPM.getFillMat() == null) {
                            p.sendMessage(ChatColor.RED + "Must select fill material first!");
                          } else {
                              FZPlotMineFillEvent event = new FZPlotMineFillEvent(p, curPM);
                              Bukkit.getPluginManager().callEvent(event);
                          }
                      }
                      if (clicked.getType().equals(Material.STONE)) {
                          p.closeInventory();
                          p.openInventory(new FZGuiPlotMineFillerMenu(plugin, curPM).getMenu());
                      } 
                      if (clicked.getType().equals(Material.DIAMOND_ORE)) {
                          p.closeInventory();
                          p.openInventory(new FZGuiPlotMineValueMenu(plugin, curPM).getMenu());
                      }
                      if (clicked.getType().equals(Material.NETHER_STAR)) {
                          if (!curPM.upgradePlotMine()) {
                              p.sendMessage(ChatColor.RED + curPM.getErrorMessage());
                          }
                      }
                    } else {
                        if (clicked.getType().equals(Material.REDSTONE_BLOCK)) {
                            FZPlotMineRemoveEvent event = new FZPlotMineRemoveEvent(curPM, p);
                            Bukkit.getPluginManager().callEvent(event);
                            p.closeInventory();
                            
                        }
                        if (clicked.getType().equals(Material.EMERALD_BLOCK)) {
                            FZPlotMineConstructEvent event = new FZPlotMineConstructEvent(curPM, p);
                            Bukkit.getPluginManager().callEvent(event);
                            p.closeInventory();
                        }
                        
                        if (clicked.getType().equals(Material.REDSTONE)) {
                            if (!curPM.isShowingParticles()) {
                                curPM.createDisplayParticles();
                            } else {
                                curPM.destroyDisplayParticles();
                            }
                        }
                    }
                }
            }
        }
    }    
}
