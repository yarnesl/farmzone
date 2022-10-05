package io.github.yarnesl.farmzone;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import io.github.yarnesl.farmzone.guis.FZGuiMain;
import io.github.yarnesl.farmzone.guis.FZGuiShopMain;
import io.github.yarnesl.farmzone.scoreboards.FZScoreboard;
import net.md_5.bungee.api.ChatColor;

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
    			        new FZGuiMain().openInventory((HumanEntity)target);
    			    } else if (args[0].equalsIgnoreCase("shop")) {
    			        new FZGuiShopMain().openInventory((HumanEntity)target);
    			    }
    			}
    			return true;
    		} else if (cmd.getName().equalsIgnoreCase("fzinfo")) {
    		    target.sendMessage("Current Rank: " + fzp.getRank());
                target.sendMessage("Current Level: " + fzp.getLevel());
                target.sendMessage("Current Wallet: " + fzp.getCoins());
    		}
		} else {
		    System.err.println("Must be a player to execute this command");
		}
		return false;
	}

}
