package com.github.justadeni.portalRandomizer.location;

import com.github.justadeni.portalRandomizer.generation.PortalFrameBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.function.BiFunction;

public enum Destination {

    OVERWORLD(ReversiblePRNG::backward, Bukkit.getWorld("world")),
    NETHER(ReversiblePRNG::forward, Bukkit.getWorld("world_nether"));
    // this the capital of Amsterdam?

    private final BiFunction<Integer, Integer, int[]> prng;
    private final World world;

    Destination(BiFunction<Integer, Integer, int[]> ref, World world) {
        this.prng = ref;
        this.world = world;
    }

    public static SearchAttempt get(Location from, Destination destination) {
        // 256*256 quadrants link
        int[] mostSignificant = destination.prng.apply(from.blockX() >>> 8, from.blockZ() >>> 8);

        // position within the quadrant
        int[] leastSignificant = destination.prng.apply(from.blockX() << 24, from.blockZ() << 24);

        int combinedX = mostSignificant[0] << 8 | leastSignificant[0] >>> 24;
        int combinedZ = mostSignificant[1] << 8 | leastSignificant[1] >>> 24;

        Location searchCenter = new Location(destination.world, combinedX, destination.world.getSeaLevel(), combinedZ);
        SearchAttempt portalSearchAttempt = PortalFinder.find(searchCenter, 16, 128);

        // Found existing Nether portal nearby to destination, using it
        if (portalSearchAttempt instanceof SearchAttempt.Success success)
            return new SearchAttempt.Success(success.location());

        // No existing Nether portal found, need to find a suitable place and make it
        SearchAttempt spaceSearchAttempt = EmptyCubeFinder.find(searchCenter, 5, 64);
        if (spaceSearchAttempt instanceof SearchAttempt.Success success)
            return new SearchAttempt.Success(PortalFrameBuilder.create(success.location()));

        return new SearchAttempt.Failure();
    }

    private static class ReversiblePRNG {
        private static final int ROUNDS = 3;
        private static final int[] KEYS = {0xA5A5A5A5, 0x5A5A5A5A, 0xDEADBEEF};

        // Feistel function (simple, fast, reversible)
        public static int[] forward(int left, int right) {
            for (int i = 0; i < ROUNDS; i++) {
                int temp = left;
                int half = left;
                half ^= KEYS[i];
                half += (KEYS[i] << 1) | (KEYS[i] >>> 1);
                left = right ^ half;
                right = temp;
            }
            return new int[]{left, right};
        }

        public static int[] backward(int left, int right) {
            for (int i = ROUNDS - 1; i >= 0; i--) {
                int temp = right;
                int half = right;
                half ^= KEYS[i];
                half += (KEYS[i] << 1) | (KEYS[i] >>> 1);
                right = left ^ half;
                left = temp;
            }
            return new int[]{left, right};
        }

    }

}
