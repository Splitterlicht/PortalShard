package grafnus.portalshard.items;

import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.portals.PortalHandler;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ItemFactory {

    @SuppressWarnings("depricated")
    public static ItemStack buildItem(@NotNull ITEMS item, @NotNull Player player) {
        ConnectionData data = DBConnection.createConnection(player);
        return buildItem(item, data.getUuid(), player);
    }

    public static ItemStack buildItem(@NotNull ITEMS item, UUID uuid, @NotNull Player player) {
        ItemStack i = new ItemStack(item.getMaterial());
        if (item.isEnchanted()) {
            i.addUnsafeEnchantment(Enchantment.PIERCING, 1);
        }
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(item.getName());
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(item.getLore()));
        if (item.equals(ITEMS.KEY)) {
            lore.add("Key: " + uuid.toString());
        }
        m.setLore(lore);
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(m);
        return i;
    }

}
