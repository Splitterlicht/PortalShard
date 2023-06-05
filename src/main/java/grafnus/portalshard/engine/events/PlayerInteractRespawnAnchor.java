package grafnus.portalshard.engine.events;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.database.tables.DBPortal;
import grafnus.portalshard.engine.Converter;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.engine.task.TaskFactory;
import grafnus.portalshard.engine.task.UpdatePortalCharges;
import grafnus.portalshard.gui.PortalMenu;
import grafnus.portalshard.gui.PortalOverviewUI;
import grafnus.portalshard.items.ITEMS;
import grafnus.portalshard.items.ItemFactory;
import grafnus.portalshard.items.ItemHandler;
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.logging.Level;

public class PlayerInteractRespawnAnchor implements IEvent {

    private EEvents type = EEvents.PLAYER_INTERACT_RESPAWN_ANCHOR;

    @Override
    public boolean isEvent(EEvents event) {
        return event.equals(type);
    }

    @Override
    public void listen(Event event) {
        PlayerInteractEvent e = Converter.convert(event, PlayerInteractEvent.class);

        ArrayList<ItemStack> upgrades = new ArrayList<>();
        upgrades.add(ItemFactory.buildItemFromTemplate(ITEMS.FIRST_UPGRADE));
        upgrades.add(ItemFactory.buildItemFromTemplate(ITEMS.SECOND_UPGRADE));
        upgrades.add(ItemFactory.buildItemFromTemplate(ITEMS.THIRD_UPGRADE));

        ItemStack itemInHand = new ItemStack(e.getPlayer().getInventory().getItemInMainHand());
        itemInHand.setAmount(1);

        ITEMS upgrade = ItemHandler.getUpgradeItem(itemInHand);

        if (e == null)
            return;
        if (!doChecks(e))
            return;
        if (PortalEngine.getInstance().getPostCommandHandler().checkForInteractionAndExecute(e.getPlayer(), e.getClickedBlock()))
            e.setCancelled(true);
        else if (itemInHand.getType().equals(Material.ENDER_PEARL))
            chargePortal(e);
        else if (upgrade != null)
            tryPortalUpgrade(e, itemInHand, upgrade);
        else
            openMenu(e);

        return;
    }

    private void tryPortalUpgrade(PlayerInteractEvent event, ItemStack itemInHand, ITEMS upgrade) {

        Bukkit.getLogger().log(Level.INFO, "Debug: ITEMS upgrade: " + upgrade.toString());

        Location loc = RelativePosition.getLocationBelowN(event.getClickedBlock().getLocation(), 2);

        ArrayList<PortalData> portals = DBPortal.getPortal(loc);
        if (portals.size() <= 0)
            return;

        ArrayList<ConnectionData> conns = DBConnection.getConnection(portals.get(0).getConnection_id());
        if (conns.size() <= 0)
            return;

        event.setCancelled(true);

        if (!PortalEngine.getInstance().getPlayerPermissionCheck().canUpgrade(portals.get(0).getConnection_id(), event.getPlayer())) {
            openMenu(event);
            return;
        }


        int requiredLevel = 0;

        Bukkit.getLogger().log(Level.INFO, upgrade.toString());

        if (upgrade.equals(ITEMS.THIRD_UPGRADE)) {
            requiredLevel = 3;
        } else if (upgrade.equals(ITEMS.SECOND_UPGRADE)) {
            requiredLevel = 2;
        } else {
            requiredLevel = 1;
        }

        if (conns.get(0).getLevel() != requiredLevel) {
            String actionbar = ChatColor.LIGHT_PURPLE +  "Upgrade not possible! Required Level: " + ChatColor.GOLD + requiredLevel + ChatColor.LIGHT_PURPLE + " Portal Level: " + ChatColor.GOLD + conns.get(0).getLevel();
            event.getPlayer().sendActionBar(Component.text(actionbar));
            return;
        }

        DBConnection.updateLevel(conns.get(0).getUuid(), (requiredLevel + 1));
        String actionbar = ChatColor.LIGHT_PURPLE +  "Upgraded! Portal Level: " + ChatColor.GOLD + (conns.get(0).getLevel() + 1);
        event.getPlayer().sendActionBar(Component.text(actionbar));

        event.getPlayer().getInventory().getItemInMainHand().setAmount((event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1));
    }

    private boolean doChecks(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return false;
        if (!event.getClickedBlock().getType().equals(Material.RESPAWN_ANCHOR))
            return false;
        return true;
    }

    private void chargePortal(PlayerInteractEvent event) {
        Location loc = RelativePosition.getLocationBelowN(event.getClickedBlock().getLocation(), 2);

        ArrayList<PortalData> portals = DBPortal.getPortal(loc);
        if (portals.size() <= 0)
            return;

        ArrayList<ConnectionData> conns = DBConnection.getConnection(portals.get(0).getConnection_id());
        if (conns.size() <= 0)
            return;

        event.setCancelled(true);

        if (!PortalEngine.getInstance().getPlayerPermissionCheck().canCharge(portals.get(0).getConnection_id(), event.getPlayer())) {
            Bukkit.getLogger().log(Level.INFO, "Open here2!");
            openMenu(event);
            return;
        }

        if (conns.get(0).getCharges() >= 20) {
            Bukkit.getLogger().log(Level.INFO, "Open here3!");
            openMenu(event);
            return;
        }

        int level = conns.get(0).getLevel();

        int newCharges = (int) (conns.get(0).getCharges() + (2.5 * Math.pow(2, level)));
        if (newCharges > 20) {
            newCharges = 20;
        }

        ItemStack ender = event.getPlayer().getInventory().getItemInMainHand();
        if (ender.getAmount() == 1) {
            ender = null;
        } else {
            ender.setAmount(ender.getAmount() - 1);
        }
        event.getPlayer().getInventory().setItemInMainHand(ender);

        String actionbar = ChatColor.LIGHT_PURPLE +  "Portal recharged: " + ChatColor.GOLD + newCharges + ChatColor.GRAY + "/" + ChatColor.LIGHT_PURPLE + "20";
        event.getPlayer().sendActionBar(Component.text(actionbar));

        int finalNewCharges = newCharges;
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                DBConnection.updateCharges(conns.get(0).getUuid(), finalNewCharges);
                ArrayList<PortalData> ps = DBPortal.getPortalByConnID(portals.get(0).getConnection_id());
                for (PortalData portal : ps) {
                    TaskFactory.createTask(new UpdatePortalCharges(portal.getLoc()));
                }
            }
        };
        task.runTaskAsynchronously(PortalShard.getInstance());
    }

    private void openMenu(PlayerInteractEvent event) {

        Location loc = RelativePosition.getLocationBelowN(event.getClickedBlock().getLocation(), 2);
        ArrayList<PortalData> portals = DBPortal.getPortal(loc);
        if (portals.size() <= 0)
            return;
        PortalOverviewUI ui = new PortalOverviewUI(event.getPlayer(), portals.get(0));
        ui.openMenu();
        event.setCancelled(true);
        /*Location loc = RelativePosition.getLocationBelowN(event.getClickedBlock().getLocation(), 2);

        ArrayList<PortalData> portals = DBPortal.getPortal(loc);
        if (portals.size() <= 0)
            return;

        event.setCancelled(true);
        PortalMenu menu = new PortalMenu(event.getPlayer(), portals.get(0));
        menu.open();
        //event.getPlayer().sendActionBar(Component.text(org.bukkit.ChatColor.LIGHT_PURPLE + "Here would come the Menu"));
        */
    }
}
