package io.github.yarnesl.farmzone;

import org.bukkit.Particle;

public class FZParticle {

    private int x;
    private int y;
    private int z;
    
    private Particle p;
    
    private double duration;
    
    public FZParticle(int x, int y, int z, Particle part) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.p = part;
    }
    
    public FZParticle(int x, int y, int z, Particle part, double duration) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.p = part;
        this.duration = duration;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public Particle getParticle() {
        return this.p;
    }
    
    public double getDuration() {
        return this.duration;
    }
    
}
