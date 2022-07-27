package grafnus.portalshard.listeners;

import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.events.EEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysicsListener implements Listener {

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
        PortalEngine.getInstance().listenToEvent(event, EEvents.BLOCK_BELOW_PORTAL_PHYSICS);
        PortalEngine.getInstance().listenToEvent(event, EEvents.BLOCK_ABOVE_PORTAL_PHYSICS);
    }
}
