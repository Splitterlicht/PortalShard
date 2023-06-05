package grafnus.portalshard.craft;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

public class CraftUtil {

    public static boolean canCraft(ItemStack[] present, ItemStack[] recipe) {
        for (int i = 0; i < recipe.length; i++) {
            ItemStack ingredient = recipe[i];
            ItemStack slot = present[i];

            //Bukkit.getLogger().log(Level.INFO, "Debug: present: " + slot.toString() + " recipe: " + ingredient.toString());

            if (!ingredient.getType().equals(slot.getType())) {
                return false;
            }
            if (!ingredient.getItemMeta().equals(slot.getItemMeta())) {
                return false;
            }
            if (!ingredient.getEnchantments().equals(slot.getEnchantments())) {
                return false;
            }
            if (!ingredient.getItemFlags().equals(slot.getItemFlags())) {
                return false;
            }
            if (!ingredient.getRarity().equals(slot.getRarity())) {
                return false;
            }

            if (ingredient.getAmount() > slot.getAmount()) {
                return false;
            }
        }
        return true;
    }

}
