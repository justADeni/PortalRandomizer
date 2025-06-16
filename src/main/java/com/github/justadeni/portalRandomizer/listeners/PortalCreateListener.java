package com.github.justadeni.portalRandomizer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreateListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPortalCreate(PortalCreateEvent event) {
        if (event.getReason() != PortalCreateEvent.CreateReason.NETHER_PAIR)
            return;

        event.setCancelled(true);
    }

}
