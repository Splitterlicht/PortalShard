package grafnus.portalshard.craft;

import com.sun.istack.NotNull;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CustomShapedRecipe extends ShapedRecipe implements CustomRecipe{

    private HashMap<Character, ItemStack> ingredients = new HashMap<Character, ItemStack>();
    private Consumer<CraftItemEvent> callback = null;

    public CustomShapedRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result) {
        super(key, result);
    }
    
    public @NotNull CustomShapedRecipe setIngredient(char key, @NotNull ItemStack ingredient) {
        ingredients.put(key, new ItemStack(ingredient));
        super.setIngredient(key, ingredient.getType());
        return this;
    }

    public @NotNull CustomShapedRecipe setCustomIngredient(char key, @NotNull ItemStack ingredient) {
        ingredients.put(key, new ItemStack(ingredient));
        super.setIngredient(key, ingredient.getType());
        return this;
    }

    public @NotNull Map<Character, ItemStack> getIngredientItemStackMap() {
        return ingredients;
    }

    @Override
    public @NotNull Consumer<CraftItemEvent> getCraftingCallback() {
        return callback;
    }

    @Override
    public void processEvent(CraftItemEvent event) {
        HumanEntity human = event.getWhoClicked();

        // On mass crafting needs to be implemented here. (Event does not get called multiple times)
        ItemStack[] recipeMatrix = getMatrix();
        ItemStack[] slotMatrix = event.getInventory().getMatrix();
        if (!canCraft(slotMatrix)) {
            event.setCancelled(true);
            return;
        }
        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            boolean canCraft = canCraft(slotMatrix);
            boolean playerHasSpace = (human.getInventory().firstEmpty() != -1);
            while (canCraft && playerHasSpace) {
                event.setCancelled(false);
                if (getCraftingCallback() != null)
                    getCraftingCallback().accept(event);
                if (event.isCancelled()) {
                    break;
                }
                event.setCancelled(true);

                executeCraft(slotMatrix, recipeMatrix);
                human.getInventory().addItem(event.getInventory().getResult());

                canCraft = canCraft(slotMatrix);
                playerHasSpace = (human.getInventory().firstEmpty() != -1);
            }
            event.getInventory().setMatrix(slotMatrix);
            return;
        } else {
            event.setCancelled(true);
            if (human.getOpenInventory().getCursor().getType() == Material.AIR) {
                executeCraft(slotMatrix, recipeMatrix);
                if (getCraftingCallback() != null)
                    getCraftingCallback().accept(event);
                human.getOpenInventory().setCursor(event.getInventory().getResult());
            }
        }
        event.getInventory().setMatrix(slotMatrix);
    }

    private void executeCraft(ItemStack[] present, ItemStack[] recipe) {
        for (int i = 0; i < present.length; i++) {
            ItemStack craftItem = present[i];

            int slotCost = recipe[i].getAmount();

            if (craftItem.getAmount() == slotCost) {
                craftItem = new ItemStack(Material.AIR);
            } else {
                craftItem.setAmount(craftItem.getAmount() - slotCost);
            }
            present[i] = craftItem;
        }
    }

    @Override
    public @NotNull CustomRecipe setCraftingCallback(Consumer<CraftItemEvent> callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public boolean canCraft(ItemStack[] invMatrix) {
        return CraftUtil.canCraft(invMatrix, getMatrix());
    }

    public @NotNull ItemStack[] getMatrix() {
        String[] matrixIngredientMap = (getShape()[0] + getShape()[1] + getShape()[2]).split("");

        ArrayList<ItemStack> ingredientMatrix = new ArrayList<>();

        for (String matrixIngredient : matrixIngredientMap) {
            ItemStack ingredient = null;
            if (matrixIngredient.equals(" ")) {
                ingredient = null;
            }
            ingredient = ingredients.get(matrixIngredient.toCharArray()[0]);
            ingredientMatrix.add(ingredient);
        }
        ItemStack[] arr = new ItemStack[ingredientMatrix.size()];
        arr = ingredientMatrix.toArray(arr);
        return arr;
    }


}
