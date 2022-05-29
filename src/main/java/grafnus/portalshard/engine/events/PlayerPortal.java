package grafnus.portalshard.engine.events;

import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.ConnectionTable;
import grafnus.portalshard.database.tables.PortalTable;
import grafnus.portalshard.engine.task.TaskFactory;
import grafnus.portalshard.engine.task.UpdatePortalCharges;
import grafnus.portalshard.util.location.LocationChecker;
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.ArrayList;
import java.util.logging.Level;

public class PlayerPortal implements IEvent {
    @Override
    public boolean isEvent(EEvents event) {
        return event.equals(EEvents.PLAYER_PORTAL);
    }

    @Override
    public void listen(Event event) {
        if (!isInstanceOfEvent(event))
            return;

        PlayerPortalEvent e = convert(event);

        Location loc = getSourceLocation(e.getFrom());

        Player player = e.getPlayer();

        ArrayList<PortalData> data = PortalTable.getPortalByLocation(loc);
        if (data.size() == 1) {
            e.setCancelled(true);
            PortalData portal = data.get(0);
            ArrayList<ConnectionData> cd = ConnectionTable.getConnection(portal.getUuid());
            if (cd.size() <= 0) {
                return;
            }
            ConnectionData c = cd.get(0);
            if (c.getCharges() == 0) {
                String actionbar = ChatColor.LIGHT_PURPLE +  "No charges left!";

                e.getPlayer().sendActionBar(Component.text(actionbar));
                return;
            }
            ConnectionTable.updateCharges(c.getUuid(), c.getCharges() - 1);

            String actionbar = ChatColor.LIGHT_PURPLE +  "You have " + ChatColor.GOLD + (c.getCharges() - 1) + ChatColor.LIGHT_PURPLE + " charges left!";

            e.getPlayer().sendActionBar(Component.text(actionbar));

            ArrayList<PortalData> portals = PortalTable.getPortalByUuid(portal.getUuid());
            for (PortalData d : portals) {
                UpdatePortalCharges task = new UpdatePortalCharges(d.getLoc());
                TaskFactory.createTask(task);
            }

            ArrayList<PortalData> pair = PortalTable.getPortalByUuid(portal.getUuid());
            for (PortalData p : pair) {
                if (!LocationChecker.isSameBlock(p.getLoc(), portal.getLoc())) {
                    e.setCancelled(false);
                    e.setTo(p.getLoc());
                }
            }
        }
    }

    private boolean isInstanceOfEvent(Event event) {
        return event instanceof PlayerPortalEvent;
    }

    private PlayerPortalEvent convert(Event event) {
        if (event instanceof PlayerPortalEvent)
            return (PlayerPortalEvent) event;
        else
            return null;
    }

    private Location getSourceLocation(Location loc) {
        Location result = loc;
        Location below = RelativePosition.getLocationBelow(loc);
        if (below.getBlock().getType().equals(Material.NETHER_PORTAL)) {
            result = below;
        }
        return result;
    }

    /*private boolean doChecks(PlayerPortalEvent event) {

        if (!event.)

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return false;
        if (!event.getClickedBlock().getType().equals(Material.RESPAWN_ANCHOR))
            return false;
        return true;
    }*/
}
