package com.chqppy.miningaway.eventhandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerManager implements Listener {

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

}
