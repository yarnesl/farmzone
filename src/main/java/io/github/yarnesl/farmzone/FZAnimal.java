package io.github.yarnesl.farmzone;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;

public class FZAnimal {
    
    private Entity entity;
    private ArmorStand armorStand1;
    private ArmorStand armorStand2;
    private Slime slime;
    
    int stack;
      
    public FZAnimal(Entity who) {
        this.stack = 1;
        this.entity = who;
        this.armorStand1 = (ArmorStand) who.getWorld().spawnEntity(who.getLocation(), EntityType.ARMOR_STAND);
        this.armorStand2 = (ArmorStand) who.getWorld().spawnEntity(who.getLocation(), EntityType.ARMOR_STAND);
        this.slime = (Slime) who.getWorld().spawnEntity(who.getLocation(), EntityType.SLIME);
        this.armorStand2.setCustomName("Stack: " + this.stack);
        this.armorStand1.setCustomNameVisible(true);
        this.armorStand2.setCustomNameVisible(true);
        this.slime.setInvisible(true);
        this.slime.setSize(1);
        this.slime.setAI(false);
        this.slime.setSilent(true);
        this.slime.setInvulnerable(true);
        armorStand1.setSmall(true);
        armorStand1.setInvulnerable(true);
        armorStand1.setMarker(true);
        armorStand1.setInvisible(true);
        armorStand2.setSmall(true);
        armorStand2.setInvulnerable(true);
        armorStand2.setMarker(true);
        armorStand2.setInvisible(true);
        
        updateHealthString(((LivingEntity)this.entity).getHealth());
        updateStackString();

        who.addPassenger(slime);
        who.addPassenger(armorStand1);
        slime.addPassenger(armorStand2); 
    }
    
    public void killPassengers() {
        System.out.println("called the mathod");
        armorStand1.remove();
        armorStand2.remove();
        slime.remove();
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public void updateHealth() {
        
    }
    
    public int getStackNumber() {
        return this.stack;
    }
    
    /**
     * Add "numToAdd" animals to the stack and return the new value of the stack.
     * @param numToAdd = number of animals to add to current stack
     * @return New stack value
     */
    public int addToStack(int numToAdd) {
        this.stack += numToAdd;
        updateStackString();
        return this.stack;
    }

    /**
     * Take one animal out of the stack and return the new value of the stack.
     * @return New stack value
     */
    public int decrementStack() {
        this.stack--;
        updateStackString();
        return this.stack;
    }
    
    public void updateHealthString(double newHealth) {
        StringBuilder healthString = new StringBuilder();
        double baseHealth = ((Attributable)this.entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double healthPercent = newHealth / baseHealth;
        System.out.println("Base health: " + baseHealth + "\nCurrent Health: " + newHealth + "\nHealth Percent: " + healthPercent + "\n*10: " + (int)(healthPercent*10));
        int tenVal = 10;
        if (healthPercent < 1) {
            tenVal = (int)(healthPercent * 10);
        }
        for (int i = 0; i < tenVal; i++) {
            healthString.append(ChatColor.GREEN + "■");
        }
        for (int i = tenVal; i < 10; i++) {
            healthString.append(ChatColor.RED + "■");
        }
        armorStand1.setCustomName(healthString.toString());
    }
    
    public void updateStackString() {
        String stackString = ChatColor.AQUA + "Stack: " + ChatColor.DARK_AQUA + this.stack;
        armorStand2.setCustomName(stackString);
    }

}
