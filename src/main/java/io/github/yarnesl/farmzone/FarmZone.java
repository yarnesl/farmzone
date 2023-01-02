package io.github.yarnesl.farmzone;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.yarnesl.farmzone.listeners.FZListenerManager;
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
    
    /**
     * A custom FileConfiguration that will store all serialized data that is
     * created when the server stops, and should be reloaded on startup
     */
    FileConfiguration customConfig;
    File customConfigFile;
    
    /**
     * A reference to the world that the player plots are contained in. This field
     * gets populated in the onEnable() method and is primarily used for loading
     * location values (such as with plotmines) from the database on startup
     */
    
    FarmZone plugin;
        
	@Override
	public void onEnable() {  
	    plugin = this;
	    
	    //Register custom enchants
	    FZEnchants.register();
	    
	    //Register custom commands
	    FZCommandExecutor fzce = new FZCommandExecutor(this);
	    this.getCommand("farmzone").setExecutor(fzce);
	    this.getCommand("fzinfo").setExecutor(fzce);
	    this.getCommand("plotmine").setExecutor(fzce);
	    
	    //Register ConfigurationSerializable classes
	    //ConfigurationSerialization.registerClass(PlotMine.class);
	   
	    //Register all listeners used in this plugin
	    FZListenerManager listMan = new FZListenerManager(this);
	    listMan.registerListeners();
	    
	    //Load information from config.yml, if not existing already
	    this.saveDefaultConfig();
	    
	    //Load the plot world if not already, so that it can be used when
	    //loading in the plotmines from the database
	    World world = getServer().createWorld(new WorldCreator("world"));
	    //createCustomConfig();
	    
	    //Load all possible serialized data
        /*ConfigurationSection sect = customConfig.getConfigurationSection("plotmines");
        if (sect != null) {
            Set<String> tmap = sect.getKeys(false);
            tmap.forEach((str) -> {
                Map<String, Object> m= sect.getConfigurationSection(str).getValues(false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (plugin.isEnabled()) {
                            PlotMine pm = new PlotMine(m);
                            FZGuiPlotMine.addNewPlotMine(pm.getUuid(), pm);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(plugin, 100L, 100L);
            });
        }*/
            
	    
	    //Populate experience values from config.yml
	    ExpVal.loadExperienceValues(getConfig().getConfigurationSection("experience"));
	    LvlThresholds.loadLvlThresholds(getConfig().getConfigurationSection("level-thresholds"));
	    
	    //First-time configuration of the database
		fzdb = new FZDatabase(this, this.getConfig().getConfigurationSection("database"));
		if (fzdb.attemptConnection()) {
		    getLogger().info("Connection to Database Successful!");
		} else {
		    getLogger().info("Critical error. Connection to database unsuccessful");
		}
		//Load all existing PlotMines from database
        new PlotMineController(this, fzdb);
        try {
            PlotMineController.loadPlotMinesFromDatabase(world);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		//In case the server was reloaded and not starting from scratch,
        //get list of all players online and add them to the list
		FZPlayer fzp;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
		    getLogger().info("online player: " + player.getName());
		    fzp = addActivePlayer(player);
		    FZScoreboard.createFZPlayerScoreboard(fzp);
		    //Register a PlotMine to this player if they have one in existence
	        if (PlotMineController.playerHasPlotMine(player.getUniqueId())) {
	            PlotMine pm = PlotMineController.getPlotMine(player.getUniqueId());
	            plugin.getLogger().info("tethering player " + player.getUniqueId().toString() + " to serialized plotmine");
    	        player.setMetadata("fzplotmine", new FixedMetadataValue(this, pm));
	        }
		}	
		
		//Start up to internal broadcasting system
		FZBroadcastSystem fzbs = new FZBroadcastSystem(this);
		fzbs.startBroadcastLoop(1200L, 6000, ChatColor.AQUA + "Currently playing " + ChatColor.DARK_PURPLE + "FarmZone");
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
		
		//Save active PlotMines to database
		try {
            PlotMineController.savePlotMinesToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		//Write all persistent objects to data.yml
		/*FZGuiPlotMine.getPMap().forEach((uuid, pm) -> {
		    if (pm.isPhysicalPlotMine() == false) {
		        pm.unload();
		    }
		    customConfig.set("plotmines." + uuid.toString(), pm.serialize());
		});
		customConfig.set("activePlayers", activePlayers.size());
		saveCustomConfig();*/
	}
	
	/*public FileConfiguration getCustomConfig() {
	    return this.customConfig;
	}
	
	public void saveCustomConfig() {
	    try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void createCustomConfig() {
	    customConfigFile = new File(getDataFolder(), "data.yml");
	    if (!customConfigFile.exists()) {
	        customConfigFile.getParentFile().mkdirs();
	        saveResource("data.yml", false);
	    }
	    
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	}*/
	
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
