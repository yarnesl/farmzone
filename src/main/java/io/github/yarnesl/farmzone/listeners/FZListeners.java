package io.github.yarnesl.farmzone.listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import io.github.yarnesl.farmzone.ExpVal;
import io.github.yarnesl.farmzone.FZPlayer;
import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.PlotMine;
import io.github.yarnesl.farmzone.PlotMineController;
import io.github.yarnesl.farmzone.ranks.FZRank;
import io.github.yarnesl.farmzone.scoreboards.FZScoreboard;

public class FZListeners implements Listener {
    
    private FarmZone plugin;
    
    public FZListeners(FarmZone plugin) {
        this.plugin = plugin;
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		//Check if the player has joined the server before
	    Player player = e.getPlayer();
	    FZPlayer fzp = plugin.addActivePlayer(e.getPlayer());
	    FZScoreboard.createFZPlayerScoreboard(fzp);
	    //Register a PlotMine to this player if they have one in existence
	    if (PlotMineController.playerHasPlotMine(player.getUniqueId())) {
            PlotMine pm = PlotMineController.getPlotMine(player.getUniqueId());
            plugin.getLogger().info("tethering player " + player.getUniqueId().toString() + " to serialized plotmine");
            player.setMetadata("fzplotmine", new FixedMetadataValue(plugin, pm));   
        }
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
	    Block b = e.getClickedBlock();
	    Player p = (Player) e.getPlayer();
	    if (b != null && b.getState() instanceof Chest) {
	        ArrayList<MetadataValue> mlist = new ArrayList<MetadataValue>();
	        mlist.addAll(p.getMetadata("fzplotmine"));
	        if (mlist.size() > 0) {
	            FixedMetadataValue fmd = (FixedMetadataValue) mlist.get(0);
	            if (fmd.value() instanceof PlotMine) {
	                PlotMine pm = (PlotMine) fmd.value();
	                if (b.getLocation().equals(pm.getLoc())) {
	                    e.setCancelled(true);
	                    p.openInventory(pm.getInv());
	                }
	            } 
	        } 
	    }
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
	    plugin.removeActivePlayer(e.getPlayer().getUniqueId().toString());
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
	    FZPlayer fzp = plugin.extractPlayer(e.getPlayer().getUniqueId().toString());
	    String message = e.getMessage();
	    e.setFormat(FZRank.generateChatTag(fzp) + message);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
	    Material blockType = e.getBlock().getType();
	    if (blockType.equals(Material.PUMPKIN) || blockType.equals(Material.MELON)
	            || blockType.equals(Material.SUGAR_CANE) || blockType.equals(Material.CACTUS)) {
	        e.getBlock().setMetadata("natural", new FixedMetadataValue(plugin, "false"));
	    }
	}
	
