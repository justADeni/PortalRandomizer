package com.github.justadeni.portalRandomizer.listeners;

import com.github.justadeni.portalRandomizer.util.Virtual;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDamageListener implements Listener {

    private static final Set<UUID> set = ConcurrentHashMap.newKeySet();

    public static void add(Player player) {
        set.add(player.getUniqueId());
        Thread.ofVirtual().start(() -> {
            Virtual.sleep(10_000);
            set.remove(player.getUniqueId());
        });
    }

    public static void remove(Player player) {
        set.remove(player.getUniqueId());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER)
            return;

        if (set.contains(event.getEntity().getUniqueId()))
            event.setCancelled(true);
    }

}
