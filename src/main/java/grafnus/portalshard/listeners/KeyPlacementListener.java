package grafnus.portalshard.listeners;

import grafnus.portalshard.items.ItemHandler;
import grafnus.portalshard.portals.PortalHandler;
import grafnus.portalshard.util.key.UUIDGrabber;
import grafnus.portalshard.util.placement.PlacementChecker;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class KeyPlacementListener implements Listener {

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        //Bukkit.getLogger().log(Level.INFO, "DEBUG MSG: " + e.toString());
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
