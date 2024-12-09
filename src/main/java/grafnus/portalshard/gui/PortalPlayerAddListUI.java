package grafnus.portalshard.gui;

import grafnus.portalshard.data.DAO.PlayerPermissionDAO;
import grafnus.portalshard.data.HibernateDO.HibernateConnection;
import grafnus.portalshard.data.HibernateDO.HibernatePlayerPermission;
import grafnus.portalshard.data.HibernateDO.HibernatePortal;
import grafnus.portalshard.util.skulls.SkullFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.paginate.PaginatedMenuBuilder;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PortalPlayerAddListUI {

    private HibernatePortal portal;
    private Player player;

    public PortalPlayerAddListUI(Player player, HibernatePortal portal) {
        this.player = player;
        this.portal = portal;
    }

    public void openMenu() {
        HibernateConnection connection = portal.getConnection();
        if (connection == null) {
            return;
        }

        Menu.Builder template = ChestMenu.builder(5).title("Portal Settings").redraw(true);
        Mask itemSlots = BinaryMask.builder(template.getDimensions())
                .pattern("000000000")
                .pattern("011111110")
                .pattern("011111110")
                .pattern("011111110")
                .pattern("000000000").build();
        PaginatedMenuBuilder pagesBuilder = PaginatedMenuBuilder.builder(template)
                .slots(itemSlots)
                .nextButton(new ItemStack(Material.ARROW))
                .nextButtonEmpty(new ItemStack(Material.BARRIER))
                .nextButtonSlot(26)
                .previousButton(new ItemStack(Material.ARROW))
                .previousButtonEmpty(new ItemStack(Material.BARRIER))
                .previousButtonSlot(18);

        List<HibernatePlayerPermission> playerPerms = PlayerPermissionDAO.getPlayerPermissionsByConnectionId(portal.getConnectionId());
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

        for (Player pl:onlinePlayers) {
            if (pl.getUniqueId().equals(player.getUniqueId())) {
                continue;
            }
            ItemStack head = SkullFactory.getPlayerHead(pl);
            ItemMeta headMeta = head.getItemMeta();

            headMeta.setDisplayName(ChatColor.GOLD + pl.getName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.BLUE + "Change Player Permissions");
            headMeta.setLore(lore);

            head.setItemMeta(headMeta);
            SlotSettings setting = SlotSettings.builder().clickHandler((player1, clickInformation) -> {
                PortalPlayerUI playerSettings = new PortalPlayerUI(player1, portal, Bukkit.getOfflinePlayer(pl.getUniqueId()));
                playerSettings.openMenu();
                Bukkit.getLogger().log(Level.INFO, "Clicked!");
            }).item(head).build();
            pagesBuilder.addItem(setting);
        }
        List<Menu> pages = pagesBuilder.build();

        pages.get(0).open(this.player);
    }
}
