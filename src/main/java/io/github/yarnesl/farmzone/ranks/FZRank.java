package io.github.yarnesl.farmzone.ranks;

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
}
