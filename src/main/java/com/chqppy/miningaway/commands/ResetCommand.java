package com.chqppy.miningaway.commands;

import com.chqppy.miningaway.MiningAway;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {

    private final MiningAway main;

    public ResetCommand(MiningAway main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            main.blocksBrokenPlayer.remove(player);
            main.blocksBrokenPlayer.put(player, 0);
            main.updatePlayerRank(player);
            System.out.println("updated player rank");

            player.getScoreboard().getTeam("blocks_broken").setSuffix(ChatColor.GOLD + "" + main.blocksBrokenPlayer.get(player) + "");

            player.getInventory().clear();
        }

        return false;
    }
}
