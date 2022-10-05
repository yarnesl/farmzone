package io.github.yarnesl.farmzone;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.yarnesl.farmzone.ranks.FZRank;
import io.github.yarnesl.farmzone.scoreboards.FZScoreboard;

public class FZPlayer {
    
    private String name;
    private String uuid;
    private int level;
    private int coins;
    private long exp;
    
    private FZRank rank;
    
    private boolean filled;
    private boolean existed;
    
    private Player player;
    
    private HashMap<String, String> scoreboardEntries;
    
    public FZPlayer() {}
    public FZPlayer (String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
        this.level = 1;
        this.rank = FZRank.FARMER;
        this.coins = 0;
        this.exp = 0;
        this.filled = false;
        this.existed = false;
        this.player = null;
        scoreboardEntries = new HashMap<String, String>();
        
    }
    
    /**
     * Gets information about a player from the database. This methods fills out the FZPlayer datatype
     * with the following variables: coins, rank, and level
     * @param db - the FZDatabase object that will be used to make the database query
     * @param id - the uuid of the player whose information is to be extracted from the database
     * @throws SQLException
     */
    public void loadFromDatabase(FZDatabase db, String id, Player player) throws SQLException {
        String sql = "select * from players where id=?";
        
        PreparedStatement stmt = db.getDBConnection().prepareStatement(sql);
        stmt.setString(1, id);
        
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            //name = result.getString("name");
            System.out.println("got result from DB");
            this.coins = result.getInt("coins");
            this.level = result.getInt("level");
            this.exp = result.getLong("exp");
            this.rank = FZRank.getRank(result.getString("rank"));
            this.filled = true;
            this.existed = true;
            this.player = player;
        }
    }
    
    /**
     * Stores the current state of a players FZPlayer datatype into the database. Good to call
     * this method in onDisable or when a player disconnects from the server
     * @param db - the FZDatabase object that will we be used to make the database update
     * @throws SQLException
     */
    public void saveToDatabase(FZDatabase db) throws SQLException {
        String sql;
        PreparedStatement stmt;
        
        if (this.existed) {
            sql = "UPDATE players SET `coins`=?, `level`=?, `name`=?, `rank`=?, `exp`=? WHERE `id`=?";
            
            stmt = db.getDBConnection().prepareStatement(sql);
            
            stmt.setInt(1, this.coins);
            stmt.setInt(2, this.level);
            stmt.setString(3, this.name);
            stmt.setString(4, this.rank.toString());
            stmt.setLong(5, this.exp);
            stmt.setString(6, this.uuid);
            stmt.executeUpdate();
        } else {
            sql = "insert into players values (?, ?, ?, ?, ?)";
            
            stmt = db.getDBConnection().prepareStatement(sql);
            
            stmt.setString(1, this.uuid);
            stmt.setInt(2, this.coins);
            stmt.setString(3, this.name);
            stmt.setString(4, this.rank.toString());
            stmt.setInt(5, this.level);
            stmt.executeUpdate();
        }
        
    }
    
    public void createNewPlayer(Player player) {
        this.rank = FZRank.FARMER;
        this.level = 1;
        this.coins = 0;
        this.exp = 0;
        this.filled = true;
        this.player = player;
    }
    
    public void addExp(long amount) {
        this.exp += amount;
        attemptLvlUp();
    }
    
    public long getExp() {
       return this.exp;
    }
    
    public void clearExp() {
        this.exp = 0;
        FZScoreboard.updateExp(this);
    }
    
    public void addScoreboardEntry(String entry, String value) {
        scoreboardEntries.put(entry, value);
    }
    
    public String getScoreboardEntry(String entry) {
        return scoreboardEntries.get(entry);
    }
    
    public void updateScoreboardEntry(String entry, String newValue) {
        scoreboardEntries.replace(entry, newValue);
    }
    
    public boolean isFilled() {
        return filled;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public int getCoins() {
        return this.coins;
    }
    
    public FZRank getRank() {
        return this.rank;
    }
    
    public String getUuid() {
        return this.uuid;
    }
    
    public void setLevel(int newLevel) {
        this.level = newLevel;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    private void attemptLvlUp() {
        if (this.level == 1 && this.exp >= LvlThresholds.toCarrot) {
            levelUp("carrots");
        } 
        else if (this.level == 2 && this.exp >= LvlThresholds.toPotato) {
            levelUp("potatoes");
        }
        else if (this.level == 3 && this.exp >= LvlThresholds.toChicken) {
            levelUp("chickens");
        }
        else if (this.level == 4 && this.exp >= LvlThresholds.toBeetroot) {
            levelUp("beetroots");
        }
        else if (this.level == 5 && this.exp >= LvlThresholds.toPig) {
            levelUp("pigs");
        }
        else if (this.level == 6 && this.exp >= LvlThresholds.toPumpkin) {
            levelUp("pumpkins");
        }
        else if (this.level == 7 && this.exp >= LvlThresholds.toMelon) {
            levelUp("melons");
        }
        else if (this.level == 8 && this.exp >= LvlThresholds.toSheep) {
            levelUp("sheep");
        }
        else if (this.level == 9 && this.exp >= LvlThresholds.toNetherwart) {
            levelUp("netherwarts");
        }
        else if (this.level == 10 && this.exp >= LvlThresholds.toSugarcane) {
            levelUp("sugarcanes");
        }
        else if (this.level == 11 && this.exp >= LvlThresholds.toCow) {
            levelUp("cows");
        }
        else if (this.level == 12 && this.exp >= LvlThresholds.toCactus) {
            levelUp("cacti");
        }
        else if (this.level == 13 && this.exp >= LvlThresholds.toCocoabeans) {
            levelUp("cocoa beans");
        }
        else if (this.level == 14 && this.exp >= LvlThresholds.toMooshroom) {
            levelUp("mooshroom cows");
        }
    }
    
    private void levelUp(String newHarvest) {
        this.level++;
        FZScoreboard.updateLevel(this);
        this.getPlayer().sendMessage(ChatColor.AQUA + "Congrats! You are now level " + this.level);
        this.getPlayer().sendMessage(ChatColor.AQUA + "You now have the ability to harvest " + newHarvest + "!");
        this.getPlayer().sendMessage(ChatColor.AQUA + "As a reward, you have received the following gifts:");
    }
    

}