	@EventHandler 
	public void onBlockBreak(BlockBreakEvent e) {
	    Block block = e.getBlock();
	    Player target = e.getPlayer();
	    FZPlayer fzp = plugin.extractPlayer(target.getUniqueId().toString());
	    long expToAdd = 0;
	    /* First check if the block broken was a plant that grows normally.
	     * If that is the case, check the age of the crop and ensure it is mature.
	     * From there, we can check level of player and determine the next step.
	     */
	    org.bukkit.block.data.Ageable ageable;
	    if (block.getType().equals(Material.WHEAT) ||
	        block.getType().equals(Material.CARROTS) ||
	        block.getType().equals(Material.POTATOES) ||
	        block.getType().equals(Material.BEETROOT) ||
	        block.getType().equals(Material.NETHER_WART) ||
	        block.getType().equals(Material.COCOA)) {
	        ageable = (org.bukkit.block.data.Ageable)block.getBlockData();
    	    if (ageable.getAge() == ageable.getMaximumAge()) {
        	    if (block.getType().equals(Material.WHEAT)) {
        	        e.setDropItems(false);
        	        if (fzp.getLevel() < ExpVal.LVL_ACCESS_WHEAT) {
        	            e.setCancelled(true);
        	            target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
        	        } else {
        	            fzp.getPlayer().getInventory().addItem(new ItemStack(Material.WHEAT_SEEDS, 1));
            	        expToAdd = (long)(ExpVal.WHEAT_LOWER + (Math.random() * (ExpVal.WHEAT_UPPER - ExpVal.WHEAT_LOWER)));
            	        fzp.addExp(expToAdd);
        	        }
        	    } else if (block.getType().equals(Material.CARROTS)) {
        	        e.setDropItems(false);
        	        if (fzp.getLevel() < ExpVal.LVL_ACCESS_CARROT) {
                        e.setCancelled(true);
                        target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
                    } else {
                        fzp.getPlayer().getInventory().addItem(new ItemStack(Material.CARROT, 1));
                        expToAdd = (long)(ExpVal.CARROT_LOWER + (Math.random() * (ExpVal.CARROT_UPPER - ExpVal.CARROT_LOWER)));
                        fzp.addExp(expToAdd);
                        }
        	    } else if (block.getType().equals(Material.POTATOES)) {
        	        e.setDropItems(false);
        	        if (fzp.getLevel() < ExpVal.LVL_ACCESS_POTATO) {
                        e.setCancelled(true);
                        target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
                    } else {
                        fzp.getPlayer().getInventory().addItem(new ItemStack(Material.POTATO, 1));
                        expToAdd = (long)(ExpVal.POTATO_LOWER + (Math.random() * (ExpVal.POTATO_UPPER - ExpVal.POTATO_LOWER)));
                        fzp.addExp(expToAdd);
                    }
                } else if (block.getType().equals(Material.BEETROOTS)) {
                    e.setDropItems(false);
                    if (fzp.getLevel() < ExpVal.LVL_ACCESS_BEETROOT) {
                        e.setCancelled(true);
                        target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
                    } else {
                        fzp.getPlayer().getInventory().addItem(new ItemStack(Material.BEETROOT, 1));
                        expToAdd = (long)(ExpVal.BEETROOT_LOWER + (Math.random() * (ExpVal.BEETROOT_UPPER - ExpVal.BEETROOT_LOWER)));
                        fzp.addExp(expToAdd);
                    }
                } else if (block.getType().equals(Material.NETHER_WART)) {
                    e.setDropItems(false);
                    if (fzp.getLevel() < ExpVal.LVL_ACCESS_NETHERWART) {
                        e.setCancelled(true);
                        target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
                    } else {
                        fzp.getPlayer().getInventory().addItem(new ItemStack(Material.NETHER_WART, 1));
                        expToAdd = (long)(ExpVal.NETHERWART_LOWER + (Math.random() * (ExpVal.NETHERWART_UPPER - ExpVal.NETHERWART_LOWER)));
                        fzp.addExp(expToAdd);
                    }
                } else if (block.getType().equals(Material.COCOA)) {
                    e.setDropItems(false);
                    if (fzp.getLevel() < ExpVal.LVL_ACCESS_COCOABEANS) {
                        e.setCancelled(true);
                        target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
                    } else {
                        expToAdd = (long)(ExpVal.COCOABEANS_LOWER + (Math.random() * (ExpVal.COCOABEANS_UPPER - ExpVal.COCOABEANS_LOWER)));
                        fzp.addExp(expToAdd);
                    }
                }
            } else {
                //Block not mature yet, but give player their seed back
                if (block.getType().equals(Material.WHEAT)) {
                    fzp.getPlayer().getInventory().addItem(new ItemStack(Material.WHEAT_SEEDS, 1));
                } 
                else if (block.getType().equals(Material.CARROTS)) {
                    fzp.getPlayer().getInventory().addItem(new ItemStack(Material.CARROT, 1));
                }
                else if (block.getType().equals(Material.POTATOES)) {
                    fzp.getPlayer().getInventory().addItem(new ItemStack(Material.POTATO, 1));
                }
                else if (block.getType().equals(Material.BEETROOTS)) {
                    fzp.getPlayer().getInventory().addItem(new ItemStack(Material.BEETROOT, 1));
                }
                else if (block.getType().equals(Material.NETHER_WART)) {
                    fzp.getPlayer().getInventory().addItem(new ItemStack(Material.NETHER_WART, 1));
                }
                else if (block.getType().equals(Material.COCOA)) {
                    fzp.getPlayer().getInventory().addItem(new ItemStack(Material.COCOA_BEANS, 1));
                }
            }
	    }
	    
	    //If crop not a traditional style, but instead takes a block form where it appears fully
	    //grown the second it is placed, look for special metadata that signals if the player placed
	    //it, and if the metadata isn't present then the block was naturally occuring and we can
	    //give the player xp for it
        boolean hasMD;
        if (block.getType().equals(Material.SUGAR_CANE)) {
            e.setCancelled(true);
            if (fzp.getLevel() < ExpVal.LVL_ACCESS_SUGARCANE) {
                target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
            } else {
                //Check block and blocks above, break whole plant and give player items as necessary
                while (block.getType().equals(Material.SUGAR_CANE)) {
                    //Break the block
                    block.setType(Material.AIR);
                    //Check whether sugar cane above is natural or placed by player
                    hasMD = false;
                    for (MetadataValue md : block.getMetadata("natural")) {
                        FixedMetadataValue fmd = (FixedMetadataValue)md;
                        if (fmd.asString().equals("false")) {
                            hasMD = true;
                        }                        
                    }
                    //If placed, then must return item to player after breaking
                    if (hasMD) {
                        target.getInventory().addItem(new ItemStack(Material.SUGAR_CANE));
                    } else {
                        expToAdd = (long)(ExpVal.SUGARCANE_LOWER + (Math.random() * (ExpVal.SUGARCANE_UPPER - ExpVal.SUGARCANE_LOWER)));
                        fzp.addExp(expToAdd);
                    }
                    block = block.getRelative(0, 1, 0);
                }
            }
        } else if (block.getType().equals(Material.CACTUS)) {
            e.setCancelled(true);
            if (fzp.getLevel() < ExpVal.LVL_ACCESS_CACTUS) {
                target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
            } else {
                //Check block and blocks above, break whole plant and give player items as necessary
                while (block.getType().equals(Material.CACTUS)) {
                    //Break the block
                    block.setType(Material.AIR);
                    //Check whether sugar cane above is natural or placed by player
                    hasMD = false;
                    for (MetadataValue md : block.getMetadata("natural")) {
                        FixedMetadataValue fmd = (FixedMetadataValue)md;
                        if (fmd.asString().equals("false")) {
                            hasMD = true;
                        }                        
                    }
                    //If placed, then must return item to player after breaking
                    if (hasMD) {
                        target.getInventory().addItem(new ItemStack(Material.CACTUS));
                    } else {
                        expToAdd = (long)(ExpVal.CACTUS_LOWER + (Math.random() * (ExpVal.CACTUS_UPPER - ExpVal.CACTUS_LOWER)));
                        fzp.addExp(expToAdd);
                    }
                    block = block.getRelative(0, 1, 0);
                }
            }
        } else if (block.getType().equals(Material.PUMPKIN)) {
            e.setDropItems(false);
            if (fzp.getLevel() < ExpVal.LVL_ACCESS_PUMPKIN) {
                e.setCancelled(true);
                target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
            } else {
                hasMD = false;
                for (MetadataValue md : block.getMetadata("natural")) {
                    FixedMetadataValue fmd = (FixedMetadataValue)md;
                    if (fmd.asString().equals("false")) {
                        hasMD = true;
                    }                        
                }
                if (!hasMD) {
                    expToAdd = (long)(ExpVal.PUMPKIN_LOWER + (Math.random() * (ExpVal.PUMPKIN_UPPER - ExpVal.PUMPKIN_LOWER)));
                    fzp.addExp(expToAdd);
                }
            }
        } else if (block.getType().equals(Material.MELON)) {
            e.setDropItems(false);
            if (fzp.getLevel() < ExpVal.LVL_ACCESS_MELON) {
                e.setCancelled(true);
                target.sendMessage(ChatColor.RED + "You do not have permission to harvest this yet");
            } else {
                hasMD = false;
                for (MetadataValue md : block.getMetadata("natural")) {
                    FixedMetadataValue fmd = (FixedMetadataValue)md;
                    if (fmd.asString().equals("false")) {
                        hasMD = true;
                    }                        
                }
                if (!hasMD) {
                    expToAdd = (long)(ExpVal.MELON_LOWER + (Math.random() * (ExpVal.MELON_UPPER - ExpVal.MELON_LOWER)));
                    fzp.addExp(expToAdd);
                }
            }
        }
	}
}
