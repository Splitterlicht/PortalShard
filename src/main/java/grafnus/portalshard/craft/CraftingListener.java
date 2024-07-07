package grafnus.portalshard.craft;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

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
