package grafnus.portalshard.database.data;

import org.bukkit.Location;

public class PortalData {

    private Location loc;
    private float connection_id;
    private float id;

    public PortalData(float cID, Location loc) {
        this.loc = loc;
        this.connection_id = cID;
    }

    public Location getLoc() {
        return loc;
    }

    public float getConnection_id() {
        return connection_id;
    }
}
