package grafnus.portalshard.util.location;

import org.bukkit.Location;

public class LocationChecker {

    public static boolean isSameBlock(Location one, Location two) {
        if (!one.getWorld().getName().equals(two.getWorld().getName())) {
            return false;
        }
        if (one.getBlockX() != two.getBlockX()) {
            return false;
        }
        if (one.getBlockY() != two.getBlockY()) {
            return false;
        }
        if (one.getBlockZ() != two.getBlockZ()) {
            return false;
        }
        return true;
    }
}
