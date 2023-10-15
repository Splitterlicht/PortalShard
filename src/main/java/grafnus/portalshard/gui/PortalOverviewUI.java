package grafnus.portalshard.gui;

import dev.dbassett.skullcreator.SkullCreator;
import grafnus.portalshard.PERMISSION;
import grafnus.portalshard.data.HibernateDO.HibernateConnection;
import grafnus.portalshard.data.HibernateDO.HibernatePortal;
import grafnus.portalshard.util.skulls.SKULL_SYMBOLS;
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

public class PortalOverviewUI {
    private HibernatePortal portal;
    private Player player;

    public PortalOverviewUI(Player player, HibernatePortal portal) {
        this.player = player;
        this.portal = portal;
    }

    public void openMenu() {
        Menu menu = ChestMenu.builder(5).title("Portal Settings").redraw(true).build();


        HibernateConnection connection = portal.getConnection();
        if (connection == null) {
            return;
        }

        Slot playerHead = menu.getSlot(10);
        ItemStack ownerHead = SkullCreator.itemFromUuid(connection.getCreatorPlayer().getUniqueId());

        ItemMeta ownerHeadMeta = ownerHead.getItemMeta();

        ownerHeadMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Owner: " + ChatColor.GOLD + connection.getCreatorPlayer().getName());

        ownerHead.setItemMeta(ownerHeadMeta);

        playerHead.setItem(ownerHead);
        playerHead.setClickOptions(ClickOptions.DENY_ALL);

        Slot levelHead = menu.getSlot(13);
        ItemStack levelSkull = SkullCreator.itemFromBase64(SKULL_SYMBOLS.ARROW_UP.toString());
        ItemMeta levelSkullMeta = levelSkull.getItemMeta();

        levelSkullMeta.setDisplayName("Level: " + connection.getLevel());

        levelSkull.setItemMeta(levelSkullMeta);
        levelHead.setItem(levelSkull);
        levelHead.setClickOptions(ClickOptions.DENY_ALL);

        Slot chargeCounterHead = menu.getSlot(16);

        ItemStack chargesSkull = SkullCreator.itemFromBase64(SKULL_SYMBOLS.GLOBE.toString());
        ItemMeta chargesSkullMeta = chargesSkull.getItemMeta();

        chargesSkullMeta.setDisplayName("Portal Information");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLUE + "Owner: " + ChatColor.WHITE + connection.getCreatorPlayer().getName());
        lore.add(ChatColor.BLUE + "Charges Left: " + ChatColor.WHITE + connection.getCharges());
        lore.add(ChatColor.BLUE + "UUID: " + ChatColor.WHITE + connection.getUuid());
        chargesSkullMeta.setLore(lore);

        chargesSkull.setItemMeta(chargesSkullMeta);
        chargeCounterHead.setItem(chargesSkull);
        chargeCounterHead.setClickOptions(ClickOptions.DENY_ALL);

        if (connection.getCreatorPlayer().getUniqueId().equals(player.getUniqueId())) {
            Slot settingHead = menu.getSlot(31);

            ItemStack settingHeadItem = new ItemStack(Material.WRITABLE_BOOK);


            ItemMeta settingHeadItemMeta = settingHeadItem.getItemMeta();
            settingHeadItemMeta.setDisplayName("Settings");
            settingHeadItem.setItemMeta(settingHeadItemMeta);
            settingHead.setItem(settingHeadItem);
            settingHead.setClickHandler((player1, clickInformation) -> {
                PortalSettingsUI portalSettings = new PortalSettingsUI(player1, portal);
                portalSettings.openMenu();
            });
            settingHead.setClickOptions(ClickOptions.DENY_ALL);
        }



        menu.open(this.player);
        return;
    }

}