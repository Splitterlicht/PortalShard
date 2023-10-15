package grafnus.portalshard.engine.task;

import grafnus.portalshard.data.DAO.PortalDAO;
import grafnus.portalshard.data.HibernateDO.HibernateConnection;
import grafnus.portalshard.data.HibernateDO.HibernatePortal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class UpdatePortalCharges implements ITaskBlueprint {

    private Location location;

    public UpdatePortalCharges(Location loc) {
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

                HibernatePortal portal = PortalDAO.getPortalByLocation(location);
                if (portal == null) {
                    Bukkit.getLogger().log(Level.INFO, "Could not find Portal!");
                    return;
                }

                HibernateConnection connection = portal.getConnection();
                if (connection == null) {
                    Bukkit.getLogger().log(Level.INFO, "Could not find portal Connection!");
                    return;
                }

                RespawnAnchor data = (RespawnAnchor) anchor.getBlockData();
                data.setCharges((connection.getCharges() / 5) % 5);
                anchor.setBlockData(data);
            }
        };
    }
}
