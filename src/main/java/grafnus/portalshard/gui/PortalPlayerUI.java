package grafnus.portalshard.gui;

import dev.dbassett.skullcreator.SkullCreator;
import grafnus.portalshard.PERMISSION;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PlayerPermsData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.database.tables.DBPlayerPerms;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;

public class PortalPlayerUI {

    private  OfflinePlayer target;
    private PortalData portalData;
    private Player player;

    public PortalPlayerUI(Player player, PortalData portalData, OfflinePlayer target) {
        this.player = player;
        this.portalData = portalData;
        this.target = target;
    }

    public void openMenu() {
        ArrayList<ConnectionData> cd = DBConnection.getConnection(portalData.getConnection_id());
        if (cd.size() <= 0) {
            return;
        }
        ConnectionData c = cd.get(0);
        DBPlayerPerms.addIfNotPresent(this.portalData.getConnection_id(), this.target);
        PlayerPermsData perms = DBPlayerPerms.getPlayerPerm(this.portalData.getConnection_id(), this.target);
        Menu menu = ChestMenu.builder(5).title("Portal Settings").redraw(true).build();

        // Head ---------------------------------------------
        Slot headSlot = menu.getSlot(4);
        ItemStack head = SkullCreator.itemFromUuid(target.getUniqueId());
        ItemMeta headMeta = head.getItemMeta();

        headMeta.setDisplayName(ChatColor.GOLD + "Settings for " + ChatColor.LIGHT_PURPLE + this.player.getName());

        head.setItemMeta(headMeta);
        headSlot.setItem(head);

        // Use Toggle ---------------------------------------------
        Slot useSlot = menu.getSlot(19);
        ItemStack useToggle = new ItemStack(getBlockTypeToggle(perms.isUse()));
        ItemMeta useMeta = useToggle.getItemMeta();

        useMeta.setDisplayName(ChatColor.BLUE + "Toggle Use Permission");
        ArrayList<String> useLore = new ArrayList<>();
        useLore.add(ChatColor.BLUE + "Click to Use permission");
        useMeta.setLore(useLore);

        useToggle.setItemMeta(useMeta);
        useSlot.setItem(useToggle);

        useSlot.setClickHandler((player1, clickInformation) -> {
            // TODO: Or if player is moderator!
            if (c.getPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_USE.isAllowed(player)) {
                DBPlayerPerms.setUse(portalData.getConnection_id(), target, !perms.isUse());
                this.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        // Charge Toggle ---------------------------------------------
        Slot chargeSlot = menu.getSlot(21);
        ItemStack chargeToggle = new ItemStack(getBlockTypeToggle(perms.isCharge()));
        ItemMeta chargeMeta = chargeToggle.getItemMeta();

        chargeMeta.setDisplayName(ChatColor.BLUE + "Toggle Charge Permission");
        ArrayList<String> chargeLore = new ArrayList<>();
        chargeLore.add(ChatColor.BLUE + "Click to Charge permission");
        chargeMeta.setLore(chargeLore);

        chargeToggle.setItemMeta(chargeMeta);
        chargeSlot.setItem(chargeToggle);

        chargeSlot.setClickHandler((player1, clickInformation) -> {
            // TODO: Or if player is moderator!
            if (c.getPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_CHARGE.isAllowed(player)) {
                DBPlayerPerms.setCharge(portalData.getConnection_id(), target, !perms.isCharge());
                this.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        // Upgrade Toggle ---------------------------------------------
        Slot upgradeSlot = menu.getSlot(23);
        ItemStack upgradeToggle = new ItemStack(getBlockTypeToggle(perms.isUpgrade()));
        ItemMeta upgradeMeta = upgradeToggle.getItemMeta();

        upgradeMeta.setDisplayName(ChatColor.BLUE + "Toggle Upgrade Permission");
        ArrayList<String> upgradeLore = new ArrayList<>();
        upgradeLore.add(ChatColor.BLUE + "Click to Upgrade permission");
        upgradeMeta.setLore(upgradeLore);

        upgradeToggle.setItemMeta(upgradeMeta);
        upgradeSlot.setItem(upgradeToggle);

        upgradeSlot.setClickHandler((player1, clickInformation) -> {
            // TODO: Or if player is moderator!
            if (c.getPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_UPGRADE.isAllowed(player)) {
                DBPlayerPerms.setUpgrade(portalData.getConnection_id(), target, !perms.isUpgrade());
                this.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        // Destroy Toggle ---------------------------------------------
        Slot destroySlot = menu.getSlot(25);
        ItemStack destroyToggle = new ItemStack(getBlockTypeToggle(perms.isDestroy()));
        ItemMeta destroyMeta = destroyToggle.getItemMeta();

        destroyMeta.setDisplayName(ChatColor.BLUE + "Toggle Destroy Permission");
        ArrayList<String> destroyLore = new ArrayList<>();
        destroyLore.add(ChatColor.BLUE + "Click to Destroy permission");
        destroyMeta.setLore(destroyLore);

        destroyToggle.setItemMeta(destroyMeta);
        destroySlot.setItem(destroyToggle);

        destroySlot.setClickHandler((player1, clickInformation) -> {
            // TODO: Or if player is moderator!
            if (c.getPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_DESTROY.isAllowed(c.getPlayer().getPlayer())) {
                DBPlayerPerms.setDestroy(portalData.getConnection_id(), target, !perms.isDestroy());
                this.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        menu.open(this.player);

    }

    private Material getBlockTypeToggle(boolean value) {
        if (value) {
            return Material.GREEN_WOOL;
        }
        return Material.RED_WOOL;
    }
}
