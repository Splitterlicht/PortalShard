package grafnus.portalshard.craft;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CustomRecipeBuilder {

    private Plugin plugin;
    private NamespacedKey recipeKey;
    private ItemStack result;
    private Map<Character, ItemStack> ingredientMap;
    private String[] shape;
    private Consumer<CraftItemEvent> craftCallback;

    private CustomRecipeBuilder(Plugin plugin) {
        this.plugin = plugin;
        this.ingredientMap = new HashMap<>();
    }

    public static CustomRecipeBuilder create(Plugin plugin) {
        return new CustomRecipeBuilder(plugin);
    }

    public CustomRecipeBuilder withKey(String key) {
        this.recipeKey = new NamespacedKey(plugin, key);
        return this;
    }

    public CustomRecipeBuilder withResult(ItemStack result) {
        this.result = result;
        return this;
    }

    public CustomRecipeBuilder withIngredient(char character, ItemStack itemStack) {
        this.ingredientMap.put(character, itemStack);
        return this;
    }

    public CustomRecipeBuilder withShape(String... shape) {
        this.shape = shape;
        return this;
    }

    public CustomRecipeBuilder withCallback(Consumer<CraftItemEvent> callback) {
        this.craftCallback = callback;
        return this;
    }

    public void build() {
        CustomShapedRecipe recipe = new CustomShapedRecipe(recipeKey, result);

        recipe.shape(shape);

        if (craftCallback != null) {
            recipe.setCraftingCallback(craftCallback);
        }

        for (Map.Entry<Character, ItemStack> entry : ingredientMap.entrySet()) {
            recipe.setCustomIngredient(entry.getKey(), entry.getValue());
        }

        Bukkit.addRecipe(recipe);
        CraftingHandler.getInstance().addRecipe(recipeKey, recipe);
    }

}
