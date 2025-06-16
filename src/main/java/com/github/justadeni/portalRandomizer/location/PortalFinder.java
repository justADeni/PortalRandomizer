package com.github.justadeni.portalRandomizer.location;

import org.bukkit.*;
import org.bukkit.block.Block;

public class PortalFinder {

    public static SearchAttempt find(Location loc, int xzRadius, int yRadius) {
        World world = loc.getWorld();
        Block centerBlock = loc.getBlock();

        // Iterate through all blocks within the specified radius
        for (int x = -xzRadius; x <= xzRadius; x++) {
            for (int y = -yRadius; y <= yRadius; y++) {
                for (int z = -xzRadius; z <= xzRadius; z++) {
                    Block block = centerBlock.getRelative(x, y, z);

                    // Check if the current block has the specified material
                    if (block.getType() == Material.NETHER_PORTAL) {
                        Block aboveBlock = block.getLocation().add(0, 1, 0).getBlock();

                        // Check if the block directly above has the same material
                        if (aboveBlock.getType() == Material.NETHER_PORTAL) {
                            return new SearchAttempt.Success(block.getLocation()); // Return location of the found block
                        }
                    }
                }
            }
        }

        return new SearchAttempt.Failure();
    }


}

