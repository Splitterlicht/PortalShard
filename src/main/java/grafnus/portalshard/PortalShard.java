package grafnus.portalshard;

import grafnus.portalshard.config.Config;
import grafnus.portalshard.craft.CraftingListener;
import grafnus.portalshard.data.HibernateUtil;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

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
        getServer().getPluginManager().registerEvents(new CraftingListener(), this);
        loadDependencies();
        PortalEngine.getInstance().start();
    }

    public void loadDependencies() {
        //Config
        this.getDataFolder().mkdirs();
        Config.getInstance();
        //Database
        HibernateUtil.getSessionFactory();
        //Canvas
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), PortalShard.getInstance());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
