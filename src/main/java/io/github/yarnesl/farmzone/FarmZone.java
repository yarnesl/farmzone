package io.github.yarnesl.farmzone;

import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.yarnesl.farmzone.guis.FZGuiManager;
import io.github.yarnesl.farmzone.scoreboards.FZScoreboard;

public class FarmZone extends JavaPlugin {
    
    /**
     * HashMap, keyed on a player's uuid (as string) that contains the FZPlayer
     * objects of every player currently active on the server.
     */
    HashMap<String, FZPlayer> activePlayers = new HashMap<String, FZPlayer>();
    
    /**
     * Reference to the FZDatabase object that contains the JDBC connection and
     * relevant methods for accessing and manipulating the SQL database.
     */
    FZDatabase fzdb;	
    
	@Override
	public void onEnable() {  
	    //Register custom commands
	    FZCommandExecutor fzce = new FZCommandExecutor(this);
	    this.getCommand("farmzone").setExecutor(fzce);
	    this.getCommand("fzinfo").setExecutor(fzce);
	    
	    //Register event listeners
	    getServer().getPluginManager().registerEvents(new FZListeners(this), this);
	    getServer().getPluginManager().registerEvents(new FZGuiManager(this), this);
	    
	    //Load information from config.yml, if not existing already
	    this.saveDefaultConfig();
	    
	    //Populate experience values from config.yml
	    ExpVal.loadExperienceValues(getConfig().getConfigurationSection("experience"));
	    LvlThresholds.loadLvlThresholds(getConfig().getConfigurationSection("level-thresholds"));
	    
	    //First-time configuration of the database
		fzdb = new FZDatabase(this, this.getConfig().getConfigurationSection("database"));
		if (fzdb.attemptConnection()) {
		    getLogger().info("Connection to Database Successful!");
		}
		//In case the server was reloaded and not starting from scratch,
        //get list of all players online and add them to the list
		FZPlayer fzp;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
		    getLogger().info("online player: " + player.getName());
		    fzp = addActivePlayer(player);
		    FZScoreboard.createScoreboard(player, fzp);
		}	
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Closing Farmzone plugin");
		activePlayers.forEach((uuid, fzp) -> {
            try {
                fzp.saveToDatabase(fzdb);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
	}
	
	public FZPlayer addActivePlayer(Player player) {
        String name = player.getName();
        String uuid = player.getUniqueId().toString();
        
        FZPlayer fzp = new FZPlayer(name, uuid);
        try {
            fzp.loadFromDatabase(fzdb, uuid, player);
            if (!fzp.isFilled()) {
                //Signals a new player that wasn't in the database
                fzp.createNewPlayer(player);
                fzp.saveToDatabase(fzdb);
                getServer().broadcastMessage(ChatColor.AQUA + "Welcome new player " + 
                        ChatColor.DARK_AQUA + player.getName() + ChatColor.AQUA + " to Farmzone!");
            }
            activePlayers.put(uuid, fzp);
            return fzp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public FZPlayer removeActivePlayer(String uuid) {
	    FZPlayer fzp = activePlayers.remove(uuid);
	    try {
            fzp.saveToDatabase(fzdb);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    return fzp;
	}
	
	public FZPlayer extractPlayer(String uuid) {
	    return activePlayers.get(uuid);
	}

	
	

}
