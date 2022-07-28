package grafnus.portalshard.engine.events;

import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.DBConnection;
import grafnus.portalshard.database.tables.DBPortal;
import grafnus.portalshard.engine.Converter;
import grafnus.portalshard.engine.PortalEngine;
import grafnus.portalshard.items.ITEMS;
import grafnus.portalshard.items.ItemFactory;
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

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
        Location source = RelativePosition.getLocationBelowN(bl, 2);

        if (!source.getBlock().getType().equals(Material.NETHER_PORTAL))
            return;

        //ArrayList<PortalData> pds = PortalTable.getPortalByLocation(source);
        ArrayList<PortalData> pds = DBPortal.getPortal(source);
        if (pds.size() != 1)
            return;

        PortalData pd = pds.get(0);

        if (!PortalEngine.getInstance().getPlayerPermissionCheck().canDestroy(pd.getConnection_id(), e.getPlayer())) {
            e.setCancelled(true);
            String actionbar = ChatColor.LIGHT_PURPLE +  "You are not permitted to destroy that portal!";
            e.getPlayer().sendActionBar(Component.text(actionbar));
            return;
        }

        ArrayList<ConnectionData> conns = DBConnection.getConnection(pd.getConnection_id());

        if (conns.size() <= 0) {
            return;
        }

        DBPortal.removePortal(source);
        //PortalTable.removePortal(pd.getUuid(), source);

        ItemStack item = ItemFactory.buildItem(ITEMS.KEY, DBConnection.getConnection(pd.getConnection_id()).get(0).getUuid(), e.getPlayer());

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
