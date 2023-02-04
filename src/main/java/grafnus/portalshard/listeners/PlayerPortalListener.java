package grafnus.portalshard.listeners;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.events.EEvents;
import grafnus.portalshard.util.location.LocationChecker;
import grafnus.portalshard.util.placement.RelativePosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class PlayerPortalListener implements Listener {

    public static ArrayList<Player> recentlyTeleported = new ArrayList<>();
    public static HashMap<Player, Location> lastTeleportedTo = new HashMap<>();

    @EventHandler
    public void onEntityPortalTouch(EntityPortalEnterEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) event.getEntity();
        if (!recentlyTeleported.contains(p)) {
            PortalEngine.getInstance().listenToEvent(event, EEvents.PLAYER_PORTAL);
        }

    }

    @EventHandler
    public void onEntityPortalExit(EntityPortalExitEvent event) {
        if (event.getEntity() instanceof Player) {
            if (recentlyTeleported.contains((Player) event.getEntity())) {
                recentlyTeleported.remove((Player) event.getEntity());
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
