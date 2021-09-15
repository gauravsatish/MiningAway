package com.chqppy.miningaway.eventhandlers;

import com.chqppy.miningaway.MiningAway;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager implements Listener {

    private final MiningAway main;

    public ItemManager(MiningAway main) {
        this.main = main;
    }

    @EventHandler
    public void onItemTakeDurability(PlayerItemDamageEvent event) {

        if (event.getItem() != null) {
            ItemMeta itemMeta = event.getItem().getItemMeta();

            for (String itemDisplayName : main.nonDamageableItems) {
                if (ChatColor.translateAlternateColorCodes('&', itemMeta.getDisplayName()).equals(itemDisplayName)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
