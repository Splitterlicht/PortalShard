package grafnus.portalshard.portals;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.data.DAO.ConnectionDAO;
import grafnus.portalshard.data.DAO.PortalDAO;
import grafnus.portalshard.data.HibernateDO.HibernateConnection;
import grafnus.portalshard.data.HibernateDO.HibernatePortal;
import grafnus.portalshard.engine.task.CreatePortalITask;
import grafnus.portalshard.engine.task.TaskFactory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PortalHandler {

    private PortalHandler() {
    }

    private static PortalHandler ph ;

    // Class body

    public boolean createNewPortal(Location loc, UUID uuid, Player player) {
        BukkitRunnable t = new BukkitRunnable() {
            @Override
            public void run() {
                // Get Connection ID

                HibernateConnection connection = ConnectionDAO.getConnectionByUuid(uuid);

                // Check for amount of connected portals!

                int count = PortalDAO.getPortalsByConnectionId(connection.getId()).size();
                if (count > 1) {
                    player.sendMessage("You cannot create a third portal using the Key!");
                    loc.getBlock().setType(Material.AIR);
                    return;
                }

                // Create new Portal

                HibernatePortal portal = new HibernatePortal(connection.getId(), loc);
                PortalDAO.savePortal(portal);

                CreatePortalITask task = new CreatePortalITask(loc);
                TaskFactory.createTask(task);
                //PortalHandler.getInstance().addOpenPortalCreations(loc);
            }
        };
        t.runTaskAsynchronously(PortalShard.getInstance());
        return true;
    }

    public UUID createPortalUUID() {
        return UUID.randomUUID();
    }

    // End of class body

    public static synchronized PortalHandler getInstance(){
        if (ph == null){
            ph = new PortalHandler();
        }
        return ph;
    }
}
