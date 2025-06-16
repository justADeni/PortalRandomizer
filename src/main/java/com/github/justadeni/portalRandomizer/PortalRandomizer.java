package com.github.justadeni.portalRandomizer;

import com.github.justadeni.portalRandomizer.listeners.BlockUpdateListener;
import com.github.justadeni.portalRandomizer.listeners.PortalCreateListener;
import com.github.justadeni.portalRandomizer.listeners.PortalUseListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PortalRandomizer extends JavaPlugin {

    private static JavaPlugin plugin;

    public static JavaPlugin getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new BlockUpdateListener(), this);
        getServer().getPluginManager().registerEvents(new PortalCreateListener(), this);
        getServer().getPluginManager().registerEvents(new PortalUseListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
