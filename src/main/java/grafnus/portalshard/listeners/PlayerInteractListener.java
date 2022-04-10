package grafnus.portalshard.listeners;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.database.data.ConnectionData;
import grafnus.portalshard.database.data.PortalData;
import grafnus.portalshard.database.tables.ConnectionTable;
import grafnus.portalshard.database.tables.PortalTable;
import grafnus.portalshard.engine.task.TaskFactory;
import grafnus.portalshard.engine.task.UpdatePortalCharges;
import grafnus.portalshard.util.placement.RelativePosition;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block b = event.getClickedBlock();
        if (b == null) {
            return;
        }
        if (b.getType().equals(Material.RESPAWN_ANCHOR) && RelativePosition.getLocationBelow(b.getLocation()).getBlock().getType().equals(Material.NETHER_PORTAL)) {



            ArrayList<PortalData> data = PortalTable.getPortalByLocation(RelativePosition.getLocationBelowN(b.getLocation(), 2));
            if (data.size() >= 1) {
                event.setCancelled(true);

                if (b.getType().equals(Material.RESPAWN_ANCHOR) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENDER_PEARL)) {
                    if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        if (!event.getClickedBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand()).isEmpty()) {
                            event.setCancelled(false);
                        } else {
                            event.setCancelled(true);
                            return;
                        }
                    }
                    ArrayList<ConnectionData> conns = ConnectionTable.getConnection(data.get(0).getUuid());
                    if (conns.size() >= 1) {
                        ConnectionData conn = conns.get(0);
                        if (conn.getCharges() < 20) {
                            int newCharges = conn.getCharges() + 5;
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
                                    ConnectionTable.updateCharges(data.get(0).getUuid(), finalNewCharges);
                                    ArrayList<PortalData> portals = PortalTable.getPortalByUuid(data.get(0).getUuid());
                                    for (PortalData portal : portals) {
                                        TaskFactory.createTask(new UpdatePortalCharges(portal.getLoc()));
                                    }
                                }
                            };
                            task.runTaskAsynchronously(PortalShard.getInstance());
                        }
                    }
                }
            }
        }
    }
}
