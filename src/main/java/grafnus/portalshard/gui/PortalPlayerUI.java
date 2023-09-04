package grafnus.portalshard.gui;

import dev.dbassett.skullcreator.SkullCreator;
import grafnus.portalshard.PERMISSION;
import grafnus.portalshard.data.DAO.PlayerPermissionDAO;
import grafnus.portalshard.data.DO.Connection;
import grafnus.portalshard.data.DO.PlayerPermission;
import grafnus.portalshard.data.DO.Portal;
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
    private Portal portal;
    private Player player;

    public PortalPlayerUI(Player player, Portal portal, OfflinePlayer target) {
        this.player = player;
        this.portal = portal;
        this.target = target;
    }

    public void openMenu() {
        Connection connection = portal.getConnection();

        PlayerPermission playerPermission = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(connection.getId(), target);

        if (playerPermission == null) {
            playerPermission = new PlayerPermission(connection.getId(), target.getUniqueId());
            PlayerPermissionDAO.savePlayerPermission(playerPermission);
        }
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
        ItemStack useToggle = new ItemStack(getBlockTypeToggle(playerPermission.isUse()));
        ItemMeta useMeta = useToggle.getItemMeta();

        useMeta.setDisplayName(ChatColor.BLUE + "Toggle Use Permission");
        ArrayList<String> useLore = new ArrayList<>();
        useLore.add(ChatColor.BLUE + "Click to Use permission");
        useMeta.setLore(useLore);

        useToggle.setItemMeta(useMeta);
        useSlot.setItem(useToggle);

        useSlot.setClickHandler((player1, clickInformation) -> {
            if (connection.getCreatorPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_USE.isAllowed(player)) {
                PlayerPermission playerPerm = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(connection.getId(), target);
                playerPerm.setUse(!playerPerm.isUse());
                PlayerPermissionDAO.savePlayerPermission(playerPerm);
                this.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        // Charge Toggle ---------------------------------------------
        Slot chargeSlot = menu.getSlot(21);
        ItemStack chargeToggle = new ItemStack(getBlockTypeToggle(playerPermission.isCharge()));
        ItemMeta chargeMeta = chargeToggle.getItemMeta();

        chargeMeta.setDisplayName(ChatColor.BLUE + "Toggle Charge Permission");
        ArrayList<String> chargeLore = new ArrayList<>();
        chargeLore.add(ChatColor.BLUE + "Click to Charge permission");
        chargeMeta.setLore(chargeLore);

        chargeToggle.setItemMeta(chargeMeta);
        chargeSlot.setItem(chargeToggle);

        chargeSlot.setClickHandler((player1, clickInformation) -> {
            if (connection.getCreatorPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_CHARGE.isAllowed(player)) {
                PlayerPermission playerPerm = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(connection.getId(), target);
                playerPerm.setCharge(!playerPerm.isCharge());
                PlayerPermissionDAO.savePlayerPermission(playerPerm);
                this.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        // Upgrade Toggle ---------------------------------------------
        Slot upgradeSlot = menu.getSlot(23);
        ItemStack upgradeToggle = new ItemStack(getBlockTypeToggle(playerPermission.isUpgrade()));
        ItemMeta upgradeMeta = upgradeToggle.getItemMeta();

        upgradeMeta.setDisplayName(ChatColor.BLUE + "Toggle Upgrade Permission");
        ArrayList<String> upgradeLore = new ArrayList<>();
        upgradeLore.add(ChatColor.BLUE + "Click to Upgrade permission");
        upgradeMeta.setLore(upgradeLore);

        upgradeToggle.setItemMeta(upgradeMeta);
        upgradeSlot.setItem(upgradeToggle);

        upgradeSlot.setClickHandler((player1, clickInformation) -> {
            if (connection.getCreatorPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_UPGRADE.isAllowed(player)) {
                PlayerPermission playerPerm = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(connection.getId(), target);
                playerPerm.setUpgrade(!playerPerm.isUpgrade());
                PlayerPermissionDAO.savePlayerPermission(playerPerm);
                this.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        // Destroy Toggle ---------------------------------------------
        Slot destroySlot = menu.getSlot(25);
        ItemStack destroyToggle = new ItemStack(getBlockTypeToggle(playerPermission.isDestroy()));
        ItemMeta destroyMeta = destroyToggle.getItemMeta();

        destroyMeta.setDisplayName(ChatColor.BLUE + "Toggle Destroy Permission");
        ArrayList<String> destroyLore = new ArrayList<>();
        destroyLore.add(ChatColor.BLUE + "Click to Destroy permission");
        destroyMeta.setLore(destroyLore);

        destroyToggle.setItemMeta(destroyMeta);
        destroySlot.setItem(destroyToggle);

        destroySlot.setClickHandler((player1, clickInformation) -> {
            if (connection.getCreatorPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_DESTROY.isAllowed(connection.getCreatorPlayer().getPlayer())) {
                PlayerPermission playerPerm = PlayerPermissionDAO.getPlayerPermissionByConnectionIdAndPlayer(connection.getId(), target);
                playerPerm.setDestroy(!playerPerm.isDestroy());
                PlayerPermissionDAO.savePlayerPermission(playerPerm);
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
