package grafnus.portalshard.engine.events;

import grafnus.portalshard.engine.Converter;
import grafnus.portalshard.util.placement.RelativePosition;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class BlockBelowPortalPhysic implements IEvent {
    @Override
    public boolean isEvent(EEvents event) {
        if (event.equals(EEvents.BLOCK_BELOW_PORTAL_PHYSICS)) {
            return true;
        }
        return false;
    }

    @Override
    public void listen(Event event) {
        BlockPhysicsEvent e = Converter.convert(event, BlockPhysicsEvent.class);
        if (e == null)
            return;

        if (!RelativePosition.getLocationAbove(e.getBlock().getLocation()).getBlock().getType().equals(Material.NETHER_PORTAL)) {
            return;
        }

        if (!RelativePosition.getLocationAboveN(e.getBlock().getLocation(), 2).getBlock().getType().equals(Material.NETHER_PORTAL)) {
            return;
        }

        if (!RelativePosition.getLocationAboveN(e.getBlock().getLocation(), 3).getBlock().getType().equals(Material.RESPAWN_ANCHOR)) {
            return;
        }

        e.setCancelled(true);

    }

}
