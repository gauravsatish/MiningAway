package com.chqppy.miningaway.eventhandlers;

import com.chqppy.miningaway.MiningAway;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class KitsManager implements Listener {

    private MiningAway main;

    public KitsManager(MiningAway main) {
        this.main = main;
    }

    @EventHandler
    public void onKitClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle()).equals(ChatColor.BLACK + "Select a kit")) {

            Inventory pInv = player.getInventory();

            if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.IRON_PICKAXE)) {
                removeKitItems(player);
                pInv.addItem(main.nubSword);
                pInv.addItem(main.nubPick);
                pInv.addItem(main.nubAxe);
                pInv.addItem(main.nubShovel);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                player.closeInventory();
            } else if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.GOLDEN_PICKAXE)) {
                if (main.blocksBrokenPlayer.get(player) >= 500) {
                    removeKitItems(player);
                    pInv.addItem(main.epicoSword);
                    pInv.addItem(main.epicoPick);
                    pInv.addItem(main.epicoAxe);
                    pInv.addItem(main.epicoShovel);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                    player.closeInventory();
                } else {
                    int blocksRemaining = 500 - main.blocksBrokenPlayer.get(player);
                    player.closeInventory();
                    player.sendMessage(ChatColor.RED + "Kit Unavailable! Mine " + ChatColor.GOLD + blocksRemaining + ChatColor.RED + " more blocks to claim this kit!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            } else if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
                if (main.blocksBrokenPlayer.get(player) >= 3500) {
                    removeKitItems(player);
                    pInv.addItem(main.wopiSword);
                    pInv.addItem(main.wopiPick);
                    pInv.addItem(main.wopiAxe);
                    pInv.addItem(main.wopiShovel);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                    player.closeInventory();
                } else {
                    int blocksRemaining = 3500 - main.blocksBrokenPlayer.get(player);
                    player.closeInventory();
                    player.sendMessage(ChatColor.RED + "Kit Unavailable! Mine " + ChatColor.GOLD + blocksRemaining + ChatColor.RED + " more blocks to claim this kit!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            } else if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.NETHERITE_PICKAXE)) {
                if (main.blocksBrokenPlayer.get(player) >= 10000) {
                    removeKitItems(player);
                    pInv.addItem(main.wahSword);
                    pInv.addItem(main.wahPick);
                    pInv.addItem(main.wahAxe);
                    pInv.addItem(main.wahShovel);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                    player.closeInventory();
                } else {
                    int blocksRemaining = 10000 - main.blocksBrokenPlayer.get(player);
                    player.closeInventory();
                    player.sendMessage(ChatColor.RED + "Kit Unavailable! Mine " + ChatColor.GOLD + blocksRemaining + ChatColor.RED + " more blocks to claim this kit!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }

            event.setCancelled(true);
        }
    }

    public void removeKitItems(Player player) {
        Inventory pInv = player.getInventory();
        if (pInv.contains(main.nubSword)) {
            pInv.remove(main.nubSword);
        }
        if (pInv.contains(main.nubPick)) {
            pInv.remove(main.nubPick);
        }
        if (pInv.contains(main.nubAxe)) {
            pInv.remove(main.nubAxe);
        }
        if (pInv.contains(main.nubShovel)) {
            pInv.remove(main.nubShovel);
        }
        if (pInv.contains(main.epicoSword)) {
            pInv.remove(main.epicoSword);
        }
        if (pInv.contains(main.epicoPick)) {
            pInv.remove(main.epicoPick);
        }
        if (pInv.contains(main.epicoAxe)) {
            pInv.remove(main.epicoAxe);
        }
        if (pInv.contains(main.epicoShovel)) {
            pInv.remove(main.epicoShovel);
        }
        if (pInv.contains(main.wopiSword)) {
            pInv.remove(main.wopiSword);
        }
        if (pInv.contains(main.wopiPick)) {
            pInv.remove(main.wopiPick);
        }
        if (pInv.contains(main.wopiAxe)) {
            pInv.remove(main.wopiAxe);
        }
        if (pInv.contains(main.wopiShovel)) {
            pInv.remove(main.wopiShovel);
        }
        if (pInv.contains(main.wahSword)) {
            pInv.remove(main.wahSword);
        }
        if (pInv.contains(main.wahPick)) {
            pInv.remove(main.wahPick);
        }
        if (pInv.contains(main.wahAxe)) {
            pInv.remove(main.wahAxe);
        }
        if (pInv.contains(main.wahShovel)) {
            pInv.remove(main.wahShovel);
        }
    }

}
