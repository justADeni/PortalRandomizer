package com.github.justadeni.portalRandomizer.location;

import com.github.justadeni.portalRandomizer.util.Coordinate;
import com.github.justadeni.portalRandomizer.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.*;

public class EmptyCubeFinder implements LocationFinder {

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

    private final List<Coordinate> space;

    public EmptyCubeFinder(int side) {
        int halfside = side/2;
        space = new ArrayList<>();
        for (int x = -halfside; x < halfside; x++){
            for (int y = -halfside; y < halfside; y++) {
                for (int z = -halfside; z < halfside; z++) {
                    space.add(new Coordinate(x, y, z));
                }
            }
        }
    }

    public Result find(Location center) {
        outer: for (int i = -32; i < 32; i++) {
            inner: for (Coordinate coordinate : space) {
                Block applied = coordinate.applyTo(center, i).getBlock();
                if (!applied.isEmpty() && !IGNORED_MATERIALS.contains(applied.getType()))
                    continue outer;
            }
            return new Result.Success(LocationUtil.alter(center, 0, i-2, 0));
        }
        return new Result.Failure();
    }

}

