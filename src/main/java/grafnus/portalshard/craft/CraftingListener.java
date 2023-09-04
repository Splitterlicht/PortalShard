package grafnus.portalshard.craft;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import grafnus.portalshard.PortalShard;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import java.util.logging.Level;

public class CraftingListener implements Listener {

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        CustomRecipe recipe = null;
        if (event.getRecipe() instanceof ShapedRecipe) {
            recipe = (CustomRecipe) CraftingHandler.getInstance().getRecipe(((ShapedRecipe) event.getRecipe()).getKey());
        } else {
            return;
        }
        if (recipe == null) {
            return;
        }
        Bukkit.getLogger().log(Level.INFO, "Event (CraftItemEvent) Called for a Custom Recipe!");
        recipe.processEvent(event);
    }

    @EventHandler
    public void onPlayerPrepareCraft(PrepareItemCraftEvent event) {
        CustomRecipe recipe = null;
        if (event.getRecipe() instanceof ShapedRecipe) {
            recipe = (CustomRecipe) CraftingHandler.getInstance().getRecipe(((ShapedRecipe) event.getRecipe()).getKey());
        } else {
            return;
        }
        if (recipe == null) {
            return;
        }
        Bukkit.getLogger().log(Level.INFO, "Event (PrepareItemCraftEvent) Called for a Custom Recipe!");
        ItemStack[] slotMatrix = event.getInventory().getMatrix();
        if (!recipe.canCraft(slotMatrix)) {
            Bukkit.getLogger().log(Level.INFO, "Event (PrepareItemCraftEvent) Can not craft!");
            event.getInventory().setResult(null);
        }
        else {
            event.getInventory().setResult(recipe.getResult());
        }
    }
}
