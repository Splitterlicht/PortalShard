package grafnus.portalshard.database.data;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class PlayerPermsData {

    private OfflinePlayer player;
    private float connection_id;
    private boolean use;
    private boolean charge;
    private boolean upgrade;
    private boolean destroy;

    public PlayerPermsData(float cID, OfflinePlayer player, boolean use, boolean charge, boolean upgrade, boolean destroy) {
        this.player = player;
        this.connection_id = cID;
        this.use = use;
        this.charge = charge;
        this.upgrade = upgrade;
        this.destroy = destroy;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public float getConnection_id() {
        return connection_id;
    }

    public boolean isUse() {
        return use;
    }

    public boolean isCharge() {
        return charge;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public boolean isDestroy() {
        return destroy;
    }
}
