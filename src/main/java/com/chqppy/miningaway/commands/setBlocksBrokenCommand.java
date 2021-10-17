package com.chqppy.miningaway.commands;

import com.chqppy.miningaway.MiningAway;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setBlocksBrokenCommand implements CommandExecutor {

    private MiningAway main;

    public setBlocksBrokenCommand(MiningAway main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.isOp()) {
                try {
                    if (args.length == 1) {

                        int blocksCounter = Integer.parseInt(args[0]);

                        main.blocksBrokenPlayer.remove(player);
                        main.blocksBrokenPlayer.put(player, blocksCounter);

                        player.getScoreboard().getTeam("blocks_broken").setSuffix(ChatColor.GOLD + "" + main.blocksBrokenPlayer.get(player) + "");

                    } else if (args.length == 2) {
                        int blocksCounter = Integer.parseInt(args[0]);

                        if (player.getServer().getPlayerExact(args[1]) != null) {
                            main.blocksBrokenPlayer.remove(player);
                            main.blocksBrokenPlayer.put(player, blocksCounter);

                            player.getScoreboard().getTeam("blocks_broken").setSuffix(ChatColor.GOLD + "" + main.blocksBrokenPlayer.get(player) + "");
                        }
//                } else {
//                    player.sendMessage(ChatColor.RED + "Invalid Usage! Correct format is /setblocksbroken <Number Of Blocks Broken> <OPTIONAL: Player>");
//                }
                    }
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Invalid Usage! " +
                            "\nCorrect format is: " +
                            "\n" + ChatColor.GRAY + "/" + ChatColor.AQUA + "setblocksbroken " +
                            ChatColor.GREEN + "<Number Of Blocks Broken> " +
                            ChatColor.GRAY + "<OPTIONAL: Player>");
                }
                main.updatePlayerRank(player);
            }
        }
        return false;
    }
}
