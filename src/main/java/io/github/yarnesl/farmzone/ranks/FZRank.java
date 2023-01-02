package io.github.yarnesl.farmzone.ranks;

import org.bukkit.ChatColor;

import io.github.yarnesl.farmzone.FZPlayer;

public enum FZRank {
    FARMER,
    RANCHER,
    BOTANIST,
    OWNER;
    
    public static FZRank getRank(String rank) {
        if (rank.equalsIgnoreCase("farmer")) {
            return FARMER;
        } else if (rank.equalsIgnoreCase("rancher")) {
            return RANCHER;
        }
        else if (rank.equalsIgnoreCase("botanist")) {
            return BOTANIST;
        }
        else if (rank.equalsIgnoreCase("owner")) {
            return OWNER;
        } else {
            return null;
        }
    }
    
    public String toString() {
        if (this == FARMER) {
            return "farmer";
        } else if (this == RANCHER) {
            return "rancher";
        } else if (this == BOTANIST) {
            return "botanist";
        } else if (this == OWNER) {
            return "owner";
        } else {
            return "null";
        }
    }
    
    public static String generateChatTag(FZPlayer fzp) {
        FZRank rank = fzp.getRank();
        String tag;
        
        switch(rank) {
            case FARMER:
                tag = ChatColor.GRAY + "[FARMER] " + ChatColor.WHITE + "<" + fzp.getName() + "> ";
                break;
            case RANCHER:
                tag = ChatColor.GOLD + "[RANCHER] " + ChatColor.WHITE + "<" + fzp.getName() + "> ";
                break;
            case BOTANIST:
                tag = ChatColor.GREEN + "[BOTANIST] " + ChatColor.WHITE + "<" + fzp.getName() + "> ";
                break;
            case OWNER:
                tag = ChatColor.RED + "[OWNER] " + ChatColor.WHITE + "<" + fzp.getName() + "> ";
                break;
            default:
                tag = "";
                break;
        }
        return tag;
        
    }
}
