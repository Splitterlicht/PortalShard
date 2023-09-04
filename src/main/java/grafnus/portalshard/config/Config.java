package grafnus.portalshard.config;

import grafnus.portalshard.PortalShard;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Config {

    // #####################[ ThreadSafeSingleton ]#########################

    private static volatile Config instance;

    private Config() {
        // loading configuration from disk into memory
        initConfiguration();
    }

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    // #####################[ ThreadSafeSingleton ]#########################

    private PortalShard plugin = PortalShard.getInstance();
    private String separator = ".";


    // ======[ Persistent Data ]======
    private boolean MySQLToggle;
    private String MySQLHost;
    private String MySQLPort;
    private String MySQLDatabase;
    private String MySQLUsername;
    private String MySQLPassword;


    private void initConfiguration() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        loadConfigIntoMemory();
        Bukkit.getLogger().log(Level.INFO, "[PortalShard] Config loaded successfully!");
    }

    private void loadConfigIntoMemory() {
        loadDataConfig();
    }

    private void loadDataConfig() {
        String prefix = "data";
        MySQLToggle = plugin.getConfig().getBoolean(prefix + separator + "enableMySQL", false);
        MySQLHost = plugin.getConfig().getString(prefix + separator + "MySQL" + separator + "Host", "localhost");
        MySQLPort = plugin.getConfig().getString(prefix + separator + "MySQL" + separator + "Port", "3306");
        MySQLDatabase = plugin.getConfig().getString(prefix + separator + "MySQL" + separator + "Database", "PortalShard");
        MySQLUsername = plugin.getConfig().getString(prefix + separator + "MySQL" + separator + "Username", "PortalShard");
        MySQLPassword = plugin.getConfig().getString(prefix + separator + "MySQL" + separator + "Password", "abcd1234ABCD!");
    }

    public boolean isMySQL() {
        return MySQLToggle;
    }

    public String getMySQLHost() {
        return MySQLHost;
    }

    public String getMySQLPort() {
        return MySQLPort;
    }

    public String getMySQLDatabase() {
        return MySQLDatabase;
    }

    public String getMySQLUsername() {
        return MySQLUsername;
    }

    public String getMySQLPassword() {
        return MySQLPassword;
    }
}
