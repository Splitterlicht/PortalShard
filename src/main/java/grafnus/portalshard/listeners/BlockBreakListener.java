package grafnus.portalshard.listeners;

import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.events.EEvents;
import grafnus.portalshard.util.placement.PlacementChecker;
import grafnus.portalshard.util.placement.RelativePosition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.GenericGameEvent;

import java.util.logging.Level;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Bukkit.getLogger().log(Level.INFO, "Calling Obsidian Break Engine Event");
        PortalEngine.getInstance().listenToEvent(event, EEvents.CRYING_OBSIDIAN_BREAK);
    }

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {

        if (!event.getBlock().getType().equals(Material.RESPAWN_ANCHOR)) {
            return;
        }
        if (!RelativePosition.getLocationBelow(event.getBlock().getLocation()).getBlock().getType().equals(Material.NETHER_PORTAL)) {
            return;
        }
        if (!RelativePosition.getLocationBelowN(event.getBlock().getLocation(), 2).getBlock().getType().equals(Material.NETHER_PORTAL)) {
            return;
        }
        event.setCancelled(true);
    }
}
