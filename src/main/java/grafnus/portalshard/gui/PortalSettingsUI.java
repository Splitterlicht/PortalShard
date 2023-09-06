package grafnus.portalshard.gui;

import grafnus.portalshard.PERMISSION;
import grafnus.portalshard.data.DAO.ConnectionDAO;
import grafnus.portalshard.data.DO.Connection;
import grafnus.portalshard.data.DO.Portal;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;

public class PortalSettingsUI {

    private Portal portal;
    private Player player;
    //TODO: Use Canvas to create UIs

    public PortalSettingsUI(Player player, Portal portal) {
        this.player = player;
        this.portal = portal;
    }

    public void openMenu() {
        Menu menu = ChestMenu.builder(5).title("Portal Settings").redraw(true).build();

        Connection connection = portal.getConnection();
        if (connection == null) {
            return;
        }

        //Change Setting FFA
        Slot ffaSetting = menu.getSlot(13);

        Material status = Material.RED_WOOL;
        String strStatus = "";
        if (connection.isFfa()) {
            status = Material.LIME_WOOL;
            strStatus = ChatColor.GREEN + "ENABLED";
        } else {
            status = Material.RED_WOOL;
            strStatus = ChatColor.RED + "DISABLED";
        }

        ItemStack ffaSettingItem = new ItemStack(status);
        ItemMeta ffaSettingItemMeta = ffaSettingItem.getItemMeta();
        ffaSettingItemMeta.setDisplayName(ChatColor.BLUE + "FFE: " + strStatus);
        ArrayList<String> ffaSettingItemLore = new ArrayList<>();
        ffaSettingItemLore.add(ChatColor.WHITE + "FFA: Free For ALL");
        ffaSettingItemMeta.setLore(ffaSettingItemLore);
        ffaSettingItem.setItemMeta(ffaSettingItemMeta);
        ffaSetting.setItem(ffaSettingItem);
        ffaSetting.setClickHandler((player1, clickInformation) -> {
            // TODO: Or if player is moderator!
            if (connection.getCreatorPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_CHANGE_PERMISSION_FFA.isAllowed(player)) {
                connection.setFfa(!connection.isFfa());
                ConnectionDAO.saveConnection(connection);
                this.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        //Add player to portal
        Slot addPlayer = menu.getSlot(29);
        ItemStack addPlayerItem = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta addPlayerItemMeta = addPlayerItem.getItemMeta();
        addPlayerItemMeta.setDisplayName(ChatColor.BLUE + "Add player");
        addPlayerItem.setItemMeta(addPlayerItemMeta);
        addPlayer.setItem(addPlayerItem);
        addPlayer.setClickHandler((player1, clickInformation) -> {
            // TODO: Or if player is moderator!
            if (connection.getCreatorPlayer().getPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_ADD_PLAYER_TO_PORTAL.isAllowed(player)) {
                PortalPlayerAddListUI playerListUI = new PortalPlayerAddListUI(player1, portal);
                playerListUI.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        //View player List
        Slot playerList = menu.getSlot(33);
        ItemStack playerListItem = new ItemStack(Material.BOOK);
        ItemMeta playerListItemMeta = playerListItem.getItemMeta();
        playerListItemMeta.setDisplayName(ChatColor.BLUE + "View player list");
        playerListItem.setItemMeta(playerListItemMeta);
        playerList.setItem(playerListItem);
        playerList.setClickHandler((player1, clickInformation) -> {
            // TODO: Or if player is moderator!
            if (connection.getCreatorPlayer().getUniqueId().equals(player1.getUniqueId()) || PERMISSION.MODERATOR_MODIFY_PLAYER_LIST.isAllowed(player)) {
                PortalPlayerListUI playerListUI = new PortalPlayerListUI(player1, portal);
                playerListUI.openMenu();
            } else {
                player1.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot do that!");
            }
        });

        menu.open(this.player);
    }
}
