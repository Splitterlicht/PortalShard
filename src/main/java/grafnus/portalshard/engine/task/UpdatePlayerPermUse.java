package grafnus.portalshard.engine.task;

import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.database.tables.DBPortal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.logging.Level;

public class UpdatePlayerPermUse implements ITaskBlueprint {

    private Location location;

    public UpdatePlayerPermUse(Location loc) {
        this.location = loc;
    }

    @Override
    public BukkitRunnable build() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                Block anchor = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 2, location.getBlockZ()).getBlock();
                if (!anchor.getType().equals(Material.RESPAWN_ANCHOR)) {
                    return;
                }
                ArrayList<PortalData> portals = DBPortal.getPortal(location);
                if (portals.size() != 1) {
                    Bukkit.getLogger().log(Level.INFO, "Could not find Portal!");
                }

                PortalData portal = portals.get(0);

                ArrayList<ConnectionData> conns = DBConnection.getConnection(portal.getConnection_id());
                if (conns.size() != 1) {
                    Bukkit.getLogger().log(Level.INFO, "Could not find portal Connection!");
                }

                ConnectionData conn = conns.get(0);

                RespawnAnchor data = (RespawnAnchor) anchor.getBlockData();
                data.setCharges((conn.getCharges() / 5) % 5);
                anchor.setBlockData(data);
            }
        };
    }
}