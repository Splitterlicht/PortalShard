package grafnus.portalshard.database.tables;

import grafnus.portalshard.database.DataSource;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class ConnectionTable {

    public static void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS `Connection` ( " +
                "`id` FLOAT NOT NULL AUTO_INCREMENT, " +
                "`uuid` VARCHAR(255) NOT NULL, " +
                "`charges` INT NOT NULL, " +
                "PRIMARY KEY (`id`) );";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            int status = p.executeUpdate();
            Bukkit.getLogger().log(Level.INFO, "Creating table 'Connection'! Status: " + status);
            return null;
        });
    }

    public static void connectPortals(UUID uuid, int charges) {
        String query = "INSERT INTO `Connection`(`uuid`, `charges`) VALUES (?,?)";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, uuid.toString());
            p.setInt(2, charges);
            Bukkit.getLogger().log(Level.INFO, "Query: " + p);
            int status = p.executeUpdate();
            Bukkit.getLogger().log(Level.INFO, "Adding a portal to the table 'Connection'! Status: " + status);
            return null;
        });
    }

    public static ArrayList<ConnectionData> getConnection(UUID uuid) {
        ArrayList<ConnectionData> data = new ArrayList<>();

        String query = "SELECT `uuid`, `charges` FROM `Connection` WHERE `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, uuid.toString());
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                UUID id = UUID.fromString(rs.getString("uuid"));
                ConnectionData cd = new ConnectionData(id, rs.getInt("charges"));
                data.add(cd);
            }
            return null;
        });
        return data;
    }

    public static void updateCharges(UUID uuid, int i) {
        String update = "UPDATE `Connection` SET `charges` = ? WHERE CONCAT(`Connection`.`uuid`) = ? ;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(update);
            p.setInt(1, i);
            p.setString(2, uuid.toString());
            p.executeUpdate();
            //int in = p.executeUpdate(update);
            return null;
        });
    }
}
