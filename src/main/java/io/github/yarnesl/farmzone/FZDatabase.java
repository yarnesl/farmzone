package io.github.yarnesl.farmzone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.configuration.ConfigurationSection;

public class FZDatabase {
    
    private String url;
    private String host;
    private int port;
    private String db_name;
    private String username;
    private String password;
    
    private Connection db;
    private final FarmZone plugin;
    
    public FZDatabase(FarmZone plugin) {
        this.plugin = plugin;
    }
    public FZDatabase(FarmZone plugin, ConfigurationSection section) {
        this.plugin = plugin;
        loadDatabaseFromFile(section);
    }
    
    public void loadDatabaseFromFile(ConfigurationSection section) {
        this.url = section.getString("url");
        this.host = section.getString("host");
        this.port = section.getInt("port");
        this.db_name = section.getString("database");
        this.username = section.getString("username");
        this.password = section.getString("password");
    }
    
    public boolean attemptConnection() {
        try {
            db = DriverManager.getConnection(getConstructedUrl(), getUsername(), getPassword());
            return true;
        } catch (SQLException ex) {
            plugin.getLogger().info(ex.getMessage());
            return false;
        }
    }
    
    public Connection getDBConnection() {
        return this.db;
    }
    
    public String getURL() {
        return this.url;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getDBName() {
        return this.db_name;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Constructs the url in the form that jbdc expects in the getConnection() call, which
     * includes information about the host, port, and specific database queried
     * @return the constructed url
     */
    public String getConstructedUrl() {
        return "" + this.url + this.host + ":" + this.port + "/" + this.db_name;
    }
    

}
