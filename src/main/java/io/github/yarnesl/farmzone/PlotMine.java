package io.github.yarnesl.farmzone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.yarnesl.farmzone.guis.FZGuiPlotMine;

public class PlotMine {
    
    /* Of format [x-mirror, z-mirror] */
    private int mirrors[];
    private int size;
    
    private String stringWorldName;
    
    private Inventory gui;
    private Location loc;
    private UUID playerId;
    
    private boolean hasPhysicalPresence;
    private boolean showParticles;
    private boolean existed;
    
    public FarmZone plugin;
    private String errorMessage;
    
    /* Physical Variables, uninitialized until constructed */
    private Villager villager;
    private UUID villagerUUID;
    private ArrayList<Location> particles;
    
    //The material that the plotmine is set to FILL with
    private Material fillMat;
    
    /* 
     * A list of materials that will spawn in the portion of the
     * plotmine that is set to spawn valuable blocks. Each material
     * currently in the list will make up exactly 1/(list_size) percent
     * of this valuable portion.
     */
    private HashSet<Material> valuableMats;
    
    
    
    /**
     * The constructor to use when creating a new PlotMine from the command
     * @param loc - The Location at which to construct the plotmine
     * @param plugin - The plugin creating the PlotMine
     * @param pid - The UUID of the player who owns the plotmine
     */
    public PlotMine(Location loc, FarmZone plugin, UUID pid) {
        this.playerId = pid;
        this.size = GlobalVariables.MIN_PLOTMINE_SIZE;
        this.plugin = plugin;
        if (loc.getYaw() >= -45.0f && loc.getYaw() < 45.0f) {
            //SOUTH
            this.loc = loc.add(0, 0, 1.0);
            mirrors = new int[] {-1,1};
        } else if (loc.getYaw() >= 45.0f && loc.getYaw() < 135.0f) {
            //WEST
            this.loc = loc.add(-1.0, 0, 0);
            mirrors = new int[] {-1,-1};
        } else if ((loc.getYaw() >= 135.0f && loc.getYaw() <= 180.0f) || (loc.getYaw() >= -180.0f && loc.getYaw() < -135.0f)) {
            //NORTH
            this.loc = loc.add(0, 0, -1.0);
            mirrors = new int[] {1,-1};
        } else if (loc.getYaw() >= -135.0f && loc.getYaw() < -45.0f) {
            //EAST
            this.loc = loc.add(1.0, 0, 0);
            mirrors = new int[] {1,1};
        } else {
            System.out.println("Critical error creating plotmine");
        }
        
        Location blockLoc = new Location(this.loc.getWorld(), this.loc.getBlockX(), this.loc.getBlockY(), this.loc.getBlockZ());
        this.loc = blockLoc;
        
        this.stringWorldName = loc.getWorld().toString();
        this.hasPhysicalPresence = false;
        this.showParticles = false;
        this.existed = false;
        this.valuableMats = new HashSet<Material>();
        
    }
    
    /**
     * The constructor to use when loading a PlotMine from the database. That is
     * to say, the PlotMine existed before a server reset.
     * @param plugin
     * @param playerId
     * @param size
     * @param hasPhysicalPresence
     * @param loc
     * @param mirrors
     */
    public PlotMine(FarmZone plugin, UUID playerId, int size, boolean hasPhysicalPresence, Location loc, int mirrors[], String villagerID) {
        this.plugin = plugin;
        this.playerId = playerId;
        this.size = size;
        this.hasPhysicalPresence = hasPhysicalPresence;
        this.loc = loc;
        this.mirrors = mirrors;
        this.showParticles = false;
        this.existed = true;
        this.villagerUUID = UUID.fromString(villagerID);
        if (!this.hasPhysicalPresence) {
            this.villager = (Villager) Bukkit.getServer().getEntity(this.villagerUUID);
            this.villager.setMetadata("associatedFZPlotmine", new FixedMetadataValue(this.plugin, this));
        }
        this.valuableMats = new HashSet<Material>();
    }   
    
