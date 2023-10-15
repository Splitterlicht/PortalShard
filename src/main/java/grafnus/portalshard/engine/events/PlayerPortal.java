package grafnus.portalshard.engine.events;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.data.DAO.ConnectionDAO;
import grafnus.portalshard.data.DAO.PortalDAO;
import grafnus.portalshard.data.HibernateDO.HibernateConnection;
import grafnus.portalshard.data.HibernateDO.HibernatePortal;
import grafnus.portalshard.engine.Converter;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.task.TaskFactory;
import grafnus.portalshard.engine.task.UpdatePortalCharges;
import grafnus.portalshard.listeners.PlayerPortalListener;
import grafnus.portalshard.util.location.LocationChecker;
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class PlayerPortal implements IEvent {
    @Override
    public boolean isEvent(EEvents event) {
        return event.equals(EEvents.PLAYER_PORTAL);
    }

    @Override
    public void listen(Event event) {
        EntityPortalEnterEvent e = Converter.convert(event, EntityPortalEnterEvent.class);
        if (e == null)
            return;

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        Location loc = getSourceLocation(e.getLocation());

        HibernatePortal portal = PortalDAO.getPortalByLocation(loc);
        if (portal == null) {
            return;
        }


        BukkitRunnable remove = new BukkitRunnable() {
            @Override
            public void run() {
                if (!PlayerPortalListener.recentlyTeleported.keySet().contains(player)) {
                    return;
                }
                if (Duration.between(PlayerPortalListener.recentlyTeleported.get(player), Instant.now()).toSeconds() >= 10) {
                    PlayerPortalListener.recentlyTeleported.remove(player);
                    PlayerPortalListener.lastTeleportedTo.remove(player);
                }
            }
        };
        remove.runTaskLater(PortalShard.getInstance(), 200);

        HibernateConnection connection = portal.getConnection();
        if (connection == null) {
            return;
        }
        if (!PortalEngine.getInstance().getPlayerPermissionCheck().canUse(connection.getId(), player)) {
            String errorActionBar = ChatColor.LIGHT_PURPLE +  "You are not permitted to use the portal!";

            player.sendActionBar(Component.text(errorActionBar));
            return;
        }

        List<HibernatePortal> portalPair = PortalDAO.getPortalsByConnectionId(portal.getConnectionId());

        if (portalPair.size() != 2) {
            String actionbar = ChatColor.LIGHT_PURPLE +  "No portal endpoint!";

            player.sendActionBar(Component.text(actionbar));
            return;
        }

        if (connection.getCharges() == 0) {
            String actionbar = ChatColor.LIGHT_PURPLE +  "No charges left!";

            player.sendActionBar(Component.text(actionbar));
            return;
        }
        if (connection.getLevel() != 4) {
            if (PortalEngine.getInstance().getPlayerPermissionCheck().canUse(connection.getId(), player, false)) {
                int newAmountCharges = connection.getCharges() - 1;
                connection.setCharges(newAmountCharges);
                ConnectionDAO.saveConnection(connection);
                String actionbar = ChatColor.LIGHT_PURPLE +  "You have " + ChatColor.GOLD + (newAmountCharges) + ChatColor.LIGHT_PURPLE + " charges left!";
                player.sendActionBar(Component.text(actionbar));
            }
        }

        for (HibernatePortal p : portalPair) {
            UpdatePortalCharges task = new UpdatePortalCharges(p.getLocation());
            TaskFactory.createTask(task);
        }

        for (HibernatePortal p : portalPair) {
            if (!LocationChecker.isSameBlock(p.getLocation(), portal.getLocation())) {

                Location dest = p.getLocation().add(new Vector(0.5, 0, 0.5));
                dest.setYaw(player.getLocation().getYaw());
                dest.setPitch(player.getLocation().getPitch());
                PlayerPortalListener.recentlyTeleported.put(player, Instant.now());
                PlayerPortalListener.lastTeleportedTo.put(player, dest);
                player.teleport(dest);
            }
        }
    }

    private Location getSourceLocation(Location loc) {
        Location result = loc;
        Location below = RelativePosition.getLocationBelow(loc);
        if (below.getBlock().getType().equals(Material.NETHER_PORTAL)) {
            result = below;
        }
        return result;
    }
}
