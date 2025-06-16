package com.github.justadeni.portalRandomizer.listeners;

import com.github.justadeni.portalRandomizer.PortalRandomizer;
import com.github.justadeni.portalRandomizer.location.Destination;
import com.github.justadeni.portalRandomizer.location.SearchAttempt;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PortalUseListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPortalUse(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)
            return;

        event.setCancelled(true);
        Destination destination = event.getTo().getWorld().getEnvironment() == World.Environment.NETHER ? Destination.NETHER : Destination.OVERWORLD;
        SearchAttempt attempt = Destination.get(event.getFrom(), destination);
        if (attempt instanceof SearchAttempt.Success success) {
            event.getPlayer().teleportAsync(success.location());
        } else {
            PortalRandomizer.getInstance().getLogger().info("Error generating a Nether portal.");
        }
    }

}
