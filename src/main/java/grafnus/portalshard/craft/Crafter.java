package grafnus.portalshard.craft;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.items.ITEMS;
import grafnus.portalshard.items.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Crafter {

    public void addRecipes() {
        addKeyRecipe();
    }

    private void addKeyRecipe() {
        Bukkit.getLogger().log(Level.INFO, "[PortalShard] Adding KEY crafting recipe!");

        PortalShard plugin = PortalShard.getInstance();

        ItemStack key = new ItemStack(Material.AMETHYST_CLUSTER);
        key.addUnsafeEnchantment(Enchantment.MENDING, 1);
        ItemMeta im = key.getItemMeta();
        im.setDisplayName(ITEMS.KEY.getName());
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        key.setItemMeta(im);
        key.setAmount(2);

        CustomRecipeBuilder
                .create(plugin)
                .withResult(ItemFactory.buildItemFromTemplate(ITEMS.KEY))
                .withKey("portal_key")
                .withShape("ABA", "BCB", "ABA")
                .withIngredient('A', new ItemStack(Material.GOLD_BLOCK))
                .withIngredient('B', new ItemStack(Material.AMETHYST_SHARD))
                .withIngredient('C', new ItemStack(Material.ENDER_EYE))
                .withCallback(inventoryClickEvent -> {
                    ItemStack item = ItemFactory.buildKey(Bukkit.getPlayer(inventoryClickEvent.getWhoClicked().getUniqueId()));
                    item.setAmount(2);
                    inventoryClickEvent.getInventory().setResult(item);
                })
                .build();

        Bukkit.getLogger().log(Level.INFO, "[PortalShard] Adding FIRST UPGRADE crafting recipe!");

        ItemStack upgradeOneItem = ItemFactory.buildItemFromTemplate(ITEMS.FIRST_UPGRADE);
        CustomRecipeBuilder
                .create(plugin)
                .withResult(upgradeOneItem)
                .withKey("portal_first_upgrade")
                .withShape("ABA", "CDC", "EFE")
                .withIngredient('A', new ItemStack(Material.EXPERIENCE_BOTTLE))
                .withIngredient('B', new ItemStack(Material.CRYING_OBSIDIAN))
                .withIngredient('C', new ItemStack(Material.DIAMOND))
                .withIngredient('D', new ItemStack(Material.ENDER_PEARL, 16))
                .withIngredient('E', new ItemStack(Material.ENDER_CHEST))
                .withIngredient('F', new ItemStack(Material.SOUL_CAMPFIRE))
                .build();

        Bukkit.getLogger().log(Level.INFO, "[PortalShard] Adding SECOND UPGRADE crafting reobcipe!");

        ItemStack upgradeTwoItem = ItemFactory.buildItemFromTemplate(ITEMS.SECOND_UPGRADE);
        CustomRecipeBuilder
                .create(plugin)
                .withResult(upgradeTwoItem)
                .withKey("portal_second_upgrade")
                .withShape("ABA", "CDC", "AEA")
                .withIngredient('A', new ItemStack(Material.OBSIDIAN))
                .withIngredient('B', new ItemStack(Material.SCULK_SENSOR))
                .withIngredient('C', new ItemStack(Material.TOTEM_OF_UNDYING))
                .withIngredient('D', new ItemStack(Material.NETHERITE_INGOT))
                .withIngredient('E', new ItemStack(Material.END_CRYSTAL))
                .build();


        Bukkit.getLogger().log(Level.INFO, "[PortalShard] Adding THIRD UPGRADE crafting recipe!");

        ItemStack upgradeThreeItem = ItemFactory.buildItemFromTemplate(ITEMS.THIRD_UPGRADE);
        ItemStack filledShulkerBox = new ItemStack(Material.SHULKER_BOX);
        if(!(filledShulkerBox.getItemMeta() instanceof BlockStateMeta)){
            Bukkit.getLogger().log(Level.INFO, "Error filling Shulker Box!");
        }
        BlockStateMeta filledShulkerBoxMeta = (BlockStateMeta)filledShulkerBox.getItemMeta();
        if(!(filledShulkerBoxMeta.getBlockState() instanceof ShulkerBox)){
            Bukkit.getLogger().log(Level.INFO, "Error filling Shulker Box!");
        }
        ShulkerBox shulker = (ShulkerBox) filledShulkerBoxMeta.getBlockState();
        Inventory inv = shulker.getInventory();
        while(inv.firstEmpty() != -1) {
            inv.addItem(new ItemStack(Material.ENDER_PEARL, Material.ENDER_PEARL.getMaxStackSize()));
        }
        filledShulkerBoxMeta.setBlockState(shulker);
        filledShulkerBox.setItemMeta(filledShulkerBoxMeta);

        CustomRecipeBuilder
                .create(plugin)
                .withResult(upgradeThreeItem)
                .withKey("portal_third_upgrade")
                .withShape("ABA", "CDC", "EFE")
                .withIngredient('A', new ItemStack(Material.DRAGON_BREATH))
                .withIngredient('B', new ItemStack(Material.RESPAWN_ANCHOR))
                .withIngredient('C', new ItemStack(Material.SEA_LANTERN))
                .withIngredient('D', new ItemStack(Material.ENDER_EYE, 64))
                .withIngredient('E', new ItemStack(Material.DIAMOND_BLOCK))
                .withIngredient('F', new ItemStack(Material.BEACON))
                .build();

    }

    public ItemStack getItemStackOfUpgrade(ITEMS itemInfo) {
        ItemStack upgradeItem = new ItemStack(itemInfo.getMaterial());
        upgradeItem.addUnsafeEnchantment(Enchantment.MENDING, 1);
        ItemMeta upgradeItemMeta = upgradeItem.getItemMeta();
        upgradeItemMeta.setDisplayName(itemInfo.getName());
        if (itemInfo.isEnchanted()) {
            upgradeItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        upgradeItemMeta.setLore(List.of(itemInfo.getLore()));
        upgradeItem.setItemMeta(upgradeItemMeta);
        return upgradeItem;
    }

}
