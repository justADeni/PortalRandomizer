package com.github.justadeni.portalRandomizer.location;

import org.bukkit.Location;

public sealed interface SearchAttempt permits SearchAttempt.Failure, SearchAttempt.Success {

    final class Failure implements SearchAttempt {}

    record Success(Location location) implements SearchAttempt {}

}
