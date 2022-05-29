package grafnus.portalshard.util.placement;

import org.bukkit.Location;

import java.math.BigInteger;

/**
 * This class is used to simplify the acquisition of locations relative to the current one.
 */

public class RelativePosition {

    /*
     *
     * Getters for a relative position on the X Axe
     *
     */

    /**
     * Gets the location one block to the west (-1 on X axe)
     * @param loc The source location
     * @return The location of the block to the west of the source location
     */
    public static Location getLocationWest(Location loc) {
        return getLocationWestN(loc, 1);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getLocationWestN(Location loc, int n) {
        return getRelativePositionOnXAxe(loc, (n * -1));
    }

    /**
     *
     * @param loc
     * @return
     */
    public static Location getLocationEast(Location loc) {
        return getRelativePositionOnXAxe(loc, 1);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getLocationEastN(Location loc, int n) {
        return getRelativePositionOnXAxe(loc, n);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getRelativePositionOnXAxe(Location loc, int n) {
        return new Location(loc.getWorld(), loc.getX() + n, loc.getY(), loc.getZ());
    }

    /*
     *
     * Getters for a relative position on the Y Axe
     *
     */

    /**
     * Gets the location one block below
     * @param loc The source location
     * @return The location of the block below the source location
     */
    public static Location getLocationBelow(Location loc) {
        return getLocationBelowN(loc, 1);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getLocationBelowN(Location loc, int n) {
        return getRelativePositionOnYAxe(loc, (n * -1));
    }

    /**
     *
     * @param loc
     * @return
     */
    public static Location getLocationAbove(Location loc) {
        return getRelativePositionOnYAxe(loc, 1);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getLocationAboveN(Location loc, int n) {
        return getRelativePositionOnYAxe(loc, n);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getRelativePositionOnYAxe(Location loc, int n) {
        return new Location(loc.getWorld(), loc.getX(), loc.getY() + n, loc.getZ());
    }

    /*
     *
     * Getters for a relative position on the Z Axe
     *
     */

    /**
     * Gets the location one block to the north (-1 on the X axe)
     * @param loc The source location
     * @return The location of the block to the north of the source location
     */
    public static Location getLocationNorth(Location loc) {
        return getLocationNorthN(loc, 1);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getLocationNorthN(Location loc, int n) {
        return getRelativePositionOnZAxe(loc, (n * -1));
    }

    /**
     *
     * @param loc
     * @return
     */
    public static Location getLocationSouth(Location loc) {
        return getRelativePositionOnZAxe(loc, 1);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getLocationSouthN(Location loc, int n) {
        return getRelativePositionOnZAxe(loc, n);
    }

    /**
     *
     * @param loc
     * @param n
     * @return
     */
    public static Location getRelativePositionOnZAxe(Location loc, int n) {
        return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + n);
    }
}
