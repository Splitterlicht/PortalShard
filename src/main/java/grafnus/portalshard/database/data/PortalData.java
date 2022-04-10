package grafnus.portalshard.database.data;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PortalData {

    private OfflinePlayer player;
    private Location loc;
    private UUID uuid;

    public PortalData(Location loc, UUID uuid, OfflinePlayer player) {
        this.loc = loc;
        this.uuid = uuid;
        this.player = player;
    }

    public Location getLoc() {
        return loc;
    }

    public UUID getUuid() {
        return uuid;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}
