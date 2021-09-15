package com.chqppy.miningaway.enums;

import org.bukkit.ChatColor;

public enum Ranks {

    NUB(ChatColor.GRAY + "Nub"),
    EPICOGAMER(ChatColor.GOLD + "Epico Gamer"),
    WOPIPIRO(ChatColor.BLUE + "Wopi Piro Gamer"),
    WAH(ChatColor.LIGHT_PURPLE + "Wah");


    private final String rankString;

    Ranks(String rank) {
        this.rankString = rank;
    }

    public String getRankString() {
        return this.rankString;
    }

}
