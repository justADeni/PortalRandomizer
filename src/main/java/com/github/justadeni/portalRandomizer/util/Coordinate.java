package com.github.justadeni.portalRandomizer.util;

import org.bukkit.Location;

public class Coordinate {

    private final int packed;

    public Coordinate(int x, int y, int z) {
        packed = ((x & 0xFF) << 16) | ((y & 0xFF) << 8) | (z & 0xFF);
    }

    public int x() {
        return (byte) (packed >> 16);
    }

    public int y() {
        return (byte) (packed >> 8);
    }

    public int z() {
        return (byte) packed;
    }

    public Location applyTo(Location location) {
        return LocationUtil.alter(location, x(), y(), z());
    }

    public Location applyTo(Location location, int deltaY) {
        return LocationUtil.alter(location, x(), y() + deltaY, z());
    }

    public Location applyAndTranspose(Location location) {
        return LocationUtil.alter(location, z(), y(), x());
    }

}
