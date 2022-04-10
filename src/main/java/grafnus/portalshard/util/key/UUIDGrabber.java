package grafnus.portalshard.util.key;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class UUIDGrabber {

    public static UUID getUUIDFromKey(ItemStack key) {
        List<String> lore = key.getItemMeta().getLore();
        if (lore.size() == 5) {
            String uuid = lore.get(4);
            return UUID.fromString(uuid);
        }
        return null;
    }

}
