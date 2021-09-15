package com.chqppy.miningaway.eventhandlers;

import com.chqppy.miningaway.MiningAway;
import com.chqppy.miningaway.enums.Ranks;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerManager implements Listener {
    private MiningAway main;

    public PlayerManager(MiningAway main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (playerData.has(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING)) {

            if (playerData.get(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING).equals("NUB")) {
                main.playerRank.put(player, Ranks.NUB);
            } else if (playerData.get(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING).equals("EPICOGAMER")) {
                main.playerRank.put(player, Ranks.EPICOGAMER);
            } else if (playerData.get(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING).equals("WOPIPIRO")) {
                main.playerRank.put(player, Ranks.WOPIPIRO);
            } else if (playerData.get(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING).equals("WAH")) {
                main.playerRank.put(player, Ranks.WAH);
            } else {
                System.out.println("Something went wrong in the setting player rank in the onJoin Event");
            }
        } else {
            playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "NUB");
        }

        if (playerData.has(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "numBlocksBroken"), PersistentDataType.INTEGER)) {
            main.blocksBrokenPlayer.put(player, playerData.get(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "numBlocksBroken"), PersistentDataType.INTEGER));
        } else {
            playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "numBlocksBroken"), PersistentDataType.INTEGER, 0);
            main.blocksBrokenPlayer.put(player, 0);
        }

        main.buildSideBar(player);

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer playerData = player.getPersistentDataContainer();

        playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "numBlocksBroken"), PersistentDataType.INTEGER, main.blocksBrokenPlayer.get(player));
    }
}
