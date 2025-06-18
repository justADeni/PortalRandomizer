package com.github.justadeni.portalRandomizer.listeners;

import com.github.justadeni.portalRandomizer.PortalRandomizer;
import com.github.justadeni.portalRandomizer.location.Destination;
import com.github.justadeni.portalRandomizer.location.Result;
import com.github.justadeni.portalRandomizer.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PortalUseListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPortalUse(PlayerPortalEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)
            return;

        Player player = event.getPlayer();

        event.setCancelled(true);
        Thread.ofVirtual().start(() -> {
            Location updatedLocation = LocationUtil.copy(event.getFrom());

            while (LocationUtil.alter(updatedLocation, -1, 0, 0).getBlock().getType() == Material.NETHER_PORTAL)
                updatedLocation.add(-1,0,0);
            while (LocationUtil.alter(updatedLocation, 0, -1, 0).getBlock().getType() == Material.NETHER_PORTAL)
                updatedLocation.add(0,-1,0);
            while (LocationUtil.alter(updatedLocation, 0, 0, -1).getBlock().getType() == Material.NETHER_PORTAL)
                updatedLocation.add(0,0,-1);

            Destination destination = event.getTo().getWorld().getEnvironment() == World.Environment.NETHER ? Destination.NETHER : Destination.OVERWORLD;
            Result<Location> attempt = Destination.get(updatedLocation, destination);
            if (attempt instanceof Result.Success<Location> success) {
                PlayerDamageListener.add(player);
                player.teleportAsync(success.value()).thenRunAsync(() -> PlayerDamageListener.remove(player));
            } else {
                Bukkit.getScheduler().runTask(PortalRandomizer.getInstance(), () -> {
                    event.getFrom().getBlock().breakNaturally();
                });
            }
        });
    }

}
