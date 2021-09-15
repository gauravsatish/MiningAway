package com.chqppy.miningaway.commands;

import com.chqppy.miningaway.MiningAway;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitsCommand implements CommandExecutor {

    private MiningAway main;

    public KitsCommand(MiningAway main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            main.applyKitsUI(player);
        }
        return false;

    }
}
