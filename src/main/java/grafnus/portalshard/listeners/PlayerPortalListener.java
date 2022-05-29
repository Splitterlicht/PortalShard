package grafnus.portalshard.listeners;

import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.ConnectionTable;
import grafnus.portalshard.database.tables.PortalTable;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.events.EEvents;
import grafnus.portalshard.engine.task.TaskFactory;
import grafnus.portalshard.engine.task.UpdatePortalCharges;
import grafnus.portalshard.util.location.LocationChecker;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.ArrayList;

public class PlayerPortalListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {

        PortalEngine.getInstance().listenToEvent(event, EEvents.PLAYER_PORTAL);

        return;
        /*
        int[] y = {0, 1};
        for (int i : y){
            Location from = event.getFrom();

            Player pl = event.getPlayer();

            from.setY(pl.getLocation().getY() - i);

            ArrayList<PortalData> data = PortalTable.getPortalByLocation(from);
            if (data.size() == 1) {
                event.setCancelled(true);
                PortalData portal = data.get(0);
                ArrayList<ConnectionData> cd = ConnectionTable.getConnection(portal.getUuid());
                if (cd.size() <= 0) {
                    return;
                }
                ConnectionData c = cd.get(0);
                if (c.getCharges() == 0) {
                    String actionbar = ChatColor.LIGHT_PURPLE +  "No charges left!";

                    event.getPlayer().sendActionBar(Component.text(actionbar));
                    return;
                }
                ConnectionTable.updateCharges(c.getUuid(), c.getCharges() - 1);

                String actionbar = ChatColor.LIGHT_PURPLE +  "You have " + ChatColor.GOLD + (c.getCharges() - 1) + ChatColor.LIGHT_PURPLE + " charges left!";

                event.getPlayer().sendActionBar(Component.text(actionbar));

                ArrayList<PortalData> portals = PortalTable.getPortalByUuid(portal.getUuid());
                for (PortalData d : portals) {
                    UpdatePortalCharges task = new UpdatePortalCharges(d.getLoc());
                    TaskFactory.createTask(task);
                }

                ArrayList<PortalData> pair = PortalTable.getPortalByUuid(portal.getUuid());
                for (PortalData p : pair) {
                    if (!LocationChecker.isSameBlock(p.getLoc(), portal.getLoc())) {
                        event.setCancelled(false);
                        event.setTo(p.getLoc());
                    }
                }
            }
        }*/
    }

}
