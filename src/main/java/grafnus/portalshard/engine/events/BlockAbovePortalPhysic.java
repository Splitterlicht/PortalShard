package grafnus.portalshard.engine.events;

import grafnus.portalshard.engine.Converter;
import grafnus.portalshard.util.placement.RelativePosition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.logging.Level;

public class BlockAbovePortalPhysic implements IEvent {
    @Override
    public boolean isEvent(EEvents event) {
        if (event.equals(EEvents.BLOCK_ABOVE_PORTAL_PHYSICS)) {
            return true;
        }
        return false;
    }

    @Override
    public void listen(Event event) {
        BlockPhysicsEvent e = Converter.convert(event, BlockPhysicsEvent.class);
        if (e == null)
            return;

        if (!e.getBlock().getType().equals(Material.RESPAWN_ANCHOR)) {
            return;
        }
        if (!RelativePosition.getLocationBelow(e.getBlock().getLocation()).getBlock().getType().equals(Material.NETHER_PORTAL)) {
            return;
        }
        if (!RelativePosition.getLocationBelowN(e.getBlock().getLocation(), 2).getBlock().getType().equals(Material.NETHER_PORTAL)) {
            return;
        }
        e.setCancelled(true);

    }

}
