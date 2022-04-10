package grafnus.portalshard.portals;


import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class Portal {

    private UUID uuid;
    private Location loc;

    public Portal(Location loc, UUID uuid) {
        this.loc = loc;
        this.uuid = uuid;
    }

}
