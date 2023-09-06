package grafnus.portalshard.engine;

import grafnus.portalshard.PERMISSION;
import grafnus.portalshard.data.DAO.ConnectionDAO;
import grafnus.portalshard.data.DAO.PlayerPermissionDAO;
import grafnus.portalshard.data.DO.Connection;
import grafnus.portalshard.data.DO.PlayerPermission;
import org.bukkit.entity.Player;

public class PermissionCheck {

    public boolean isOwner(Long cID, Player player) {
        Connection connection = ConnectionDAO.getConnectionById(cID);

        if (player.getUniqueId().equals(connection.getCreatorUUID())) {
            return true;
        }
        return false;
    }

    public boolean canUse(Long cID, Player player, boolean adminCheck) {
        if (adminCheck) {
            if (PERMISSION.MODERATOR_PORTAL_USE.isAllowed(player)) {
                return true;
            }
        }
        if (isOwner(cID, player)) {
            return true;
        }
        if (ConnectionDAO.getConnectionById(cID).isFfa()) {
            return true;
        }
        PlayerPermission playerPerm = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(cID, player);

        if (playerPerm != null) {
            if (playerPerm.isUse()) {
                return true;
            }
        }
        return false;
    }

    public boolean canUse(Long cID, Player player) {
        return canUse(cID, player, true);
    }

    public boolean canCharge(Long cID, Player player) {

        if (PERMISSION.MODERATOR_PORTAL_USE.isAllowed(player)) {
            return true;
        }
        if (isOwner(cID, player)) {
            return true;
        }
        PlayerPermission playerPerm = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(cID, player);

        if (ConnectionDAO.getConnectionById(cID).isFfa()) {
            return true;
        }

        if (playerPerm != null) {
            if (!playerPerm.isCharge()) {
                return false;
            }
        }
        return true;
    }

    public boolean canUpgrade(Long cID, Player player) {

        if (PERMISSION.MODERATOR_PORTAL_USE.isAllowed(player)) {
            return true;
        }
        if (isOwner(cID, player)) {
            return true;
        }
        PlayerPermission playerPerm = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(cID, player);

        if (playerPerm != null) {
            if (playerPerm.isUpgrade()) {
                return true;
            }
        }
        return false;
    }

    public boolean canDestroy(Long cID, Player player) {

        if (PERMISSION.MODERATOR_PORTAL_USE.isAllowed(player)) {
            return true;
        }
        if (isOwner(cID, player)) {
            return true;
        }
        PlayerPermission playerPerm = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(cID, player);

        if (playerPerm != null) {
            if (playerPerm.isDestroy()) {
                return true;
            }
        }
        return false;
    }

}
