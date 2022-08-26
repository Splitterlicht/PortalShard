package grafnus.portalshard.database.tables;

import grafnus.portalshard.database.DataSource;
import grafnus.portalshard.database.data.PlayerPermsData;
import grafnus.portalshard.database.data.PortalData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class DBPlayerPerms extends DataTable {
    public DBPlayerPerms() {
        super("PlayerPerms", "CREATE TABLE IF NOT EXISTS `player_perms` (" +
                "`id` FLOAT NOT NULL AUTO_INCREMENT," +
                "`connection_id` FLOAT NOT NULL," +
                "`uuid` VARCHAR(255) NOT NULL," +
                "`use` BOOLEAN NOT NULL," +
                "`charge` BOOLEAN NOT NULL," +
                "`upgrade` BOOLEAN NOT NULL," +
                "`destroy` BOOLEAN NOT NULL," +
                "`created` DATETIME," +
                "PRIMARY KEY (`id`)" +
                ");");
    }

    public static void addPlayerPerm(PlayerPermsData data) {
        String query = "INSERT INTO `player_perms`" +
                "(`connection_id`, `uuid`, `use`, `charge`, `upgrade`, `destroy`) " +
                "VALUES (?,?,?,?,?,?)";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setFloat(1, data.getConnection_id());
            p.setString(2, data.getPlayer().getUniqueId().toString());
            p.setBoolean(3, data.isUse());
            p.setBoolean(4, data.isCharge());
            p.setBoolean(5, data.isUpgrade());
            p.setBoolean(6, data.isDestroy());

            int status = p.executeUpdate();
            return null;
        });
    }

    public static ArrayList<PlayerPermsData> getPlayerPerms(float cID) {
        ArrayList<PlayerPermsData> data = new ArrayList<>();

        String query = "SELECT `uuid`, `use`, `charge`, `upgrade`, `destroy` FROM `player_perms` WHERE `connection_id` = ?;";

        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setFloat(1, cID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {

                String uuid = rs.getString("uuid");
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));

                boolean use = rs.getBoolean("use");
                boolean charge = rs.getBoolean("charge");
                boolean upgrade = rs.getBoolean("upgrade");
                boolean destroy = rs.getBoolean("destroy");

                PlayerPermsData d = new PlayerPermsData(cID, player, use, charge, upgrade, destroy);

                data.add(d);
            }
            return null;
        });
        return data;
    }

    public static PlayerPermsData getPlayerPerm(float cID, OfflinePlayer player) {
        ArrayList<PlayerPermsData> data = getPlayerPerms(cID);

        for (PlayerPermsData d : data) {
            if (d.getPlayer().equals(player)) {
                return d;
            }
        }
        return null;
    }

    public static PlayerPermsData addIfNotPresent(float cID, OfflinePlayer player) {
        PlayerPermsData data = getPlayerPerm(cID, player);
        if (data == null) {
            data = new PlayerPermsData(cID, player, false, true, false, false);
            addPlayerPerm(data);
        }
        return data;
    }

    public static void setUse(float cID, OfflinePlayer player, boolean use) {
        PlayerPermsData data = addIfNotPresent(cID, player);

        String update = "UPDATE `player_perms` SET `use` = ? WHERE `connection_id` = ? AND `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(update);
            p.setBoolean(1, use);
            p.setFloat(2, cID);
            p.setString(3, player.getUniqueId().toString());
            p.executeUpdate();
            return null;
        });

    }

    public static void setCharge(float cID, OfflinePlayer player, boolean charge) {
        PlayerPermsData data = addIfNotPresent(cID, player);

        String update = "UPDATE `player_perms` SET `charge` = ? WHERE `connection_id` = ? AND `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(update);
            p.setBoolean(1, charge);
            p.setFloat(2, cID);
            p.setString(3, player.getUniqueId().toString());
            p.executeUpdate();
            return null;
        });

    }

    public static void setUpgrade(float cID, OfflinePlayer player, boolean upgrade) {
        PlayerPermsData data = addIfNotPresent(cID, player);

        String update = "UPDATE `player_perms` SET `upgrade` = ? WHERE `connection_id` = ? AND `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(update);
            p.setBoolean(1, upgrade);
            p.setFloat(2, cID);
            p.setString(3, player.getUniqueId().toString());
            p.executeUpdate();
            return null;
        });

    }

    public static void setDestroy(float cID, OfflinePlayer player, boolean destroy) {
        PlayerPermsData data = addIfNotPresent(cID, player);

        String update = "UPDATE `player_perms` SET `destroy` = ? WHERE `connection_id` = ? AND `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(update);
            p.setBoolean(1, destroy);
            p.setFloat(2, cID);
            p.setString(3, player.getUniqueId().toString());
            p.executeUpdate();
            return null;
        });

    }

    public static void removePortal(float cID, OfflinePlayer player) {
        String query = "DELETE FROM `player_perms` WHERE `connection_id` = ? AND `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setFloat(1, cID);
            p.setString(2, player.getUniqueId().toString());

            int status = p.executeUpdate();
            return null;
        });
    }

}
