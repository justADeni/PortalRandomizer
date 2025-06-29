package com.github.justadeni.portalRandomizer.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when Player attempts to use Nether portal.
 * <p>
 * This event is {@code asynchronous} and should be used with caution.
 * Do not interact with Bukkit API methods that are not thread-safe unless switching to the main thread.
 */
public class NetherPortalUseEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private boolean cancelled = false;
    private final Player player;
    private final Location originLocation;
    private final Location destinationLocation;

    public NetherPortalUseEvent(Player player, Location originLocation, Location destinationLocation) {
        super(true);
        this.player = player;
        this.originLocation = originLocation;
        this.destinationLocation = destinationLocation;
    }

    /**
     * Gets the origin location the player is coming from.
     *
     * @return the origin location
     */
    public Location getOriginLocation() {
        return originLocation;
    }

    /**
     * Gets the destination location the player is teleporting to.
     *
     * @return the destination location
     */
    public Location getDestinationLocation() {
        return destinationLocation;
    }

    /**
     * Gets the player involved in this event.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Checks if the event is cancelled.
     *
     * @return {@code true} if the event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of this event.
     *
     * @param cancel {@code true} to cancel the event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
