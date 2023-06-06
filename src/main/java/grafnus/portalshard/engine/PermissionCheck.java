package grafnus.portalshard.engine;

import grafnus.portalshard.PERMISSION;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PlayerPermsData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.database.tables.DBPlayerPerms;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PermissionCheck {

    public boolean isOwner(float cID, Player player) {
        ArrayList<ConnectionData> cList = DBConnection.getConnection(cID);
        if (cList.size() < 1) {
            return false;
        }
        ConnectionData cData = cList.get(0);
        if (cData.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) {
            return true;
        }
        return false;
    }

    public boolean canUse(float cID, Player player, boolean adminCheck) {
        if (adminCheck) {
            if (PERMISSION.MODERATOR_PORTAL_USE.isAllowed(player)) {
                return true;
            }
        }
        if (isOwner(cID, player)) {
            return true;
        }
        if (DBConnection.getConnection(cID).get(0).isFfa()) {
            return true;
        }
        PlayerPermsData ppData = DBPlayerPerms.getPlayerPerm(cID, player);

        if (ppData != null) {
            if (ppData.isUse()) {
                return true;
            }
        }
        return false;
    }

    public boolean canUse(float cID, Player player) {
        return canUse(cID, player, true);
    }

    public boolean canCharge(float cID, Player player) {

        if (PERMISSION.MODERATOR_PORTAL_USE.isAllowed(player)) {
            return true;
        }
        if (isOwner(cID, player)) {
            return true;
        }
        PlayerPermsData ppData = DBPlayerPerms.getPlayerPerm(cID, player);

        if (DBConnection.getConnection(cID).get(0).isFfa()) {
            return true;
        }

        if (ppData != null) {
            if (!ppData.isCharge()) {
                return false;
            }
        }
        return true;
    }

    public boolean canUpgrade(float cID, Player player) {

        if (PERMISSION.MODERATOR_PORTAL_USE.isAllowed(player)) {
            return true;
        }
        if (isOwner(cID, player)) {
            return true;
        }
        PlayerPermsData ppData = DBPlayerPerms.getPlayerPerm(cID, player);

        if (ppData != null) {
            if (ppData.isUpgrade()) {
                return true;
            }
        }
        return false;
    }

    public boolean canDestroy(float cID, Player player) {

        if (PERMISSION.MODERATOR_PORTAL_USE.isAllowed(player)) {
            return true;
        }
        if (isOwner(cID, player)) {
            return true;
        }
        PlayerPermsData ppData = DBPlayerPerms.getPlayerPerm(cID, player);

        if (ppData != null) {
            if (ppData.isDestroy()) {
                return true;
            }
        }
        return false;
    }

}
