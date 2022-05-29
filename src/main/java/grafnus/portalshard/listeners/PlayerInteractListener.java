package grafnus.portalshard.listeners;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.ConnectionTable;
import grafnus.portalshard.database.tables.PortalTable;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.events.EEvents;
import grafnus.portalshard.engine.task.TaskFactory;
import grafnus.portalshard.engine.task.UpdatePortalCharges;
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;

        if (!event.getClickedBlock().getType().equals(Material.RESPAWN_ANCHOR))
            return;

        PortalEngine.getInstance().listenToEvent(event, EEvents.PLAYER_INTERACT_RESPAWN_ANCHOR);

        return;
    }
}
