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

import java.util.ArrayList;

public class UpdatePortalFFA extends AbstractPostCommandInteraction {

    private boolean value;

    public UpdatePortalFFA(Player sender, boolean value) {
        super(sender, Material.RESPAWN_ANCHOR);
        this.value = value;
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

        DBConnection.updateFFA(conn.getUuid(), value);

        getSender().sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You set the " + ChatColor.RED + "Free For All" + ChatColor.LIGHT_PURPLE + " option of your portal to " + ChatColor.RED + Boolean.toString(value));
        return;
    }

    public boolean isValue() {
        return value;
    }
}
