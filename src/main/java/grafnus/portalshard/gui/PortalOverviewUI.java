package grafnus.portalshard.gui;

import dev.dbassett.skullcreator.SkullCreator;
import grafnus.portalshard.database.DataSource;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.util.skulls.SKULL_NUMBERS;
import grafnus.portalshard.util.skulls.SKULL_SYMBOLS;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.ClickOptions;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.List;

public class PortalOverviewUI {
    private PortalData portalData;
    private Player player;

    public PortalOverviewUI(Player player, PortalData portalData) {
        this.player = player;
        this.portalData = portalData;
    }

    public void openMenu() {
        Menu menu = ChestMenu.builder(5).title("Portal Settings").redraw(true).build();



        ArrayList<ConnectionData> cd = DBConnection.getConnection(portalData.getConnection_id());
        if (cd.size() <= 0) {
            return;
        }

        ConnectionData c = cd.get(0);

        Slot playerHead = menu.getSlot(10);
        ItemStack ownerHead = SkullCreator.itemFromUuid(c.getPlayer().getUniqueId());

        ItemMeta ownerHeadMeta = ownerHead.getItemMeta();

        ownerHeadMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Owner: " + ChatColor.GOLD + c.getPlayer().getName());

        ownerHead.setItemMeta(ownerHeadMeta);

        playerHead.setItem(ownerHead);
        playerHead.setClickOptions(ClickOptions.DENY_ALL);

        Slot levelHead = menu.getSlot(13);
        ItemStack levelSkull = SkullCreator.itemFromBase64(SKULL_SYMBOLS.ARROW_UP.toString());
        ItemMeta levelSkullMeta = levelSkull.getItemMeta();

        levelSkullMeta.setDisplayName("Level: " + c.getLevel());

        levelSkull.setItemMeta(levelSkullMeta);
        levelHead.setItem(levelSkull);
        levelHead.setClickOptions(ClickOptions.DENY_ALL);

        Slot chargeCounterHead = menu.getSlot(16);

        ItemStack chargesSkull = SkullCreator.itemFromBase64(SKULL_SYMBOLS.GLOBE.toString());
        ItemMeta chargesSkullMeta = chargesSkull.getItemMeta();

        chargesSkullMeta.setDisplayName("Portal Information");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLUE + "Owner: " + ChatColor.WHITE + c.getPlayer().getName());
        lore.add(ChatColor.BLUE + "Charges Left: " + ChatColor.WHITE + c.getCharges());
        lore.add(ChatColor.BLUE + "UUID: " + ChatColor.WHITE + c.getUuid());
        chargesSkullMeta.setLore(lore);

        chargesSkull.setItemMeta(chargesSkullMeta);
        chargeCounterHead.setItem(chargesSkull);
        chargeCounterHead.setClickOptions(ClickOptions.DENY_ALL);

        Slot settingHead = menu.getSlot(31);

        ItemStack settingHeadItem = new ItemStack(Material.WRITABLE_BOOK);


        ItemMeta settingHeadItemMeta = settingHeadItem.getItemMeta();
        settingHeadItemMeta.setDisplayName("Settings");
        settingHeadItem.setItemMeta(settingHeadItemMeta);
        settingHead.setItem(settingHeadItem);
        settingHead.setClickHandler((player1, clickInformation) -> {
            PortalSettingsUI portalSettings = new PortalSettingsUI(player1, portalData);
            portalSettings.openMenu();
        });
        settingHead.setClickOptions(ClickOptions.DENY_ALL);

        menu.open(this.player);
        return;
    }

}