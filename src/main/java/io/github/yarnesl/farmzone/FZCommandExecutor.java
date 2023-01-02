package io.github.yarnesl.farmzone;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scoreboard.DisplaySlot;

import io.github.yarnesl.farmzone.events.FZPlotMineCreateEvent;
import io.github.yarnesl.farmzone.events.FZPlotMineRemoveEvent;
import io.github.yarnesl.farmzone.guis.FZGuiMain;
import io.github.yarnesl.farmzone.guis.FZGuiShopMain;
import io.github.yarnesl.farmzone.ranks.FZRank;
import io.github.yarnesl.farmzone.scoreboards.FZScoreboard;

public class FZCommandExecutor implements CommandExecutor {
	
	private final FarmZone plugin;
	
	public FZCommandExecutor(FarmZone plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
		    Player target = (Player)sender;
		    FZPlayer fzp = plugin.extractPlayer(target.getUniqueId().toString());
		    if (fzp == null) {
		        System.err.println("couldnt retrieve fzp");
		        return false;
		    }
    	    if (cmd.getName().equalsIgnoreCase("farmzone")) {
    			if (args.length >= 1) {
    			    if (args[0].equalsIgnoreCase("info")) {
    			        target.sendMessage(ChatColor.BLUE + "========================");
    			        target.sendMessage(ChatColor.AQUA + "Username: " + ChatColor.DARK_AQUA + target.getName());
    			        target.sendMessage(ChatColor.AQUA + "Time On Server: " + ChatColor.DARK_AQUA + target.getStatistic(Statistic.TOTAL_WORLD_TIME));
    			        target.sendMessage(ChatColor.BLUE + "========================");
    			    } else if (args[0].equalsIgnoreCase("level")) {
    			        if (args.length >= 2) {
    			            if (args[1].equalsIgnoreCase("set")) {
    			                if (args.length >= 3) {
    			                    int levelParam = Integer.valueOf(args[2]);
    			                    int prevValue = fzp.getLevel();
    			                    fzp.setLevel(levelParam);
    			                    target.sendMessage("Level updated: " + prevValue + " --> " + levelParam);
    			                    FZScoreboard.updateLevel(fzp);
    			                    return true;
    			                } else {
    			                    target.sendMessage("Wrong args");
    			                }
    			            }
    			        } else {
    			            target.sendMessage("Wrong args");
    			        }
    			    } else if (args[0].equalsIgnoreCase("rank")) {
    			        if (args.length >= 2) {
    			            if (args[1].equalsIgnoreCase("set")) {
    			                if (args.length >= 3) {
    			                    switch(args[2]) {
    			                        case "farmer":
    			                            fzp.setRank(FZRank.FARMER);
    			                            FZScoreboard.updateRank(fzp);
    			                            break;
    			                        case "rancher":
    			                            fzp.setRank(FZRank.RANCHER);
    			                            FZScoreboard.updateRank(fzp);
    			                            break;
    			                        case "botanist":
    			                            fzp.setRank(FZRank.BOTANIST);
    			                            FZScoreboard.updateRank(fzp);
    			                            break;
    			                        case "owner":
    			                            fzp.setRank(FZRank.OWNER);
    			                            FZScoreboard.updateRank(fzp);
    			                            break;
    			                        default:
    			                            target.sendMessage(ChatColor.RED + "Parameter not recognized");
    			                            break;
    			                    }
    			                }
    			            }
    			        } else {
    			            target.sendMessage("Wrong args");
    			        }
    			    } else if (args[0].equalsIgnoreCase("clear")) {
    			        fzp.clearExp();
    			    } else if (args[0].equalsIgnoreCase("scoreboard")) {
    			        if (args.length == 2) {
    			            if (args[1].equalsIgnoreCase("show")) {
    			                target.getScoreboard().getObjective("FZPlayerBoard").setDisplaySlot(DisplaySlot.SIDEBAR);
    			            } else if (args[1].equalsIgnoreCase("hide")) {
    			                target.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    			            } else {
    			                target.sendMessage(ChatColor.RED + "Bad parameter");
    			                return false;
    			            }
    			        }
    			    } else if (args[0].equalsIgnoreCase("menu")) {
    			        target.openInventory(new FZGuiMain(plugin).getMenu());
    			    } else if (args[0].equalsIgnoreCase("shop")) {
    			        target.openInventory(new FZGuiShopMain(plugin).getMenu());
    			    }
    			}
    			return true;
    		} else if (cmd.getName().equalsIgnoreCase("fzinfo")) {
    		    target.sendMessage("Current Rank: " + fzp.getRank());
                target.sendMessage("Current Level: " + fzp.getLevel());
                target.sendMessage("Current Wallet: " + fzp.getCoins());
                return true;
    		} else if (cmd.getName().equalsIgnoreCase("plotmine")) {
    		    if (args.length >= 1) {
    		        if (args[0].equalsIgnoreCase("create")) {
    		            /* Check if the player has existing plotmine on record */
    		            ArrayList<MetadataValue> mlist = new ArrayList<MetadataValue>();
    		            mlist.addAll(target.getMetadata("fzplotmine"));
    		            if (mlist.size() == 0) {
    		                FZPlotMineCreateEvent event = new FZPlotMineCreateEvent(target);
    		                Bukkit.getPluginManager().callEvent(event);
    		                target.sendMessage("creating new plotmine object");
    		            } else {
    		                target.sendMessage(ChatColor.RED + "You already have a registered PlotMine.");
    		            }
    		        } else if (args[0].equalsIgnoreCase("remove")) {
    		            ArrayList<MetadataValue> mlist = new ArrayList<MetadataValue>();
                        mlist.addAll(target.getMetadata("fzplotmine"));
                        if (mlist.size() == 0) {
                            target.sendMessage(ChatColor.RED + "You do not have a registered PlotMine to remove.");
                        } else {                  
                            FixedMetadataValue fmd = (FixedMetadataValue) mlist.get(0);
                            if (fmd.value() instanceof PlotMine) {
                                PlotMine pm = (PlotMine)fmd.value();
                                FZPlotMineRemoveEvent event = new FZPlotMineRemoveEvent(pm, target);
                                Bukkit.getPluginManager().callEvent(event);
                                target.sendMessage("removing plotmine");
                            }
                        }
    		        }
    		    } else {
    		        target.sendMessage("Must pass at least 1 argument");
    		        return false;
    		    }
    		}
    	    return true;
		} else {
		    System.err.println("Must be a player to execute this command");
		    return true;
		}
	}

}
