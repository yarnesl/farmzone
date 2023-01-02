package io.github.yarnesl.farmzone;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;

public class FZEnchants {

    public static final Enchantment GLOW = new EnchantmentWrapper("fzglow", "Glow", 1);
    
    public static void register() {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(GLOW);
        
        if (!registered) {
            registerEnchantment(GLOW);
        }
    }
    
    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null,  true);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        
        if (registered) {
            Bukkit.getLogger().info("Enchantment " + enchantment.toString() +  " already registered");
        }
    }
    
}
