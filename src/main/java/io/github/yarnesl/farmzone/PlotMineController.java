package io.github.yarnesl.farmzone;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import io.github.yarnesl.farmzone.guis.FZGuiPlotMine;

public class PlotMineController {
    
    private static HashMap<UUID, PlotMine> pmap;
    public static FarmZone plugin;
    public static FZDatabase db;
    
    public PlotMineController(FarmZone plugin, FZDatabase fzdb) {
        pmap = new HashMap<UUID, PlotMine>();
        PlotMineController.plugin = plugin;
        db = fzdb;
    }
    
    public static boolean playerHasPlotMine(UUID id) {
        return pmap.containsKey(id);
    }
    
    public static PlotMine getPlotMine(UUID id) {
        return (PlotMine)pmap.get(id);
    }
    
    public static HashMap<UUID, PlotMine> getPMap() {
        return pmap;
    }
    
    public static void addPlotMine(UUID id, PlotMine pm) {
        FZGuiPlotMine.addNewPlotMine(id, pm);
        pmap.put(id, pm);
    }
    
    public static void removePlotMine(UUID uuid) {
        try {
            removePlotMineFromDatabase(uuid.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pmap.remove(uuid);
    }
    
    public static void removePlotMineFromDatabase(String uuid) throws SQLException {
        String sql = "delete from plotmines where `playerid`=?";
        
        PreparedStatement stmt = db.getDBConnection().prepareStatement(sql);
        stmt.setString(1, uuid);
        stmt.executeUpdate();
    }
    
    /**
     * Gets information about a plotmine from the database. This methods creates a PlotMine object
     * @param db - the FZDatabase object that will be used to make the database query
     * @param id - the uuid of the player whose owns the plotmine that is to be extracted from the database
     * @throws SQLException
     */
    public static void loadPlotMinesFromDatabase(World world) throws SQLException {
        UUID playerId;
        int size;
        boolean hasPhysicalPresence;
        int mirrors[] = new int[2];
        int x, y, z;
        Location loc;
        PlotMine pm;
        String villagerID;
          
        String sql = "select * from plotmines";
        
        PreparedStatement stmt = db.getDBConnection().prepareStatement(sql);
        
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            /* Store temp data about each PlotMine */
            System.out.println("got result from DB");
            playerId = UUID.fromString(result.getString("playerid"));
            size = result.getInt("size");
            hasPhysicalPresence = result.getBoolean("hasPhysicalPresence");
            mirrors[0] = result.getInt("xmirror");
            mirrors[1] = result.getInt("zmirror");
            villagerID = result.getString("villagerID");
            x = result.getInt("xcoord");
            y = result.getInt("ycoord");
            z = result.getInt("zcoord");
                
            loc = new Location(world, x, y, z);
            
            /* Create new object and store in list */
            pm = new PlotMine(plugin, playerId, size, hasPhysicalPresence, loc, mirrors, villagerID);
            addPlotMine(playerId, pm);
            
        }       
        
    }
    
    /**
     * Stores the current state of a PlotMine object into the database. Good to call
     * this method in onDisable()
     * @param db - the FZDatabase object that will we be used to make the database update
     * @param pm - the PlotMine object to store into the database
     * @throws SQLException
     */
    public static void savePlotMinesToDatabase() throws SQLException {
 
        pmap.forEach((pid, pm) -> {
            
            //Then, save the appropriate data to the database
            String sql;
            PreparedStatement stmt;
            if (pm.existedInDB()) {
                sql = "UPDATE plotmines SET `size`=?, `hasPhysicalPresence`=?, `xmirror`=?, `zmirror`=?, `villagerID`=?, `xcoord`=?, `ycoord`=?, `zcoord`=? WHERE `playerid`=?";
                Bukkit.getLogger().info("updating");
                try {
                    stmt = db.getDBConnection().prepareStatement(sql);                
                    stmt.setInt(1, pm.getSize());
                    stmt.setBoolean(2, pm.isPhysicalPlotMine());
                    stmt.setInt(3, pm.getMirrors()[0]);
                    stmt.setInt(4, pm.getMirrors()[1]);
                    stmt.setString(5,pm.getVillagerID());
                    stmt.setInt(6, pm.getX());
                    stmt.setInt(7, pm.getY());
                    stmt.setInt(8, pm.getZ());
                    stmt.setString(9, pm.getUuid().toString());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                sql = "insert into plotmines values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                Bukkit.getLogger().info("inserting");
                try {
                    stmt = db.getDBConnection().prepareStatement(sql);
                    
                    stmt.setString(1, pm.getUuid().toString());
                    stmt.setInt(2, pm.getSize());
                    stmt.setBoolean(3, pm.isPhysicalPlotMine());
                    stmt.setInt(4, pm.getMirrors()[0]);
                    stmt.setInt(5, pm.getMirrors()[1]);
                    stmt.setString(6, pm.getVillagerID());
                    stmt.setInt(7, pm.getX());
                    stmt.setInt(8, pm.getY());
                    stmt.setInt(9, pm.getZ());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });        
    }
}
