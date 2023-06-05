package grafnus.portalshard.craft;

import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public interface CustomRecipe extends Recipe {

    public @NotNull Consumer<CraftItemEvent> getCraftingCallback();

    public default void executeCallback(CraftItemEvent event) {
        if (getCraftingCallback() != null) {
            getCraftingCallback().accept(event);
        }
    }

    public void processEvent(CraftItemEvent event);

    public @NotNull CustomRecipe setCraftingCallback(Consumer<CraftItemEvent> callback);

    public boolean canCraft(ItemStack[] recipeMatrix);

}
