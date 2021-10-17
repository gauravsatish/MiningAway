package com.chqppy.miningaway.enums;

import org.bukkit.ChatColor;

public enum Ranks {

    PEASANT(ChatColor.GRAY + "Peasant"),
    SENATOR(ChatColor.GOLD + "Senator"),
    PADAWAN(ChatColor.BLUE + "Padawan"),
    JEDI(ChatColor.LIGHT_PURPLE + "Jedi");


    private final String rankString;

    Ranks(String rank) {
        this.rankString = rank;
    }

    public String getRankString() {
        return this.rankString;
    }

}
