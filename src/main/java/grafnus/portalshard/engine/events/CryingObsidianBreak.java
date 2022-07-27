package grafnus.portalshard.engine.events;

import grafnus.portalshard.engine.Converter;
import grafnus.portalshard.util.placement.RelativePosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CryingObsidianBreak implements IEvent {
    @Override
    public boolean isEvent(EEvents event) {
        if (event.equals(EEvents.CRYING_OBSIDIAN_BREAK)) {
            return true;
        }
        return false;
    }

    @Override
    public void listen(Event event) {
        BlockBreakEvent e = Converter.convert(event, BlockBreakEvent.class);
        if (e == null)
            return;
        if (!e.getBlock().getType().equals(Material.CRYING_OBSIDIAN))
            return;

        Location bl = e.getBlock().getLocation();

        ArrayList<Location> locs = new ArrayList<>();
        locs.add(RelativePosition.getLocationNorth(bl));
        locs.add(RelativePosition.getLocationEast(bl));
        locs.add(RelativePosition.getLocationSouth(bl));
        locs.add(RelativePosition.getLocationWest(bl));

        for (int i = 0; i < locs.size(); i++) {
            Location loc = locs.get(i);
            Block b = loc.getBlock();
            if (b.getType().equals(Material.NETHER_PORTAL)) {
                e.setCancelled(true);
                break;
            }
            // if Respawn Anchor add the block below to be checked!
            if (b.getType().equals(Material.RESPAWN_ANCHOR)) {
                locs.add(RelativePosition.getLocationBelow(loc));
            }
        }
    }
}
