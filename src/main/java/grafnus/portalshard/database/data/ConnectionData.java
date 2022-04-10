package grafnus.portalshard.database.data;

import java.util.UUID;

public class ConnectionData {

    private UUID uuid;
    private int charges;

    public ConnectionData(UUID uuid, int charges) {
        this.uuid = uuid;
        this.charges = charges;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getCharges() {
        return charges;
    }
}
