package grafnus.portalshard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public enum PERMISSION {
    MODERATOR_CHANGE_PERMISSION_USE("moderation.modify.use"),
    MODERATOR_CHANGE_PERMISSION_CHARGE("moderation.modify.charge"),
    MODERATOR_CHANGE_PERMISSION_UPGRADE("moderation.modify.upgrade"),
    MODERATOR_CHANGE_PERMISSION_DESTROY("moderation.modify.destroy"),
    MODERATOR_ADD_PLAYER_TO_PORTAL("moderation.modify.addplayer"),
    MODERATOR_CHANGE_PERMISSION_FFA("moderation.modify.use"),
    MODERATOR_MODIFY_PLAYER_LIST("moderation.modify.use"),
    MODERATOR_PORTAL_USE("moderation.use"),
    ;


    private String permission;

    PERMISSION(String permissionKey) {
        permission = "portalshard." + permissionKey;
    }

    public boolean isAllowed(Player player) {
        Bukkit.getLogger().log(Level.INFO, "Checking for " + player.getName());
        if (player.isOp()) {
            Bukkit.getLogger().log(Level.INFO, "IS OP!");
            return true;
        }
        return player.hasPermission(permission);
    }
}
