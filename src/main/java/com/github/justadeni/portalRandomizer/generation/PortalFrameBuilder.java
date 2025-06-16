package com.github.justadeni.portalRandomizer.generation;

import com.github.justadeni.portalRandomizer.listeners.BlockUpdateListener;
import org.bukkit.*;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Orientable;

public class PortalFrameBuilder {

    public static Location create(Location location) {
        World world = location.getWorld();
        boolean flag = (location.getBlockX() & 1) != 0;

        // Make sure chunks are loaded
        /*
        Chunk initial = location.getChunk();
        initial.load(true);
        Chunk possibleOverlap = location.clone().add(flag ? 4 : 0, 0, flag ? 0 : 4).getChunk();
        if (possibleOverlap != initial)
            possibleOverlap.load(true);
        */

        Location firstPortalBlock = null;

        for (int x = 0; x <= 3; x++) {
            for (int y = 0; y <= 4; y++) {
                Location blockLocation;
                if (x == 0 || x == 3 || y == 0 || y == 4) {
                    // Place OBSIDIAN blocks at the corners of each layer
                    blockLocation = location.clone().add(flag ? x : 0, y, flag ? 0 : x);
                    world.getBlockAt(blockLocation).setType(Material.OBSIDIAN);
                } else {
                    // Place NETHER_PORTAL blocks inside the frame
                    blockLocation = location.clone().add(flag ? x : 0, y, flag ? 0 : x);
                    if (firstPortalBlock == null)
                        firstPortalBlock = blockLocation;
                    BlockUpdateListener.add(blockLocation);
                    world.getBlockAt(blockLocation).setType(Material.NETHER_PORTAL);
                    Block block = world.getBlockAt(blockLocation);
                    if (block.getBlockData() instanceof Orientable) {
                        ((Orientable) block.getBlockData()).setAxis(flag ? Axis.Z : Axis.X);
                        block.setBlockData(block.getBlockData());
                    }
                }
            }
        }
        // So that we know where to teleport the Player
        return firstPortalBlock;
    }

}


