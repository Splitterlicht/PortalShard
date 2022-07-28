package grafnus.portalshard.gui;

import dev.dbassett.skullcreator.SkullCreator;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.DBConnection;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.menu.scheme.StandardSchemeMappings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Function;

public class PortalMenu extends Gui {

    private float connectionID;
    private ConnectionData connection;
    private PortalData portal;

    public PortalMenu(Player player, PortalData portal) {

        super(player, 3, "Portal Einstellungen");
        this.connectionID = portal.getConnection_id();

        ArrayList<ConnectionData> data = DBConnection.getConnection(this.connectionID);

        if (data.size() <= 0)
            return;

        this.connection = data.get(0);
        this.portal = portal;
    }


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

            //getPlayer().sendMessage(connection.getPlayer().getUniqueId().toString() + "::" + getPlayer().getUniqueId().toString());

            if (connection.getPlayer().getUniqueId().equals(getPlayer().getUniqueId())) {
                this.setItem(11, ItemStackBuilder.of(Material.BARRIER)
                        .name("Settings")
                        .lore("")
                        .lore("This feature has not been implemented yet :(")
                        .build(() -> {}));
                /*
                ItemStack settings = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc1OTVhY2MxZjJjNDY5ZWIwZDNiNGEyYzJkODI1MmE4OTI1YjhkOTk5MmY4OWQyNWJjYjczM2EwMWQ0ZTFhIn19fQ==");

                this.setItem(11, ItemStackBuilder.of(settings)
                        .name("Settings")
                        .lore("")
                        .lore("Change the settings of the portal")
                        .lore("Opens another Menu")
                        .build(() -> {
                            SettingsMenu settingsMenu = new SettingsMenu(getPlayer(), getPlayer().getName(), portal);
                            settingsMenu.open();
                        }));

                 */
            } else {
                this.setItem(11, ItemStackBuilder.of(Material.BARRIER)
                        .name("Settings")
                        .lore("")
                        .lore("You cannot change the settings")
                        .build(() -> {}));
            }
            ItemStack info = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAxYWZlOTczYzU0ODJmZGM3MWU2YWExMDY5ODgzM2M3OWM0MzdmMjEzMDhlYTlhMWEwOTU3NDZlYzI3NGEwZiJ9fX0=");

            this.setItem(15, ItemStackBuilder.of(info)
                    .name("Portal Information")
                    .lore("")
                    .lore("Owner: " + connection.getPlayer().getName())
                    .lore("Charges: " + connection.getCharges() + "/20")
                    .lore("FFA Enabled: " + Boolean.toString(connection.isFfa()))
                    .lore("Level: " + connection.getLevel())
                    .build(() -> {}));

        }


    }
}
