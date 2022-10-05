package io.github.yarnesl.farmzone.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.yarnesl.farmzone.FZPlayer;

public class FZGuiSources extends FZGui {
    
    private FZPlayer viewer;
    
    public FZGuiSources(FZPlayer viewer) {
        this.viewer = viewer;
        this.createGui(27, "Farming Tree");
    }
    
    @Override
    protected void populateItems() {
        gui.setItem(0, createBlockableGuiItem(viewer, 1, Material.RED_STAINED_GLASS_PANE, Material.WHEAT, 1, "The first source of experience"));
        gui.setItem(1, createBlockableGuiItem(viewer, 2, Material.RED_STAINED_GLASS_PANE, Material.CARROT, 2, "Slightly better than wheat, though", "not by much..."));
        gui.setItem(2, createBlockableGuiItem(viewer, 3, Material.RED_STAINED_GLASS_PANE, Material.POTATO, 3, "Who knew there was something to be", "gained from farming so many", "potatoes"));
        gui.setItem(3, createBlockableGuiItem(viewer, 4, Material.RED_STAINED_GLASS_PANE, Material.CHICKEN_SPAWN_EGG, 4, "The first animal to be farmed.", "Seems a bit cruel, though"));
        gui.setItem(4, createBlockableGuiItem(viewer, 5, Material.RED_STAINED_GLASS_PANE, Material.BEETROOT, 5, "Minecraft's newest vegetable!"));
        gui.setItem(5, createBlockableGuiItem(viewer, 6, Material.RED_STAINED_GLASS_PANE, Material.PIG_SPAWN_EGG, 6, "Should get a decent amount", "of experience from those", "meaty thighs"));
        gui.setItem(6, createBlockableGuiItem(viewer, 7, Material.RED_STAINED_GLASS_PANE, Material.PUMPKIN, 7, "A crop that you don't have", "to replant. Genius!"));
        gui.setItem(7, createBlockableGuiItem(viewer, 8, Material.RED_STAINED_GLASS_PANE, Material.MELON, 8, "Watermelooooooooon"));
        gui.setItem(8, createBlockableGuiItem(viewer, 9, Material.RED_STAINED_GLASS_PANE, Material.SHEEP_SPAWN_EGG, 9, "If only I could harvest", "the wool off one of", "these bad boys"));
        gui.setItem(20, createBlockableGuiItem(viewer, 10, Material.RED_STAINED_GLASS_PANE, Material.NETHER_WART, 10, "A rather hellish source", "of experience"));
        gui.setItem(21, createBlockableGuiItem(viewer, 11, Material.RED_STAINED_GLASS_PANE, Material.SUGAR_CANE, 11, "Tall and pointy"));
        gui.setItem(22, createBlockableGuiItem(viewer, 12, Material.RED_STAINED_GLASS_PANE, Material.COW_SPAWN_EGG, 12, "Cow goes moo"));
        gui.setItem(23, createBlockableGuiItem(viewer, 13, Material.RED_STAINED_GLASS_PANE, Material.CACTUS, 13, "Also tall and pointy"));
        gui.setItem(24, createBlockableGuiItem(viewer, 14, Material.RED_STAINED_GLASS_PANE, Material.COCOA_BEANS, 14, "Grows on a tree"));
        gui.setItem(25, createBlockableGuiItem(viewer, 15, Material.RED_STAINED_GLASS_PANE, Material.MOOSHROOM_SPAWN_EGG, 15, "The better version", "of a cow"));
    }
    public static void onMenuClick(Inventory inv, ItemStack itemClicked, FZPlayer fzp) {
       
    }

}
