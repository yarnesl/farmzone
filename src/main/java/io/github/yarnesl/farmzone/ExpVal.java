package io.github.yarnesl.farmzone;

import org.bukkit.configuration.ConfigurationSection;

public class ExpVal {
    
    public static long WHEAT_LOWER;
    public static long WHEAT_UPPER;
    public static long CARROT_LOWER;
    public static long CARROT_UPPER;
    public static long POTATO_LOWER;
    public static long POTATO_UPPER;
    public static long CHICKEN_LOWER;
    public static long CHICKEN_UPPER;
    public static long BEETROOT_LOWER;
    public static long BEETROOT_UPPER;
    public static long PIG_LOWER;
    public static long PIG_UPPER;
    public static long PUMPKIN_LOWER;
    public static long PUMPKIN_UPPER;
    public static long MELON_LOWER;
    public static long MELON_UPPER;
    public static long SHEEP_LOWER;
    public static long SHEEP_UPPER;
    public static long NETHERWART_LOWER;
    public static long NETHERWART_UPPER;
    public static long SUGARCANE_LOWER;
    public static long SUGARCANE_UPPER;
    public static long COW_LOWER;
    public static long COW_UPPER;
    public static long CACTUS_LOWER;
    public static long CACTUS_UPPER;
    public static long COCOABEANS_LOWER;
    public static long COCOABEANS_UPPER;
    public static long MOOSHROOM_LOWER;
    public static long MOOSHROOM_UPPER;
    
    public static short LVL_ACCESS_WHEAT;
    public static short LVL_ACCESS_CARROT;
    public static short LVL_ACCESS_POTATO;
    public static short LVL_ACCESS_CHICKEN;
    public static short LVL_ACCESS_BEETROOT;
    public static short LVL_ACCESS_PIG;
    public static short LVL_ACCESS_PUMPKIN;
    public static short LVL_ACCESS_MELON;
    public static short LVL_ACCESS_SHEEP;
    public static short LVL_ACCESS_NETHERWART;
    public static short LVL_ACCESS_SUGARCANE;
    public static short LVL_ACCESS_COW;
    public static short LVL_ACCESS_CACTUS;
    public static short LVL_ACCESS_COCOABEANS;
    public static short LVL_ACCESS_MOOSHROOM;
    
    public static void loadExperienceValues(ConfigurationSection section) {
        WHEAT_LOWER = section.getLong("wheat.lower");
        WHEAT_UPPER = section.getLong("wheat.upper");
        CARROT_LOWER = section.getLong("carrot.lower");
        CARROT_UPPER = section.getLong("carrot.upper");
        POTATO_LOWER = section.getLong("potato.lower");
        POTATO_UPPER = section.getLong("potato.upper");
        CHICKEN_LOWER = section.getLong("chicken.lower");
        CHICKEN_UPPER = section.getLong("chicken.upper");
        BEETROOT_LOWER = section.getLong("beetroot.lower");
        BEETROOT_UPPER = section.getLong("beetroot.upper");
        PIG_LOWER = section.getLong("pig.lower");
        PIG_UPPER = section.getLong("pig.upper");
        PUMPKIN_LOWER = section.getLong("pumpkin.lower");
        PUMPKIN_UPPER = section.getLong("pumpkin.upper");
        MELON_LOWER = section.getLong("melon.lower");
        MELON_UPPER = section.getLong("melon.upper");
        SHEEP_LOWER = section.getLong("wheat.lower");
        SHEEP_UPPER = section.getLong("wheat.upper");
        NETHERWART_LOWER = section.getLong("netherwart.lower");
        NETHERWART_UPPER = section.getLong("netherwart.upper");
        SUGARCANE_LOWER = section.getLong("sugarcane.lower");
        SUGARCANE_UPPER = section.getLong("sugarcane.upper");
        COW_LOWER = section.getLong("cow.lower");
        COW_UPPER = section.getLong("cow.upper");
        CACTUS_LOWER = section.getLong("cactus.lower");
        CACTUS_UPPER = section.getLong("cactus.upper");
        COCOABEANS_LOWER = section.getLong("cocoa_beans.lower");
        COCOABEANS_UPPER = section.getLong("cocoa_beans.upper");
        MOOSHROOM_LOWER = section.getLong("mooshroom.lower");
        MOOSHROOM_UPPER = section.getLong("mooshroom.upper");
        
        LVL_ACCESS_WHEAT = (short) section.getInt("wheat.level-of-access");
        LVL_ACCESS_CARROT = (short) section.getInt("carrot.level-of-access");
        LVL_ACCESS_POTATO = (short) section.getInt("potato.level-of-access");
        LVL_ACCESS_CHICKEN = (short) section.getInt("chicken.level-of-access");
        LVL_ACCESS_BEETROOT = (short) section.getInt("beetroot.level-of-access");
        LVL_ACCESS_PIG = (short) section.getInt("pig.level-of-access");
        LVL_ACCESS_PUMPKIN = (short) section.getInt("pumpkin.level-of-access");
        LVL_ACCESS_MELON = (short) section.getInt("melon.level-of-access");
        LVL_ACCESS_SHEEP = (short) section.getInt("sheep.level-of-access");
        LVL_ACCESS_NETHERWART = (short) section.getInt("netherwart.level-of-access");
        LVL_ACCESS_SUGARCANE = (short) section.getInt("sugarcane.level-of-access");
        LVL_ACCESS_COW = (short) section.getInt("cow.level-of-access");
        LVL_ACCESS_CACTUS = (short) section.getInt("cactus.level-of-access");
        LVL_ACCESS_COCOABEANS = (short) section.getInt("cocoa_beans.level-of-access");
        LVL_ACCESS_MOOSHROOM = (short) section.getInt("mooshroom.level-of-access");
    }
}
