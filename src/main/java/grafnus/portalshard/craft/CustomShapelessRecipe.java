package grafnus.portalshard.craft;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CustomShapelessRecipe extends ShapelessRecipe implements CustomRecipe {

    private Consumer<CraftItemEvent> callback = null;
    private ArrayList<ItemStack> ingredients = new ArrayList<>();

    public CustomShapelessRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result) {
        super(key, result);
    }

    public @NotNull ShapelessRecipe addIngredient(@NotNull ItemStack ingredient) {
        super.addIngredient(ingredient.getType());
        ingredients.add(ingredient);
        return this;
    }

    public @NotNull ArrayList<ItemStack> getIngredientItemStackMap() {
        return this.ingredients;
    }

    @Override
    public @NotNull Consumer<CraftItemEvent> getCraftingCallback() {
        return this.callback;
    }

    @Override
    public void processEvent(CraftItemEvent event) {
        // Do some checks, alter event
        executeCallback(event);
    }

    @Override
    public @NotNull CustomRecipe setCraftingCallback(Consumer<CraftItemEvent> callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public boolean canCraft(ItemStack[] recipeMatrix) {
        return false;
    }
}
