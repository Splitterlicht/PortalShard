package grafnus.portalshard.util.key;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class UUIDGrabber {

    public static UUID getUUIDFromKey(ItemStack key) {
        List<String> lore = key.getItemMeta().getLore();

        for (String s : lore) {
            if (s.startsWith("Key: ")) {
                return UUID.fromString(s.replace("Key: ", ""));
            }
        }
        return null;
    }

}
