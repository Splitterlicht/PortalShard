package grafnus.portalshard.engine;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.engine.events.*;
import grafnus.portalshard.listeners.PlayerPortalListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class Listener {

    private ArrayList<IEvent> listeners = new ArrayList<IEvent>();

    private BukkitRunnable cooldown = new BukkitRunnable() {
        @Override
        public void run() {

            for (Player p : PlayerPortalListener.lastTeleportedTo.keySet()) {
                Location loc = PlayerPortalListener.lastTeleportedTo.get(p);
                if ((p.getLocation().getBlockX() != loc.getBlockX()) || (p.getLocation().getBlockY() != loc.getBlockY()) || (p.getLocation().getBlockZ() != loc.getBlockZ())) {

                    BukkitRunnable remove = new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location loc = PlayerPortalListener.lastTeleportedTo.get(p);
                            if (loc == null)
                                return;

                            if (loc.distance(p.getLocation()) >= 2) {
                                PlayerPortalListener.lastTeleportedTo.remove(p);
                                PlayerPortalListener.recentlyTeleported.remove(p);
                            }
                        }
                    };

                    remove.runTaskLaterAsynchronously(PortalShard.getInstance(), 5L);


                }
            }
        }
    };

    public Listener() {
    }

    public void startListening() {
        listeners.add(new PlayerInteractRespawnAnchor());
        listeners.add(new PlayerPortal());
        listeners.add(new CryingObsidianBreak());
        listeners.add(new RespawnAnchorBreak());
        listeners.add(new BlockAbovePortalPhysic());
        listeners.add(new BlockBelowPortalPhysic());

        cooldown.runTaskTimerAsynchronously(PortalShard.getInstance(), 0L, 20L);

    }

    public void stopListening() {
        listeners.clear();
    }

    public void listen(Event event, EEvents type) {
        for (IEvent e : listeners) {
            if (!e.isEvent(type))
                continue;
            e.listen(event);
        }
    }

}
