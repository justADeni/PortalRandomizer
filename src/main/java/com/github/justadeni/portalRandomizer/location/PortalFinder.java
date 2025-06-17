package com.github.justadeni.portalRandomizer.location;

import com.github.justadeni.portalRandomizer.util.LocationUtil;
import org.bukkit.*;

public class PortalFinder {

    public static Result<Location> find(Location loc) {
        Location copy = LocationUtil.alter(loc, 0, -32, 0);
        for (int i = 0; i < 64; i++) {
            copy.add(0,1,0);
            if (copy.getBlock().getType() == Material.NETHER_PORTAL)
                return new Result.Success<>(copy);
        }

        return new Result.Failure<>();
    }

}

