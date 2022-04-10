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
import java.util.logging.Level;

public class PortalTable {

    public static void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS `Portal` (" +
                "`id` FLOAT NOT NULL AUTO_INCREMENT," +
                "`world` VARCHAR(255) NOT NULL," +
                "`x` FLOAT NOT NULL," +
                "`y` FLOAT NOT NULL," +
                "`z` FLOAT NOT NULL," +
                "`uuid` VARCHAR(255) NOT NULL," +
                "`player` VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (`id`)" +
                ");";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            int status = p.executeUpdate();
            Bukkit.getLogger().log(Level.INFO, "Creating table 'Portal'! Status: " + status);
            return null;
        });
    }

    public static ArrayList<PortalData> getPortalByUuid(UUID uuid) {
        ArrayList<PortalData> data = new ArrayList<>();

        String query = "SELECT `world`, `x`, `y`, `z`, `uuid`, `player` FROM `Portal` WHERE `uuid` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, uuid.toString());
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                Location loc = new Location(Bukkit.getWorld(rs.getString("world")), rs.getFloat("x"), rs.getFloat("y"), rs.getFloat("z"));
                UUID id = UUID.fromString(rs.getString("uuid"));
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("player")));
                PortalData pd = new PortalData(loc, id, player);
                data.add(pd);
            }
            return null;
        });
        return data;
    }

    public static ArrayList<PortalData> getPortalByLocation(Location loc) {
        ArrayList<PortalData> data = new ArrayList<>();

        String query = "SELECT `world`, `x`, `y`, `z`, `uuid`, `player` FROM `Portal` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?;";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, loc.getWorld().getName());
            p.setDouble(2, loc.getBlockX());
            p.setDouble(3, loc.getBlockY());
            p.setDouble(4, loc.getBlockZ());
            //Bukkit.getServer().getLogger().log(Level.INFO, p.toString());
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                Location location = new Location(Bukkit.getWorld(rs.getString("world")), rs.getFloat("x"), rs.getFloat("y"), rs.getFloat("z"));
                UUID id = UUID.fromString(rs.getString("uuid"));
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("player")));
                PortalData pd = new PortalData(location, id, player);
                data.add(pd);
            }
            return null;
        });
        return data;
    }

    public static void addPortal(PortalData data) {
        String query = "INSERT INTO `Portal`" +
                "(`world`, `x`, `y`, `z`, `uuid`, `player`) " +
                "VALUES (?,?,?,?,?,?)";
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, data.getLoc().getWorld().getName());
            p.setFloat(2, data.getLoc().getBlockX());
            p.setFloat(3, data.getLoc().getBlockY());
            p.setFloat(4, data.getLoc().getBlockZ());
            p.setString(5, data.getUuid().toString());
            p.setString(6, data.getPlayer().getUniqueId().toString());

            Bukkit.getLogger().log(Level.INFO, p.toString());

            int status = p.executeUpdate();
            Bukkit.getLogger().log(Level.INFO, "Adding a portal to the table 'Portal'! Status: " + status);
            return null;
        });
    }

}
