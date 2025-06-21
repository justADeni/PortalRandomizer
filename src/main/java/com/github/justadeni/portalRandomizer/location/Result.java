package com.github.justadeni.portalRandomizer.location;

import org.bukkit.Location;

public sealed interface Result permits Result.Success, Result.Failure {
    record Success(Location location) implements Result {}
    final class Failure implements Result {}
}

interface LocationFinder {
    Result find(Location location);
}