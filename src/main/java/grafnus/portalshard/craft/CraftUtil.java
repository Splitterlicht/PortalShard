package grafnus.portalshard.craft;

import org.bukkit.inventory.ItemStack;

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
            if (ingredient.hasItemMeta()) {
                if (!ingredient.getItemMeta().getItemFlags().equals(slot.getItemMeta().getItemFlags())) {
                    return false;
                }
            }

            if (ingredient.getAmount() > slot.getAmount()) {
                return false;
            }
        }
        return true;
    }

}
