package com.github.justadeni.portalRandomizer.location;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EmptyCubeFinder {

    private static final Set<Material> IGNORED_MATERIALS = new HashSet<>(Arrays.asList(
            Material.SHORT_GRASS,
            Material.TALL_GRASS,
            Material.SHORT_DRY_GRASS,
            Material.TALL_DRY_GRASS,
            Material.POWDER_SNOW,
            Material.POPPY,
            Material.DANDELION,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.BLUE_ORCHID,
            Material.ORANGE_TULIP,
            Material.PEONY,
            Material.RED_TULIP,
            Material.WHITE_TULIP,
            Material.LILAC
            // Add other materials to ignore here if needed
    ));

    public static SearchAttempt find(Location center, int size, int radius) {
        World world = center.getWorld();
        if (world == null || size <= 0 || radius < size / 2) return null;

        int offset = size / 2;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    Location cubeCenter = center.clone().add(dx, dy, dz);
                    Block baseBlock = cubeCenter.getBlock();
                    boolean isClear = true;

                    // Check if the entire cube is AIR or empty
                    for (int x = -offset; x < size - offset && isClear; x++) {
                        for (int y = -offset; y < size - offset && isClear; y++) {
                            for (int z = -offset; z < size - offset; z++) {
                                Block b = baseBlock.getRelative(x, y, z);
                                if (!(b.getType() == Material.AIR || b.isEmpty())) {
                                    isClear = false;
                                    break;
                                }
                            }
                        }
                    }

                    // Check below the cube if clear
                    if (isClear) {
                        Block below = baseBlock.getRelative(0, -offset - 1, 0);
                        if (below.isEmpty() || IGNORED_MATERIALS.contains(below.getType())) {
                            isClear = false;
                        }
                    }

                    if (isClear) {
                        return new SearchAttempt.Success(cubeCenter);
                    }
                }
            }
        }

        return new SearchAttempt.Failure();
    }

}

