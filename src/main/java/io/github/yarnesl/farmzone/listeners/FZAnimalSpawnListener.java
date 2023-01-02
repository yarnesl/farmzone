package io.github.yarnesl.farmzone.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

import io.github.yarnesl.farmzone.FZAnimal;
import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.events.FZAnimalSpawnEvent;

public class FZAnimalSpawnListener implements Listener {
    
    private FarmZone plugin;
    
    public FZAnimalSpawnListener(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onFZAnimalSpawnEvent(FZAnimalSpawnEvent e) {
        FZAnimal animal = new FZAnimal(e.getEntity());
        FixedMetadataValue fmd = new FixedMetadataValue(plugin, (Object)animal);
        e.getEntity().setMetadata("FZAnimalIdentity", fmd);
    }

}
