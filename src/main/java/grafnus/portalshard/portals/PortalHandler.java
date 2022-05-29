package grafnus.portalshard.portals;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.ConnectionTable;
import grafnus.portalshard.database.tables.PortalTable;
import grafnus.portalshard.engine.task.CreatePortalITask;
import grafnus.portalshard.engine.task.TaskFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
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
                ArrayList<PortalData> portals = PortalTable.getPortalByUuid(uuid);
                if (portals.size() > 1) {
                    player.sendMessage("You cannot create a third portal using the Key!");
                    loc.getBlock().setType(Material.AIR);
                    return;
                }
                PortalData portal = new PortalData(loc, uuid, Bukkit.getOfflinePlayer(player.getUniqueId()));
                PortalTable.addPortal(portal);
                //Bukkit.getLogger().log(Level.INFO, player.displayName().toString() + " has created a portal at " + loc.toString());
                portals = PortalTable.getPortalByUuid(uuid);
                if (portals.size() == 2) {
                    ConnectionTable.connectPortals(uuid, 20);
                }

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
