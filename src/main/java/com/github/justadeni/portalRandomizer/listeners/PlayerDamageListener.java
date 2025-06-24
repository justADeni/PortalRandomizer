package com.github.justadeni.portalRandomizer.listeners;

import com.github.justadeni.portalRandomizer.util.Virtual;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDamageListener implements Listener {

    private static final Set<UUID> set = ConcurrentHashMap.newKeySet();

    public static void add(Player player) {
        set.add(player.getUniqueId());
        Thread.ofVirtual().start(() -> {
            Virtual.sleep(15_000);
            set.remove(player.getUniqueId());
        });
    }

    public static void remove(Player player) {
        Thread.ofVirtual().start(() -> {
            Virtual.sleep(5_000);
            set.remove(player.getUniqueId());
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTakeDamage(EntityDamageByBlockEvent event) {
        if (event.getEntityType() != EntityType.PLAYER)
            return;

        if (set.contains(event.getEntity().getUniqueId()))
            event.setCancelled(true);
    }

}
