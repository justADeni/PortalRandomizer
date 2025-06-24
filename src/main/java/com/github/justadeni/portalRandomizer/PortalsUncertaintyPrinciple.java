package com.github.justadeni.portalRandomizer;

import com.github.justadeni.portalRandomizer.listeners.PlayerDamageListener;
import com.github.justadeni.portalRandomizer.listeners.PortalCreateListener;
import com.github.justadeni.portalRandomizer.listeners.PortalUseListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class PortalsUncertaintyPrinciple extends JavaPlugin {

    private static JavaPlugin plugin;

    public static JavaPlugin getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        generateKeys();
        getServer().getPluginManager().registerEvents(new PortalCreateListener(), this);
        getServer().getPluginManager().registerEvents(new PortalUseListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
    }

    private void generateKeys() {
        if (getConfig().getIntegerList("keys").isEmpty()) {
            Random random = new Random();
            List<Integer> keys = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                keys.add(random.nextInt(256)); // 8-bit keys
            }
            getConfig().set("keys", keys);
            saveConfig();
        }
    }

}
