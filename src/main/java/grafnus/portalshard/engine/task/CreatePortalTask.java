package grafnus.portalshard.engine.task;

import grafnus.portalshard.util.placement.PORTAL_ORIENTATION;
import grafnus.portalshard.util.placement.PlacementChecker;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.scheduler.BukkitRunnable;

public class CreatePortalTask implements TaskBlueprint {

    private Location location;

    public CreatePortalTask(Location loc) {
        location = loc;
    }

    @Override
    public BukkitRunnable build() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                Block anchor = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 2, location.getBlockZ()).getBlock();
                anchor.setType(Material.RESPAWN_ANCHOR);
                RespawnAnchor anchorData = (RespawnAnchor) anchor.getBlockData();
                anchorData.setCharges(anchorData.getMaximumCharges());
                anchor.setBlockData(anchorData);

                Block lowerPortal = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()).getBlock();
                lowerPortal.setType(Material.NETHER_PORTAL);
                Orientable lowerOrientable = (Orientable) lowerPortal.getBlockData();
                if (PlacementChecker.getOrientation(location).equals(PORTAL_ORIENTATION.NOSO)) {
                    lowerOrientable.setAxis(Axis.X);
                } else {
                    lowerOrientable.setAxis(Axis.Z);
                }
                lowerPortal.setBlockData(lowerOrientable);

                Block upperPortal = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 1, location.getBlockZ()).getBlock();
                upperPortal.setType(Material.NETHER_PORTAL);
                Orientable upperOrientable = (Orientable) upperPortal.getBlockData();
                if (PlacementChecker.getOrientation(location) == PORTAL_ORIENTATION.NOSO) {
                    upperOrientable.setAxis(Axis.X);
                } else {
                    upperOrientable.setAxis(Axis.Z);
                }
                upperPortal.setBlockData(upperOrientable);
            }
        };
    }
}