    public void setInv(Inventory inv) {
        this.gui = inv;
    }
    
    public int getX() {
        return this.loc.getBlockX();
    }
    
    public int getY() {
        return this.loc.getBlockY();
    }
    
    public int getZ() {
        return this.loc.getBlockZ();
    }
    
    public int getSize() {
        return this.size;
    }
    
    public String getWorldName() {
        return this.stringWorldName;
    }
    
    public boolean isPhysicalPlotMine() {
        return hasPhysicalPresence;
    }
    
    public boolean existedInDB() {
        return this.existed;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }
    
    public Location getLoc() {
        return this.loc;
    }
    
    public Inventory getInv() {
        return this.gui;
    }
    
    public Material getFillMat() {
        return this.fillMat;
    }
    
    public void setFillMat(Material newMat) {
        this.fillMat = newMat;
    }
    
    public UUID getUuid() {
        return this.playerId;
    }
    
    public int[] getMirrors() {
        return this.mirrors;
    }
    
    public boolean isShowingParticles() {
        return this.showParticles;
    }
    
    public String getVillagerID() {
        return this.villagerUUID.toString();
    }
    
    public void constructPhysicalPlotMine() {
        /* Build the PlotMine */        
        Material type;
        Location curLoc;
        
        for (int x = 0; x < size+2; x++) {
            for (int y = 0; y < size+2; y++) {
                for (int z = 0; z < size+2; z++) {
                    type = Material.AIR;
                    if (y != 0) {
                        if (x == 0 || x == size+1) {
                            type = Material.BEDROCK;
                        } else if (z == 0 || z == size+1) {
                            type = Material.BEDROCK;
                        } else if (y == size+1) {
                            type = Material.BEDROCK;
                        } 
                    }
                    curLoc = new Location(loc.getWorld(), getX()+(x*mirrors[0]), getY()-y, getZ()+(z*mirrors[1]));
                    curLoc.getBlock().setType(type);
                }
            }
        }     
        
        /* Remove the villager/previous inventory */
        if (this.villager != null) 
            this.villager.remove();
        
        /* Create a chest that allows control over PlotMine */
        loc.getBlock().setType(Material.CHEST);
        if (loc.getBlock() instanceof Chest) {
            Chest chest = (Chest) loc.getBlock();
            chest.setCustomName("Plotmine " + playerId.toString());
        }
        
        this.hasPhysicalPresence = true;
        
        /* 
         * Update the internal inventory to reflect the new options once
         * the PlotMine is built
         */
        FZGuiPlotMine.changePlotMineInventory("chest", this);
    }
    
    public void deconstruct() {
        plugin.getLogger().info("attempting to deconstruct plotmine");
        this.showParticles = false;
        if (!this.hasPhysicalPresence) {
            this.villager.remove();
        }
        this.hasPhysicalPresence &= !this.hasPhysicalPresence;
        PlotMineController.removePlotMine(playerId);
    }
    
    /**
     * Increment the size of the constructed PlotMine by 1, then reconstruct.
     */
    public boolean upgradePlotMine() {
        if (size < GlobalVariables.MAX_PLOTMINE_SIZE) {
            size++;
            this.constructPhysicalPlotMine();
            return true;
        } else {
            errorMessage = "PlotMine is already max tier!";
            return false;
        }
    }
    
    public boolean addValuableMaterial(Material mat) {
        return this.valuableMats.add(mat);
    }
    
    public boolean removeValuableMaterial(Material mat) {
        return this.valuableMats.remove(mat);
    }
    
    public boolean containsValuableMaterial(Material mat) {
        return this.valuableMats.contains(mat);
    }
    
    public Material[] getValuableMaterials() {
        Material[] mats = new Material[this.valuableMats.size()];
        this.valuableMats.toArray(mats);
        return mats;
    }
          
