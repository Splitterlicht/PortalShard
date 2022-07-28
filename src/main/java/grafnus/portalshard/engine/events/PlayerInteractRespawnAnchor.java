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
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PlayerInteractRespawnAnchor implements IEvent {

    private EEvents type = EEvents.PLAYER_INTERACT_RESPAWN_ANCHOR;

    @Override
    public boolean isEvent(EEvents event) {
        return event.equals(type);
    }

    @Override
    public void listen(Event event) {
        PlayerInteractEvent e = Converter.convert(event, PlayerInteractEvent.class);
        if (e == null)
            return;
        if (!doChecks(e))
            return;
        if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENDER_PEARL))
            chargePortal(e);
        if (PortalEngine.getInstance().getPostCommandHandler().checkForInteractionAndExecute(e.getPlayer(), e.getClickedBlock()))
            e.setCancelled(true);
        else
            openMenu(e);
        return;
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
            return;
        }

        if (conns.get(0).getCharges() >= 20) {
            openMenu(event);
            return;
        }

        int newCharges = conns.get(0).getCharges() + 5;
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

        event.setCancelled(true);
        PortalMenu menu = new PortalMenu(event.getPlayer(), portals.get(0));
        menu.open();
        //event.getPlayer().sendActionBar(Component.text(org.bukkit.ChatColor.LIGHT_PURPLE + "Here would come the Menu"));
    }
}
