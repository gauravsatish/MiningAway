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
                pInv.addItem(main.basicSword);
                pInv.addItem(main.basicPick);
                pInv.addItem(main.basicAxe);
                pInv.addItem(main.basicShovel);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                player.closeInventory();
            } else if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.GOLDEN_PICKAXE)) {
                if (main.blocksBrokenPlayer.get(player) >= 500) {
                    removeKitItems(player);
                    pInv.addItem(main.senatorSword);
                    pInv.addItem(main.senatorPick);
                    pInv.addItem(main.senatorAxe);
                    pInv.addItem(main.senatorShovel);
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
                    pInv.addItem(main.padawanSword);
                    pInv.addItem(main.padawanPick);
                    pInv.addItem(main.padawanAxe);
                    pInv.addItem(main.padawanShovel);
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
                    pInv.addItem(main.jediSword);
                    pInv.addItem(main.jediPick);
                    pInv.addItem(main.jediAxe);
                    pInv.addItem(main.jediShovel);
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
        if (pInv.contains(main.basicSword)) {
            pInv.remove(main.basicSword);
        }
        if (pInv.contains(main.basicPick)) {
            pInv.remove(main.basicPick);
        }
        if (pInv.contains(main.basicAxe)) {
            pInv.remove(main.basicAxe);
        }
        if (pInv.contains(main.basicShovel)) {
            pInv.remove(main.basicShovel);
        }
        if (pInv.contains(main.senatorSword)) {
            pInv.remove(main.senatorSword);
        }
        if (pInv.contains(main.senatorPick)) {
            pInv.remove(main.senatorPick);
        }
        if (pInv.contains(main.senatorAxe)) {
            pInv.remove(main.senatorAxe);
        }
        if (pInv.contains(main.senatorShovel)) {
            pInv.remove(main.senatorShovel);
        }
        if (pInv.contains(main.padawanSword)) {
            pInv.remove(main.padawanSword);
        }
        if (pInv.contains(main.padawanPick)) {
            pInv.remove(main.padawanPick);
        }
        if (pInv.contains(main.padawanAxe)) {
            pInv.remove(main.padawanAxe);
        }
        if (pInv.contains(main.padawanShovel)) {
            pInv.remove(main.padawanShovel);
        }
        if (pInv.contains(main.jediSword)) {
            pInv.remove(main.jediSword);
        }
        if (pInv.contains(main.jediPick)) {
            pInv.remove(main.jediPick);
        }
        if (pInv.contains(main.jediAxe)) {
            pInv.remove(main.jediAxe);
        }
        if (pInv.contains(main.jediShovel)) {
            pInv.remove(main.jediShovel);
        }
    }

}
