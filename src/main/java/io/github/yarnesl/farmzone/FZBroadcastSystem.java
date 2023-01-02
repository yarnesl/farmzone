package io.github.yarnesl.farmzone;

import java.util.Arrays;

import org.bukkit.Bukkit;

public class FZBroadcastSystem {
    
    FarmZone plugin;
    
    public FZBroadcastSystem(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    public void startBroadcastLoop(long delay, long period, String...message) {
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            @Override
            public void run() {
                Arrays.asList(message).forEach((String str) -> {
                    plugin.getServer().broadcastMessage(str);
                });                
            }
            
        }, delay, period);
    }

}
