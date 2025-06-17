package com.github.justadeni.portalRandomizer.generation;

import com.github.justadeni.portalRandomizer.PortalRandomizer;
import com.github.justadeni.portalRandomizer.util.Coordinate;
import org.bukkit.*;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Orientable;

import java.util.Set;

public class PortalFrameBuilder {

    private static final Set<Coordinate> OBSIDIAN = Set.of(
            // Bottom row
            new Coordinate(-1,-1,0),
            new Coordinate(0, -1, 0),
            new Coordinate(1, -1, 0),
            new Coordinate(2, -1, 0),

            // Top row
            new Coordinate(-1,3,0),
            new Coordinate(0, 3, 0),
            new Coordinate(1, 3, 0),
            new Coordinate(2, 3, 0),

            // Left column
            new Coordinate(-1,0,0),
            new Coordinate(-1, 1, 0),
            new Coordinate(-1, 2, 0),

            // Right column
            new Coordinate(2,0,0),
            new Coordinate(2, 1, 0),
            new Coordinate(2, 2, 0),

            // Shelf
            new Coordinate(0, -1, 1),
            new Coordinate(1, -1, 1),
            new Coordinate(0, -1, -1),
            new Coordinate(1, -1, -1)
    );

    private static final Set<Coordinate> NETHER_PORTAL = Set.of(
            new Coordinate(0,0,0),
            new Coordinate(0,1,0),
            new Coordinate(0,2,0),
            new Coordinate(1,0,0),
            new Coordinate(1,1,0),
            new Coordinate(1,2,0)
    );

    public static void create(Location location) {
        World world = location.getWorld();
        boolean flag = (location.getBlockX() & 1) != 0;

        world.getChunkAtAsync(location).thenRun(() -> {
            Bukkit.getScheduler().runTask(PortalRandomizer.getInstance(), () -> {
                for (Coordinate coordinate : OBSIDIAN) {
                    Location frame = flag ? coordinate.applyTo(location) : coordinate.applyAndTranspose(location);
                    frame.getBlock().setType(Material.OBSIDIAN);
                }
                for (Coordinate coordinate : NETHER_PORTAL) {
                    Location inside = flag ? coordinate.applyTo(location) : coordinate.applyAndTranspose(location);
                    Block block = inside.getBlock();
                    block.setType(Material.NETHER_PORTAL);
                    Orientable blockData = ((Orientable) block.getBlockData());
                    blockData.setAxis(flag ? Axis.X : Axis.Z);
                    block.setBlockData(blockData, false);
                }
            });
        });
    }


}


