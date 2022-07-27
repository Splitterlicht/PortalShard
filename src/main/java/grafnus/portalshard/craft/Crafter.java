package grafnus.portalshard.craft;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.items.ITEMS;
import grafnus.portalshard.items.ItemFactory;
import me.lucko.helper.Events;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Crafter {

    public void addRecipes() {
        addKeyRecipe();
    }

    private void addKeyRecipe() {
        PortalShard plugin = PortalShard.getInstance();
        ItemStack key = new ItemStack(Material.AMETHYST_CLUSTER);
        key.addUnsafeEnchantment(Enchantment.MENDING, 1);
        ItemMeta im = key.getItemMeta();
        im.setDisplayName(ITEMS.KEY.getName());
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        key.setItemMeta(im);
        key.setAmount(2);

        NamespacedKey nsk = new NamespacedKey(plugin, "portal_key");

        ShapedRecipe sr = new ShapedRecipe(nsk, key);
        sr.shape("ABA", "BCB", "ABA");
        sr.setIngredient('A', new ItemStack(Material.GOLD_BLOCK));
        sr.setIngredient('B', new ItemStack(Material.AMETHYST_SHARD));
        sr.setIngredient('C', new ItemStack(Material.ENDER_EYE));

        Bukkit.addRecipe(sr);

        Events.subscribe(PlayerJoinEvent.class).handler(e -> {
            e.getPlayer().discoverRecipe(nsk);
        });

        Events.subscribe(CraftItemEvent.class).handler(e -> {
            HumanEntity he = e.getWhoClicked();

            if (!(he instanceof Player)) {
                return;
            }

            if (!e.getCurrentItem().equals(key)) {
                return;
            }

            Player p = (Player) e.getWhoClicked();

            if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                e.setCancelled(true);

                while (p.getInventory().firstEmpty() != -1) {

                    CraftingInventory ci = e.getInventory();
                    ItemStack[] crafting = ci.getMatrix();

                    for (int i = 0; i < crafting.length; i++) {
                        ItemStack craftItem = crafting[i];

                        if (craftItem == null) {
                            return;
                        }
                    }

                    for (int i = 0; i < crafting.length; i++) {
                        ItemStack craftItem = crafting[i];

                        if (craftItem.getAmount() == 1) {
                            ci.setItem(i + 1, new ItemStack(Material.AIR));
                        } else {
                            craftItem.setAmount(craftItem.getAmount() - 1);
                        }

                        crafting[i] = craftItem;
                    }

                    ItemStack item = ItemFactory.buildItem(ITEMS.KEY, p);
                    item.setAmount(2);
                    p.getInventory().setItem(p.getInventory().firstEmpty(), item);
                }
                return;
            }
            ItemStack item = ItemFactory.buildItem(ITEMS.KEY, p);
            item.setAmount(2);
            e.setCurrentItem(item);

        });
    }

}
