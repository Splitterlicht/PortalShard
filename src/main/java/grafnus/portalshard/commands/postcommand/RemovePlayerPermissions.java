package grafnus.portalshard.commands.postcommand;

import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.database.tables.DBPlayerPerms;
import grafnus.portalshard.database.tables.DBPortal;
import grafnus.portalshard.engine.PortalEngine;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.sound.sampled.Port;
import java.util.ArrayList;

public class RemovePlayerPermissions extends AbstractPostCommandInteraction {

    private OfflinePlayer target;

    public RemovePlayerPermissions(Player sender, OfflinePlayer target) {
        super(sender, Material.RESPAWN_ANCHOR);
        this.target = target;
    }

    @Override
    protected void execute(Block anchor) {
        Location portalSource = anchor.getLocation().add(new Vector(0, -2, 0));

        ArrayList<PortalData> pData = DBPortal.getPortal(portalSource);
        if (pData.size() != 1) {
            return;
        }
        float cID = pData.get(0).getConnection_id();
        ArrayList<ConnectionData> cData = DBConnection.getConnection(cID);
        if (cData.size() != 1) {
            return;
        }

        if (!PortalEngine.getInstance().getPlayerPermissionCheck().isOwner(cID, getSender())) {
            getSender().sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You need to be the Owner of the Portal to change it's settings!");
            return;
        }

        ConnectionData conn = cData.get(0);

        DBPlayerPerms.addIfNotPresent(cID, getSender());
        DBPlayerPerms.removePortal(cID, getTarget());

        getSender().sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You removed " + ChatColor.GOLD + getTarget().getName() + ChatColor.LIGHT_PURPLE + " from your portal!");
        return;
    }

    public OfflinePlayer getTarget() {
        return target;
    }
}
