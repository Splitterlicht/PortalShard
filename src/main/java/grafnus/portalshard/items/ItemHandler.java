package grafnus.portalshard.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

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

}