package grafnus.portalshard.items;

import grafnus.portalshard.data.DAO.ConnectionDAO;
import grafnus.portalshard.data.HibernateDO.HibernateConnection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemFactory {

    public static ItemStack buildKey(@NotNull Player player) {
        //ConnectionData data = DBConnection.createConnection(player);
        HibernateConnection conn = new HibernateConnection(player);
        ConnectionDAO.saveConnection(conn);
        return buildKey(UUID.fromString(conn.getUuid()));
    }

    public static ItemStack buildKey(@NotNull UUID uuid) {
        return addItemUUID(buildItemFromTemplate(ITEMS.KEY), uuid);
    }

    private static ItemStack addItemUUID(@NotNull ItemStack item, UUID uuid) {
        ItemMeta m = item.getItemMeta();
        List<String> lore = m.getLore();
        lore.add("Key: " + uuid.toString());
        m.setLore(lore);
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack buildItemFromTemplate(@NotNull ITEMS item) {
        ItemStack i = new ItemStack(item.getMaterial());
        if (item.isEnchanted()) {
            i.addUnsafeEnchantment(Enchantment.MENDING, 1);
        }
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(item.getName());
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(item.getLore()));
        m.setLore(lore);
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(m);
        return i;
    }

}
