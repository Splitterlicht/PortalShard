package grafnus.portalshard.util.placement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.logging.Level;

public class PlacementChecker {

    private static ArrayList<Location> getPossibleBlockPositionsNOSO(Location loc) {
        ArrayList<Location> blocks = new ArrayList<Location>();
        for (int i = 0; i < 3; i++) {
            blocks.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + i, loc.getBlockZ() + 1));
            blocks.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + i, loc.getBlockZ() - 1));
        }
        blocks.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + 2, loc.getBlockZ()));
        return blocks;
    }

    private static ArrayList<Location> getPossibleBlockPositionsEAWE(Location loc) {
        ArrayList<Location> blocks = new ArrayList<Location>();
        for (int i = 0; i < 3; i++) {
            blocks.add(new Location(loc.getWorld(), loc.getBlockX() + 1, loc.getBlockY() + i, loc.getBlockZ()));
            blocks.add(new Location(loc.getWorld(), loc.getBlockX() - 1, loc.getBlockY() + i, loc.getBlockZ()));
        }
        blocks.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + 2, loc.getBlockZ()));
        return blocks;
    }

    public static boolean isPortalFrame(Location loc) {
        Location backup = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        ArrayList<Location> blocksNOSO = getPossibleBlockPositionsNOSO(loc);
        ArrayList<Location> blocksEAWE = getPossibleBlockPositionsEAWE(loc);

        boolean result = true;
        for (Location l : blocksNOSO) {
            if (!l.getWorld().getBlockAt(l.getBlockX(), l.getBlockY(), l.getBlockZ()).getType().equals(Material.CRYING_OBSIDIAN)) {
                result = false;
            }
        }
        if (result)
            return true;

        result = true;
        for (Location l : blocksEAWE) {
            if (!l.getWorld().getBlockAt(l.getBlockX(), l.getBlockY(), l.getBlockZ()).getType().equals(Material.CRYING_OBSIDIAN)) {
                result = false;
            }
        }
        if (result)
            return true;
        loc = backup;
        return false;
    }

    public static PORTAL_ORIENTATION getOrientation(Location loc) {
        if (loc.getWorld().getBlockAt(loc.getBlockX() + 1, loc.getBlockY(), loc.getBlockZ()).getType().equals(Material.CRYING_OBSIDIAN) && loc.getWorld().getBlockAt(loc.getBlockX() - 1, loc.getBlockY(), loc.getBlockZ()).getType().equals(Material.CRYING_OBSIDIAN)) {
            return PORTAL_ORIENTATION.NOSO;
        } else if (loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() + 1).getType().equals(Material.CRYING_OBSIDIAN) && loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() - 1).getType().equals(Material.CRYING_OBSIDIAN)) {
            return PORTAL_ORIENTATION.EAWE;
        }
        return PORTAL_ORIENTATION.INVALID;
    }

    public static Location getLocationAbove(Location loc) {
        return new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public static Location getLocationBelow(Location loc) {
        return new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public static Location getLocationSouth(Location loc) {
        return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1, loc.getYaw(), loc.getPitch());
    }

    public static Location getLocationNorth(Location loc) {
        return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1, loc.getYaw(), loc.getPitch());
    }

    public static Location getLocationEast(Location loc) {
        return new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public static Location getLocationWest(Location loc) {
        return new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
}
