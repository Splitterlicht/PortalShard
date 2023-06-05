package grafnus.portalshard.craft;

import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.function.Consumer;

public class CraftingHandler {

    private static CraftingHandler instance;

    private HashMap<NamespacedKey, CustomRecipe> recipes = new HashMap<>();

    private CraftingHandler() {}

    public void addRecipe(NamespacedKey key, CustomRecipe recipe) {
        this.recipes.put(key, recipe);
    }

    public CustomRecipe getRecipe(NamespacedKey key) {
        if (recipes.containsKey(key)) {
            return recipes.get(key);
        }
        return null;
    }

    public static CraftingHandler getInstance() {
        if (instance == null) {
            synchronized (CraftingHandler.class) {
                if (instance == null) {
                    instance = new CraftingHandler();
                }
            }
        }
        return instance;
    }

}
