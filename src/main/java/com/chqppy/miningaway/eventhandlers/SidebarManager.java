package com.chqppy.miningaway.eventhandlers;

import com.chqppy.miningaway.MiningAway;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SidebarManager implements Listener {

    private MiningAway main;

    public SidebarManager(MiningAway main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        int amount = main.blocksBrokenPlayer.get(player);

        main.blocksBrokenPlayer.remove(player);
        main.blocksBrokenPlayer.put(player, amount + 1);

        player.getScoreboard().getTeam("blocks_broken").setSuffix(ChatColor.GOLD + "" + main.blocksBrokenPlayer.get(player) + "");

        main.updatePlayerRank(player);

    }

}
