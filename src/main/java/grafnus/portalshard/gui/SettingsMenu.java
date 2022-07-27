package grafnus.portalshard.gui;

import dev.dbassett.skullcreator.SkullCreator;
import grafnus.portalshard.database.data.PortalData;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.paginated.PaginatedGui;
import me.lucko.helper.menu.paginated.PaginatedGuiBuilder;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.menu.scheme.StandardSchemeMappings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SettingsMenu extends Gui {

    private PortalData portal;

    public SettingsMenu(Player player, String title, PortalData data) {
        super(player, 3, "Settings for: " + title);
        this.portal = data;
    }

    private boolean isFFA = false;

    private static final MenuScheme BUTTONS = new MenuScheme(StandardSchemeMappings.STAINED_GLASS)
            .mask("111111111")
            .mask("100000001")
            .mask("111111111")
            .scheme(15, 15, 15, 15, 15, 15, 15, 15, 15)
            .scheme(15, 15)
            .scheme(15, 15, 15, 15, 15, 15, 15, 15, 15);

    @Override
    public void redraw() {

        if (isFirstDraw()) {
            BUTTONS.apply(this);
        }

        ItemStack add = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTVkMmNiMzg0NThkYTE3ZmI2Y2RhY2Y3ODcxNjE2MDJhMjQ5M2NiZjkzMjMzNjM2MjUzY2ZmMDdjZDg4YTljMCJ9fX0=");
        this.setItem(10, ItemStackBuilder.of(add)
                .name("Add Players")
                .lore("")
                .lore("Let players use you portal,")
                .lore("charge it, upgrade it or")
                .lore("destroy it.")
                .build(() -> {
                    Function<PaginatedGui, List<Item>> model = new Function<PaginatedGui, List<Item>>() {
                        @Override
                        public List<Item> apply(PaginatedGui paginatedGui) {
                            if (paginatedGui instanceof PlayerSelectorMenu)
                            {
                                ArrayList<Item> items = new ArrayList<Item>();
                                OfflinePlayer[] players = Bukkit.getOfflinePlayers();
                                for (OfflinePlayer player : players) {
                                    ItemStack playerHead = SkullCreator.itemFromUuid(player.getUniqueId());
                                    Item i = ItemStackBuilder.of(playerHead)
                                            .name(player.getName())
                                            .lore("Click to add player to add him to portal")
                                            .build(() -> {});
                                    items.add(i);
                                }
                                return items;
                            }
                            return new ArrayList<Item>();
                        }
                    };


                    new PlayerSelectorMenu(model, getPlayer(), PaginatedGuiBuilder.create(), portal);
                    getPlayer().sendMessage("Open another Menu!");
                }));
        ItemStack manage = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTVkMmNiMzg0NThkYTE3ZmI2Y2RhY2Y3ODcxNjE2MDJhMjQ5M2NiZjkzMjMzNjM2MjUzY2ZmMDdjZDg4YTljMCJ9fX0=");
        this.setItem(13, ItemStackBuilder.of(manage)
                .name("Manage Players")
                .lore("")
                .lore("Let players use you portal,")
                .lore("charge it, upgrade it or")
                .lore("destroy it.")
                .build(() -> {
                    getPlayer().sendMessage("Open another Menu!");
                }));


        ItemStack ffa = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTVkMmNiMzg0NThkYTE3ZmI2Y2RhY2Y3ODcxNjE2MDJhMjQ5M2NiZjkzMjMzNjM2MjUzY2ZmMDdjZDg4YTljMCJ9fX0=");

        ItemStackBuilder toggle = ItemStackBuilder.of(ffa)
                .name("Toggle FFA")
                .lore("")
                .lore("Toggle the Free For All mode.")
                .lore("Decide whether all players can ")
                .lore("or cannot use the portal!")
                .lore("")
                .lore("Currently:");
        if (isFFA) {
            toggle.lore("&6&lENABLED")
                    .lore("&8DISABLED");
        } else {
            toggle.lore("&8ENABLED")
                    .lore("&6&lDISABLED");
        }

        Item toggleItem = toggle.build(() -> {
            isFFA = !isFFA;
            redraw();
        });

        this.setItem(16, toggleItem);
    }
}
