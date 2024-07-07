package grafnus.portalshard.listeners;

import grafnus.portalshard.items.ItemHandler;
import grafnus.portalshard.portals.PortalHandler;
import grafnus.portalshard.util.key.UUIDGrabber;
import grafnus.portalshard.util.placement.PlacementChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class KeyPlacementListener implements Listener {

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        ItemStack placed = e.getPlayer().getInventory().getItemInMainHand();
        if (!ItemHandler.isKey(placed)) {
            return;
        }
        e.setCancelled(true);
        // Check if it was set inside a portal
        if (!PlacementChecker.isPortalFrame(e.getBlockPlaced().getLocation())) {
            return;
        }

        PortalHandler.getInstance().createNewPortal(e.getBlockPlaced().getLocation(), UUIDGrabber.getUUIDFromKey(placed), e.getPlayer());

        if (placed.getAmount() == 1) {
            e.getPlayer().getInventory().setItemInMainHand(null);
        } else {
            placed.setAmount(1);
            e.getPlayer().getInventory().setItemInMainHand(placed);
        }
    }

}
