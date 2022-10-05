package io.github.yarnesl.farmzone;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class LvlThresholds {
    
    public static long toCarrot;
    public static long toPotato;
    public static long toChicken;
    public static long toBeetroot;
    public static long toPig;
    public static long toPumpkin;
    public static long toMelon;
    public static long toSheep;
    public static long toNetherwart;
    public static long toSugarcane;
    public static long toCow;
    public static long toCactus;
    public static long toCocoabeans;
    public static long toMooshroom;
    
    public static ArrayList<ItemStack> lvl2Gifts;
    public static ArrayList<ItemStack> lvl3Gifts;
    public static ArrayList<ItemStack> lvl4Gifts;
    public static ArrayList<ItemStack> lvl5Gifts;
    public static ArrayList<ItemStack> lvl6Gifts;
    public static ArrayList<ItemStack> lvl7Gifts;
    public static ArrayList<ItemStack> lvl8Gifts;
    public static ArrayList<ItemStack> lvl9Gifts;
    public static ArrayList<ItemStack> lvl10Gifts;
    public static ArrayList<ItemStack> lvl11Gifts;
    public static ArrayList<ItemStack> lvl12Gifts;
    public static ArrayList<ItemStack> lvl13Gifts;
    public static ArrayList<ItemStack> lvl14Gifts;
    public static ArrayList<ItemStack> lvl15Gifts;
    
    
    public static void loadLvlThresholds(ConfigurationSection section) {
        toCarrot = section.getLong("to-carrot");
        toPotato = section.getLong("to-potato");
        toChicken = section.getLong("to-chicken");
        toBeetroot = section.getLong("to-beetroot");
        toPig = section.getLong("to-pig");
        toPumpkin = section.getLong("to-pumpkin");
        toMelon = section.getLong("to-melon");
        toSheep = section.getLong("to-sheep");
        toNetherwart = section.getLong("to-netherwart");
        toSugarcane = section.getLong("to-sugarcane");
        toCow = section.getLong("to-cow");
        toCactus = section.getLong("to-cactus");
        toCocoabeans = section.getLong("to-cocoabeans");
        toMooshroom = section.getLong("to-mooshroom");
        
        lvl2Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.CARROT, 8));
            }
        };
        lvl3Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.POTATO, 8));
            }
        };
        lvl4Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.CHICKEN_SPAWN_EGG, 8));
            }
        };
        lvl5Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.BEETROOT_SEEDS, 8));
            }
        };
        lvl6Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.PIG_SPAWN_EGG, 8));
            }
        };
        lvl7Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.PUMPKIN_SEEDS, 8));
            }
        };
        lvl8Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.MELON_SEEDS, 8));
            }
        };
        lvl9Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.SHEEP_SPAWN_EGG, 8));
            }
        };
        lvl10Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.NETHER_SPROUTS, 8));
            }
        };
        lvl11Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.SUGAR_CANE, 8));
            }
        };
        lvl12Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.COW_SPAWN_EGG, 8));
            }
        };
        lvl13Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.CACTUS, 8));
            }
        };
        lvl14Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.COCOA_BEANS, 8));
            }
        };
        lvl15Gifts = new ArrayList<ItemStack>() {
            {
                add(new ItemStack(Material.MOOSHROOM_SPAWN_EGG, 8));
            }
        };

    }

}
