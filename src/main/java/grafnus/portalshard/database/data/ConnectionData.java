package grafnus.portalshard.database.data;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class ConnectionData {

    private UUID uuid;
    private int charges;
    private int level;
    private boolean ffa; // FFA = Free For All
    private OfflinePlayer player;

    public ConnectionData(UUID uuid, int charges, OfflinePlayer player) {
        this.uuid = uuid;
        this.charges = charges;
        this.level = 1;
        this.ffa = false;
        this.player = player;
    }

    public ConnectionData(UUID uuid, int charges, int level, OfflinePlayer player) {
        this.uuid = uuid;
        this.charges = charges;
        this.level = level;
        this.ffa = false;
        this.player = player;
    }

    public ConnectionData(UUID uuid, int charges, boolean ffa, OfflinePlayer player) {
        this.uuid = uuid;
        this.charges = charges;
        this.level = 1;
        this.ffa = ffa;
        this.player = player;
    }

    public ConnectionData(UUID uuid, int charges, int level, boolean ffa, OfflinePlayer player) {
        this.uuid = uuid;
        this.charges = charges;
        this.level = level;
        this.ffa = ffa;
        this.player = player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getCharges() {
        return charges;
    }

    public int getLevel() {
        return level;
    }

    public boolean isFfa() {
        return ffa;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}
