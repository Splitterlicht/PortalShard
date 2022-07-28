package grafnus.portalshard.database.tables;

import grafnus.portalshard.database.DataSource;
import grafnus.portalshard.database.data.ConnectionData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class DBConnection extends DataTable {

    public DBConnection() {
        super("ConnectionConnection",
                "CREATE TABLE IF NOT EXISTS `connection` (" +
                "`id` FLOAT NOT NULL AUTO_INCREMENT," +
                "`uuid` VARCHAR(255) NOT NULL," +
                "`charges` INT NOT NULL, " +
                "`level` INT NOT NULL," +
                "`ffa` BOOLEAN NOT NULL," +
                "`created_by` VARCHAR(255) NOT NULL," +
                "`created` DATETIME," +
                "`updated` DATETIME," +
                "PRIMARY KEY (`id`)" +
                ");");
    }

    public static ConnectionData createConnection(OfflinePlayer player) {
        ConnectionData data = new ConnectionData(UUID.randomUUID(), 20, player);
        String query = "INSERT INTO `connection`(`uuid`, `charges`, `level`, `ffa`, `created_by`) VALUES (?,?,?,?, ?)";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, data.getUuid().toString());
            p.setInt(2, data.getCharges());
            p.setInt(3, data.getLevel());
            p.setBoolean(4, data.isFfa());
            p.setString(5, player.getUniqueId().toString());
            int status = p.executeUpdate();
            return null;
        });
        return data;
    }

    public static ArrayList<ConnectionData> getConnection(UUID uuid) {
        ArrayList<ConnectionData> data = new ArrayList<>();

        String query = "SELECT `uuid`, `charges`, `level`, `ffa`, `created_by` FROM `connection` WHERE `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, uuid.toString());
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                UUID id = UUID.fromString(rs.getString("uuid"));
                UUID player = UUID.fromString(rs.getString("created_by"));
                ConnectionData cd = new ConnectionData(id, rs.getInt("charges"), rs.getInt("level"), rs.getBoolean("ffa"), Bukkit.getOfflinePlayer(player));
                data.add(cd);
            }
            return null;
        });
        return data;
    }

    public static ArrayList<ConnectionData> getConnection(float id) {
        ArrayList<ConnectionData> data = new ArrayList<>();

        String query = "SELECT `uuid`, `charges`, `level`, `ffa`, `created_by` FROM `connection` WHERE `id` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setFloat(1, id);
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                UUID rs_id = UUID.fromString(rs.getString("uuid"));
                UUID player = UUID.fromString(rs.getString("created_by"));
                ConnectionData cd = new ConnectionData(rs_id, rs.getInt("charges"), rs.getInt("level"), rs.getBoolean("ffa"), Bukkit.getOfflinePlayer(player));
                data.add(cd);
            }
            return null;
        });
        return data;
    }

    public static float getConnectionID(UUID uuid) {
        ArrayList<Float> ids = new ArrayList<>();

        String query = "SELECT `id` FROM `connection` WHERE `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, uuid.toString());
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                ids.add(rs.getFloat("id"));
            }
            return null;
        });
        if (ids.size() > 0) {
            return ids.get(0);
        }
        return -1F;
    }

    public static void updateCharges(UUID uuid, int i) {
        String update = "UPDATE `connection` SET `charges` = ? WHERE CONCAT(`connection`.`uuid`) = ? ;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(update);
            p.setInt(1, i);
            p.setString(2, uuid.toString());
            p.executeUpdate();
            //int in = p.executeUpdate(update);
            return null;
        });
    }

    public static void updateLevel(UUID uuid, int i) {
        String update = "UPDATE `connection` SET `level` = ? WHERE CONCAT(`connection`.`uuid`) = ? ;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(update);
            p.setInt(1, i);
            p.setString(2, uuid.toString());
            p.executeUpdate();
            return null;
        });
    }

    public static void updateFFA(UUID uuid, boolean b) {
        String update = "UPDATE `connection` SET `ffa` = ? WHERE CONCAT(`connection`.`uuid`) = ? ;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(update);
            p.setBoolean(1, b);
            p.setString(2, uuid.toString());
            p.executeUpdate();
            return null;
        });
    }
}
