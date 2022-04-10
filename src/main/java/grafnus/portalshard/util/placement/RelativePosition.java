package grafnus.portalshard.util.placement;

import org.bukkit.Location;

public class RelativePosition {

    public static Location getLocationBelow(Location loc) {
        return getLocationBelowN(loc, 1);
    }

    public static Location getLocationBelowN(Location loc, int n) {
        return new Location(loc.getWorld(), loc.getX(), loc.getY() - n, loc.getZ());
    }

    public static Location getLocationAbove(Location loc) {
        return new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
    }
}
