package com.github.justadeni.portalRandomizer.listeners;

import com.github.justadeni.portalRandomizer.PortalRandomizer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class BlockUpdateListener implements Listener {

    private static final Set<Location> exemptLocations = new HashSet<>();

    public static void add(Location exempt) {
        exemptLocations.add(exempt);
        new BukkitRunnable() {
            @Override
            public void run() {
                exemptLocations.remove(exempt);
            }
        }.runTaskLater(PortalRandomizer.getInstance(), 100);
    }

    /*
    private static Set<Location> around(Location location) {
        return Set.of(
                new Location(location.getWorld(), location.getBlockX() + 1, location.getBlockY(), location.getBlockZ()),
                new Location(location.getWorld(), location.getBlockX() - 1, location.getBlockY(), location.getBlockZ()),
                new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 1, location.getBlockZ()),
                new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ()),
                new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ() + 1),
                new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ() - 1)
        );
    }
    */

    @EventHandler(ignoreCancelled = true)
    public void onBlockUpdate(BlockPhysicsEvent event) {
        if (exemptLocations.isEmpty())
            return;

        if (exemptLocations.contains(event.getBlock().getLocation()))
            event.setCancelled(true);
    }

}
