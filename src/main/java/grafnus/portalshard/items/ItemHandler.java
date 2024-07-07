package grafnus.portalshard.items;

import com.sun.istack.NotNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemHandler {

    public static boolean isKey(@NotNull ItemStack item) {
        if (!item.getType().equals(ITEMS.KEY.getMaterial())) {
            return false;
        }
        if (!item.getItemMeta().getDisplayName().equals(ITEMS.KEY.getName())) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return false;
        }
        if (!meta.getLore().get(0).equals(ITEMS.KEY.getLore()[0])) {
            return false;
        }
        if (!meta.getLore().get(1).equals(ITEMS.KEY.getLore()[1])) {
            return false;
        }
        if (!meta.getLore().get(2).equals(ITEMS.KEY.getLore()[2])) {
            return false;
        }
        if (!meta.getLore().get(3).equals(ITEMS.KEY.getLore()[3])) {
            return false;
        }
        if (ITEMS.KEY.isEnchanted()) {
            if (item.getEnchantments().isEmpty()) {
                return false;
            }
        } else {
            if (!item.getEnchantments().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static ITEMS getUpgradeItem(ItemStack item) {
        ItemStack original = new ItemStack(item);
        original.setAmount(1);
        ItemStack compare = ItemFactory.buildItemFromTemplate(ITEMS.FIRST_UPGRADE);

        if (compare.equals(original)) {
            return ITEMS.FIRST_UPGRADE;
        }

        compare = ItemFactory.buildItemFromTemplate(ITEMS.SECOND_UPGRADE);

        if (compare.equals(original)) {
            return ITEMS.SECOND_UPGRADE;
        }

        compare = ItemFactory.buildItemFromTemplate(ITEMS.THIRD_UPGRADE);

        if (compare.equals(original)) {
            return ITEMS.THIRD_UPGRADE;
        }
        return null;
    }

}
