package grafnus.portalshard.engine.events;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.database.tables.DBPortal;
import grafnus.portalshard.engine.Converter;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.task.TaskFactory;
import grafnus.portalshard.engine.task.UpdatePortalCharges;
import grafnus.portalshard.listeners.PlayerPortalListener;
import grafnus.portalshard.util.location.LocationChecker;
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.logging.Level;

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

        ArrayList<PortalData> data = DBPortal.getPortal(loc);

        BukkitRunnable remove = new BukkitRunnable() {
            @Override
            public void run() {
                PlayerPortalListener.lastTeleportedTo.remove(player);
                PlayerPortalListener.recentlyTeleported.remove(player);
            }
        };
        remove.runTaskLater(PortalShard.getInstance(), 200);

        if (data.size() == 1) {
            PlayerPortalListener.recentlyTeleported.add(player);
            PortalData portal = data.get(0);
            ArrayList<ConnectionData> cd = DBConnection.getConnection(portal.getConnection_id());
            if (cd.size() <= 0) {
                return;
            }

            ConnectionData c = cd.get(0);
            if (!PortalEngine.getInstance().getPlayerPermissionCheck().canUse(DBConnection.getConnectionID(c.getUuid()), player)) {
                String errorActionBar = ChatColor.LIGHT_PURPLE +  "You are not permitted to use the portal!";

                player.sendActionBar(Component.text(errorActionBar));
                return;
            }

            ArrayList<PortalData> pair = DBPortal.getPortalByConnID(portal.getConnection_id());

            if (pair.size() != 2) {
                String actionbar = ChatColor.LIGHT_PURPLE +  "No portal endpoint!";

                player.sendActionBar(Component.text(actionbar));
                return;
            }

            if (c.getCharges() == 0) {
                String actionbar = ChatColor.LIGHT_PURPLE +  "No charges left!";

                player.sendActionBar(Component.text(actionbar));
                return;
            }
            DBConnection.updateCharges(c.getUuid(), c.getCharges() - 1);

            String actionbar = ChatColor.LIGHT_PURPLE +  "You have " + ChatColor.GOLD + (c.getCharges() - 1) + ChatColor.LIGHT_PURPLE + " charges left!";

            player.sendActionBar(Component.text(actionbar));

            for (PortalData d : pair) {
                UpdatePortalCharges task = new UpdatePortalCharges(d.getLoc());
                TaskFactory.createTask(task);
            }

            for (PortalData p : pair) {
                if (!LocationChecker.isSameBlock(p.getLoc(), portal.getLoc())) {

                    Location dest = p.getLoc().add(new Vector(0.5, 0, 0.5));
                    dest.setYaw(player.getLocation().getYaw());
                    dest.setPitch(player.getLocation().getPitch());

                    PlayerPortalListener.lastTeleportedTo.put(player, dest);
                    player.teleport(dest);
                }
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
