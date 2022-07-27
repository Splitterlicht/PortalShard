package grafnus.portalshard.database.tables;

import grafnus.portalshard.database.DataSource;
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
        super("Portal", "CREATE TABLE IF NOT EXISTS `player_perms` (" +
                "`id` FLOAT NOT NULL AUTO_INCREMENT," +
                "`connection_id` FLOAT NOT NULL," +
                "`uuid` VARCHAR(255) NOT NULL," +
                "`use` BOOLEAN NOT NULL," +
                "`charge` BOOLEAN NOT NULL," +
                "`upgrade` BOOLEAN NOT NULL," +
                "`destroy` BOOLEAN NOT NULL," +
                "`created_by` VARCHAR(255) NOT NULL," +
                "`created` DATETIME," +
                "PRIMARY KEY (`id`)" +
                ");");
    }

    public static float addPortal(PortalData data) {
        String query = "INSERT INTO `portal`" +
                "(`connection_id`, `world`, `x`, `y`, `z`) " +
                "VALUES (?,?,?,?,?,?)";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setFloat(1, data.getConnection_id());
            p.setString(2, data.getLoc().getWorld().getName());
            p.setFloat(3, data.getLoc().getBlockX());
            p.setFloat(4, data.getLoc().getBlockY());
            p.setFloat(5, data.getLoc().getBlockZ());

            int status = p.executeUpdate();
            return null;
        });

        return getPortalID(data);
    }

    public static ArrayList<PortalData> getPortal(Location loc) {
        ArrayList<PortalData> data = new ArrayList<>();

        String query = "SELECT `world`, `x`, `y`, `z`, `connection_id` FROM `portal` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?;";

        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, loc.getWorld().getName());
            p.setDouble(2, loc.getBlockX());
            p.setDouble(3, loc.getBlockY());
            p.setDouble(4, loc.getBlockZ());
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                float cID = rs.getFloat("connection_id");

                Location location = new Location(Bukkit.getWorld(rs.getString("world")), rs.getFloat("x"), rs.getFloat("y"), rs.getFloat("z"));
                PortalData pd = new PortalData(cID, location);
                data.add(pd);
            }
            return null;
        });
        return data;
    }



    public static ArrayList<PortalData> getPortalByConnID(float cID) {
        ArrayList<PortalData> data = new ArrayList<>();

        String query = "SELECT `world`, `x`, `y`, `z`, `connection_id`, `created_by` FROM `portal` WHERE `connection_id` = ?;";

        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setFloat(1, cID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {

                Location location = new Location(Bukkit.getWorld(rs.getString("world")), rs.getFloat("x"), rs.getFloat("y"), rs.getFloat("z"));
                PortalData pd = new PortalData(rs.getFloat("connection_id"), location);
                data.add(pd);
            }
            return null;
        });
        return data;
    }

    public static float getPortalID(PortalData data) {
        String queryPortalID = "SELECT `id` FROM `portal` WHERE `connection_id` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ? AND `created_by` = ?;";
        float[] id = new float[1];
        DataSource.getInstance().execute( (conn -> {
            PreparedStatement p = conn.prepareStatement(queryPortalID);
            p.setFloat(1, data.getConnection_id());
            p.setString(2, data.getLoc().getWorld().getName());
            p.setFloat(3, data.getLoc().getBlockX());
            p.setFloat(4, data.getLoc().getBlockY());
            p.setFloat(5, data.getLoc().getBlockZ());
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                id[0] = rs.getFloat("id");
            }
            return null;
        }));
        return id[0];
    }

    public static float getConnectionID(PortalData data) {
        String queryPortalID = "SELECT `connection_id` FROM `portal` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ? AND `created_by` = ?;";
        float[] id = new float[1];
        DataSource.getInstance().execute( (conn -> {
            PreparedStatement p = conn.prepareStatement(queryPortalID);
            p.setString(1, data.getLoc().getWorld().getName());
            p.setFloat(2, data.getLoc().getBlockX());
            p.setFloat(3, data.getLoc().getBlockY());
            p.setFloat(4, data.getLoc().getBlockZ());
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                id[0] = rs.getFloat("id");
            }
            return null;
        }));
        return id[0];
    }

    public static void removePortal(Location loc) {
        String query = "DELETE FROM `portal` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, loc.getWorld().getName());
            p.setFloat(2, loc.getBlockX());
            p.setFloat(3, loc.getBlockY());
            p.setFloat(4, loc.getBlockZ());

            int status = p.executeUpdate();
            return null;
        });
    }

}