    /*public boolean incrementSize() {
        if (size+1 > GlobalVariables.MAX_PLOTMINE_SIZE) {
            errorMessage = "Specified size is larger than the maximum valid plotmine size.";
            return false;
        }
        size++;
        if (showParticles) {
            destroyDisplayParticles();
            createDisplayParticles();
        }
        return true;
    }
    
    public boolean decrementSize() {
        if (size-1 < GlobalVariables.MIN_PLOTMINE_SIZE) {
            errorMessage = "Specified size is smaller than the minimum valid plotmine size.";
            return false;
        }
        size--;
        if (showParticles) {
            destroyDisplayParticles();
            createDisplayParticles();
        }
        return true;
    }*/
    
    public void createDisplayParticles() {
        this.showParticles = true;
        
        /* Populate local array of particles */
        particles = new ArrayList<Location>();

        for (int xoffset = 0; xoffset < size+2; xoffset++) {
            int xoffset_2 = xoffset * mirrors[0];
            particles.add(new Location(loc.getWorld(), loc.getBlockX()+xoffset_2, loc.getBlockY(), loc.getBlockZ()));
            particles.add(new Location(loc.getWorld(), loc.getBlockX()+xoffset_2, loc.getBlockY(), loc.getBlockZ()+(mirrors[1] *(size+1))));
        }
        for (int zoffset = 0; zoffset < size+2; zoffset++) {
            int zoffset_2 = zoffset * mirrors[1];
            particles.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()+zoffset_2));
            particles.add(new Location(loc.getWorld(), loc.getBlockX()+(mirrors[0]*(size+1)), loc.getBlockY(), loc.getBlockZ()+zoffset_2));
        }
        
        /* 
         * Create a repeating runnable that continuously spawns in particles as long
         * as they should be visible
         */
        new BukkitRunnable() {
            @Override
            public void run() {
                if (showParticles) {
                    DustOptions dopt = new DustOptions(Color.fromRGB(255, 0, 0), 1.0f);
                    for (Location loc : particles) {
                        loc.add(.5, .25, .5);
                        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 10, .1, 0, .1, dopt);
                        loc.subtract(.5, .25, .5);
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
    
    public void fillPlotMine() {
        Location curLoc;
        Material fillType;
        Material[] valuableTypes = getValuableMaterials();
        
        double r;
        double valPer = 0.05 + (this.size - 3) * 0.015;
        double valInterval = (this.valuableMats.size() == 0) ? 0 : valPer / this.valuableMats.size();
        
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                for (int z = 1; z <= size; z++) {
                    curLoc = new Location(loc.getWorld(), getX()+(x*mirrors[0]), getY()-y, getZ()+(z*mirrors[1]));
                    
                    r = Math.random();
                    if (r > valPer) {
                        fillType = this.fillMat;
                    } else {
                        if (valInterval == 0) {
                            fillType = this.fillMat;
                        } else {
                            fillType = valuableTypes[(int) (r/valInterval)];
                        }
                    }
                    curLoc.getBlock().setType(fillType);
                }
            }
        }    
    }
    
    public void destroyDisplayParticles() {
        this.showParticles = false;
    }
    
    public boolean createPlotMine() {
        villager = (Villager) loc.getWorld().spawnEntity(this.loc, EntityType.VILLAGER);
        villager.setAI(false);
        villager.setCustomName(ChatColor.AQUA + "PlotMine Configurator");
        villager.setCustomNameVisible(true);
        villager.setInvulnerable(true);
        villager.setSilent(true);
        villager.setRemoveWhenFarAway(false);
        villager.setVillagerType(Type.DESERT);
        villager.setProfession(Profession.NITWIT);
        villager.setMetadata("associatedFZPlotmine", new FixedMetadataValue(this.plugin, this));
        villagerUUID = villager.getUniqueId();
        
        return true;
    }

}
