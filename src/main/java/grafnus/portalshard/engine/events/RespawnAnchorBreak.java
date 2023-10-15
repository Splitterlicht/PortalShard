package grafnus.portalshard.engine.events;

import grafnus.portalshard.data.DAO.PortalDAO;
import grafnus.portalshard.data.HibernateDO.HibernateConnection;
import grafnus.portalshard.data.HibernateDO.HibernatePortal;
import grafnus.portalshard.engine.Converter;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.items.ItemFactory;
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class RespawnAnchorBreak implements IEvent {
    @Override
    public boolean isEvent(EEvents event) {
        if (event.equals(EEvents.RESPAWN_ANCHOR_BREAK)) {
            return true;
        }
        return false;
    }

    @Override
    public void listen(Event event) {
        BlockBreakEvent e = Converter.convert(event, BlockBreakEvent.class);
        if (e == null)
            return;
        if (!e.getBlock().getType().equals(Material.RESPAWN_ANCHOR))
            return;

        Location bl = e.getBlock().getLocation();
        Location sourceLocation = RelativePosition.getLocationBelowN(bl, 2);

        if (!sourceLocation.getBlock().getType().equals(Material.NETHER_PORTAL))
            return;

        //ArrayList<PortalData> pds = PortalTable.getPortalByLocation(source);
        HibernatePortal portal = PortalDAO.getPortalByLocation(sourceLocation);
        if (portal == null)
            return;

        HibernateConnection connection = portal.getConnection();
        if (connection == null) {
            return;
        }

        if (!PortalEngine.getInstance().getPlayerPermissionCheck().canDestroy(connection.getId(), e.getPlayer())) {
            e.setCancelled(true);
            String actionbar = ChatColor.LIGHT_PURPLE +  "You are not permitted to destroy that portal!";
            e.getPlayer().sendActionBar(Component.text(actionbar));
            return;
        }

        PortalDAO.removePortal(portal);

        ItemStack item = ItemFactory.buildKey(connection.getUUID());

        e.setDropItems(false);
        e.getBlock().getWorld().dropItemNaturally(bl, item);
        e.getBlock().getWorld().dropItemNaturally(bl, new ItemStack(Material.CRYING_OBSIDIAN));

        String actionbar = ChatColor.LIGHT_PURPLE +  "You destroyed a portal!";

        e.getPlayer().sendActionBar(Component.text(actionbar));
    }

    private BlockBreakEvent convert(Event event) {
        if (event instanceof BlockBreakEvent) {
            return (BlockBreakEvent) event;
        }
        return null;
    }
}
