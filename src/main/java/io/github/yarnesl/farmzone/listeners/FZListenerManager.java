package io.github.yarnesl.farmzone.listeners;

import org.bukkit.Bukkit;

import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.guis.FZGuiMain;
import io.github.yarnesl.farmzone.guis.FZGuiPlotMine;
import io.github.yarnesl.farmzone.guis.FZGuiPlotMineFillerMenu;
import io.github.yarnesl.farmzone.guis.FZGuiPlotMineValueMenu;
import io.github.yarnesl.farmzone.guis.FZGuiShopMain;

public class FZListenerManager {
    
    FarmZone plugin;
    
    public FZListenerManager(FarmZone plugin) {
        this.plugin = plugin;
    }
    
    public void registerListeners() {
        //Register event listeners
        Bukkit.getServer().getPluginManager().registerEvents(new FZListeners(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZAnimalListener(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZAnimalDeathListener(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZAnimalSpawnListener(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZPlotMineCreateListener(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZPlotMineConstructListener(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZPlotMineFillListener(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZPlotMineRemoveListener(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZPlayerInteractWithEntityListener(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZGuiPlotMine(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZGuiPlotMineFillerMenu(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZGuiPlotMineValueMenu(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZGuiMain(plugin), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FZGuiShopMain(plugin), plugin);
        
    }
}
