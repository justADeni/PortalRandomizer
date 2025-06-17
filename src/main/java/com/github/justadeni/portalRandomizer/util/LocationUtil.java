package com.github.justadeni.portalRandomizer.util;

import org.bukkit.Location;

public class LocationUtil {

    public static Location copy(Location location) {
        return new Location(location.getWorld(), location.x(), location.y(), location.z());
    }

    public static Location alter(Location location, int x, int y, int z) {
        return new Location(location.getWorld(), location.x() + x, location.y() + y, location.z() + z);
    }

}
