package grafnus.portalshard.craft;

import com.sun.istack.NotNull;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.function.Consumer;

public interface CustomRecipe extends Recipe {

    public @NotNull
    Consumer<CraftItemEvent> getCraftingCallback();

    public default void executeCallback(CraftItemEvent event) {
        if (getCraftingCallback() != null) {
            getCraftingCallback().accept(event);
        }
    }

    public void processEvent(CraftItemEvent event);

    public @NotNull CustomRecipe setCraftingCallback(Consumer<CraftItemEvent> callback);

    public boolean canCraft(ItemStack[] recipeMatrix);

}
