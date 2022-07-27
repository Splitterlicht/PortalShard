package grafnus.portalshard;

import grafnus.portalshard.commands.BaseCommand;
import grafnus.portalshard.database.DataSource;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class PortalShard extends JavaPlugin {

    private static PortalShard instance;

    public static PortalShard getInstance() {
        return PortalShard.instance;
    }

    @Override
    public void onEnable() {
        PortalShard.instance = this;
        getServer().getPluginManager().registerEvents(new KeyPlacementListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPhysicsListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerPortalListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        //getServer().getPluginCommand("portal").setExecutor(new BaseCommand());
        DataSource.getInstance().initTables();
        PortalEngine.getInstance().start();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
