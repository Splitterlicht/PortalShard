package grafnus.portalshard.listeners;

import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.events.EEvents;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
