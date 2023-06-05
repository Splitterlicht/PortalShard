package grafnus.portalshard.craft;

import grafnus.portalshard.items.ITEMS;
import grafnus.portalshard.items.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import java.util.ArrayList;
import java.util.function.Consumer;
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
            event.getInventory().setResult(null);
        }

    }

    @EventHandler
    public void onInvMove(InventoryMoveItemEvent event) {
        //Bukkit.getLogger().log(Level.INFO, event.getInitiator().toString());
    }
    @EventHandler
    public void onInvMove(InventoryClickEvent event) {
        //Bukkit.getLogger().log(Level.INFO, event.getAction().name());
    }
}
