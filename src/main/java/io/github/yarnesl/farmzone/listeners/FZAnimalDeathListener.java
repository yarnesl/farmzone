package io.github.yarnesl.farmzone.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import io.github.yarnesl.farmzone.ExpVal;
import io.github.yarnesl.farmzone.FZPlayer;
import io.github.yarnesl.farmzone.FarmZone;
import io.github.yarnesl.farmzone.events.FZAnimalDeathEvent;

public class FZAnimalDeathListener implements Listener {
    
    @SuppressWarnings("unused")
    private FarmZone plugin;
    
    public FZAnimalDeathListener(FarmZone plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onFZAnimalDeath(FZAnimalDeathEvent e) {
        FZPlayer fzp = e.getFZPlayer();
        long lowerVal, upperVal;
        Material dropMat = Material.AIR;
        switch(e.getTypeKilled()) {
            case CHICKEN:
                lowerVal = ExpVal.CHICKEN_LOWER;
                upperVal = ExpVal.CHICKEN_UPPER;
                dropMat = Material.CHICKEN;
                break;
            case PIG:
                lowerVal = ExpVal.PIG_LOWER;
                upperVal = ExpVal.PIG_UPPER;
                dropMat = Material.PORKCHOP;
                break;
            case COW:
                lowerVal = ExpVal.COW_LOWER;
                upperVal = ExpVal.COW_UPPER;
                dropMat = Material.BEEF;
                break;
            case SHEEP:
                lowerVal = ExpVal.SHEEP_LOWER;
                upperVal = ExpVal.SHEEP_UPPER;
                dropMat = Material.MUTTON;
                break;
            case MUSHROOM_COW:
                lowerVal = ExpVal.MOOSHROOM_LOWER;
                upperVal = ExpVal.MOOSHROOM_UPPER;
                dropMat = Material.BROWN_MUSHROOM;
                break;
            default:
                lowerVal = 0;
                upperVal = 0;
                System.out.println("Couldn't retrieve animal type, adding 0 exp");
                break;
        }
        if ((Math.random() * 100) > fzp.getAnimalDropChance()) {
            fzp.getPlayer().getInventory().addItem(new ItemStack(dropMat));
        }
        long expToAdd = (long)(lowerVal + (Math.random() * (upperVal - lowerVal)));
        System.out.println("need to add " + expToAdd + " xp to player");
        fzp.addExp(expToAdd);
        
    }

}
