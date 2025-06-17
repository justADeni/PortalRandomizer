package com.github.justadeni.portalRandomizer.location;

import com.github.justadeni.portalRandomizer.crypto.CombinedFeistel;
import com.github.justadeni.portalRandomizer.generation.PortalFrameBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.function.Function;

public enum Destination {

    OVERWORLD(CombinedFeistel::forward, Bukkit.getWorld("world")),
    NETHER(CombinedFeistel::backward, Bukkit.getWorld("world_nether"));
    // this the capital of Amsterdam?

    private final Function<Integer, Integer> prng;
    private final World world;

    private final static EmptyCubeFinder cubeFinder = new EmptyCubeFinder(5);

    Destination(Function<Integer, Integer> ref, World world) {
        this.prng = ref;
        this.world = world;
    }

    public static Result<Location> get(Location from, Destination destination) {
        Location searchCenter = new Location(destination.world, destination.prng.apply(from.blockX()), destination.world.getSeaLevel(), destination.prng.apply(from.blockZ()));
        Result<Location> portalSearchAttempt = PortalFinder.find(searchCenter);

        // Found existing Nether portal nearby to destination, using it
        if (portalSearchAttempt instanceof Result.Success<Location> success) {
            return portalSearchAttempt;
        }

        // No existing Nether portal found, need to find a suitable place and make it
        Result<Location> spaceSearchAttempt = cubeFinder.find(searchCenter);
        if (spaceSearchAttempt instanceof Result.Success<Location> success) {
            PortalFrameBuilder.create(success.value());
            return new Result.Success<>(success.value());
        }

        return new Result.Failure<>();
    }

}
