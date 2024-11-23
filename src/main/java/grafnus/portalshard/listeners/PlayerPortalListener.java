package grafnus.portalshard.listeners;

import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.events.EEvents;
import grafnus.portalshard.util.Pair;
import grafnus.portalshard.util.placement.RelativePosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;

public class PlayerPortalListener implements Listener {

    public static HashMap<Player, Instant> recentlyTeleported = new HashMap<>();
    public static HashMap<Player, Location> lastTeleportedTo = new HashMap<>();

    @EventHandler
    public void onEntityPortalTouch(EntityPortalEnterEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) event.getEntity();
        if (!recentlyTeleported.keySet().contains(p)) {
            PortalEngine.getInstance().listenToEvent(event, EEvents.PLAYER_PORTAL);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerPortalFix(PlayerPortalEvent event) {
        Bukkit.getLogger().log(Level.INFO, "Event Called");

        Pair<Location, Boolean> resultsLocationFix = fixNetherPortalExitLocation(event.getSearchRadius(), event.getTo());
        Location fixedLocation = resultsLocationFix.getFirst();
        boolean isPlayerPortalInRange = resultsLocationFix.getSecond();

        if (isPlayerPortalInRange && Objects.isNull(fixedLocation)) {
            event.setSearchRadius(1);
            return;
        }

        event.setTo(fixedLocation);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityPortalFix(EntityPortalEvent event) {
        Bukkit.getLogger().log(Level.INFO, "Event Called");

        if (!(event.getEntity() instanceof Player)) {
            if (RelativePosition.getLocationAboveN(event.getEntity().getLocation(), 2).getBlock().getType().equals(Material.RESPAWN_ANCHOR)) {
                event.setCancelled(true);
            }
        }

        Pair<Location, Boolean> resultsLocationFix = fixNetherPortalExitLocation(event.getSearchRadius(), event.getTo());
        Location fixedLocation = resultsLocationFix.getFirst();
        boolean isPlayerPortalInRange = resultsLocationFix.getSecond();

        if (isPlayerPortalInRange && Objects.isNull(fixedLocation)) {
            event.setSearchRadius(1);
            return;
        }

        event.setTo(fixedLocation);
    }

    private Pair<Location, Boolean> fixNetherPortalExitLocation(int radius, Location calculatedExitPosition) {
        World world = calculatedExitPosition.getWorld();

        ArrayList<Location> filteredLocations = new ArrayList<>();

        boolean isPlayerPortalInRange = false;

        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                for (int z = -radius; z < radius; z++) {
                    Block block = world.getBlockAt(calculatedExitPosition.getBlockX()+x, calculatedExitPosition.getBlockY()+y, calculatedExitPosition.getBlockZ()+z);
                    if (block.getType() == Material.NETHER_PORTAL) {
                        Location blockLocation = block.getLocation();
                        boolean isPlayerPortal = false;
                        Location firstCheck = RelativePosition.getLocationAboveN(blockLocation, 1);
                        if (firstCheck.getBlock().getType() == Material.RESPAWN_ANCHOR) {
                            isPlayerPortal = true;
                            isPlayerPortalInRange = true;
                        }

                        Location secondCheck = RelativePosition.getLocationAboveN(blockLocation, 2);
                        if (secondCheck.getBlock().getType() == Material.RESPAWN_ANCHOR) {
                            isPlayerPortal = true;
                            isPlayerPortalInRange = true;
                        }

                        if (!isPlayerPortal) {
                            filteredLocations.add(blockLocation);
                        }
                    }
                }
            }
        }

        Collections.sort(filteredLocations, new Comparator<Location>() {
            @Override
            public int compare(Location blockLocation1, Location blockLocation2) {

                double distance1 = blockLocation1.distance(calculatedExitPosition);
                double distance2 = blockLocation2.distance(calculatedExitPosition);

                return Double.compare(distance1, distance2);
            }
        });

        Location fixedLocation;

        if (filteredLocations.size() == 0) {
            fixedLocation = null;
        } else {
            fixedLocation = filteredLocations.get(0);
        }
        return new Pair<Location, Boolean>(fixedLocation, isPlayerPortalInRange);
    }

    @EventHandler
    public void onEntityPortalExit(EntityPortalExitEvent event) {
        if (event.getEntity() instanceof Player) {
            if (recentlyTeleported.keySet().contains((Player) event.getEntity())) {
                if (Duration.between(recentlyTeleported.get((Player) event.getEntity()), Instant.now()).toSeconds() >= 10) {
                    recentlyTeleported.remove((Player) event.getEntity());
                    PlayerPortalListener.lastTeleportedTo.remove((Player) event.getEntity());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player p = event.getPlayer();
        ArrayList<Location> locs = new ArrayList<>();
        locs.add(p.getLocation());
        locs.add(RelativePosition.getLocationNorth(p.getLocation()));
        locs.add(RelativePosition.getLocationEast(p.getLocation()));
        locs.add(RelativePosition.getLocationSouth(p.getLocation()));
        locs.add(RelativePosition.getLocationWest(p.getLocation()));
        for (Location loc : locs) {
            if (RelativePosition.getLocationAboveN(loc, 2).getBlock().getType().equals(Material.RESPAWN_ANCHOR)) {
                event.setCancelled(true);
            }
        }
    }

}
