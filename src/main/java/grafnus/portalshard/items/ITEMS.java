package grafnus.portalshard.items;

import org.bukkit.Material;

public enum ITEMS {
    KEY(Material.AMETHYST_CLUSTER, "Portal Key", new String[]{"This Key connects two portals", "with each other by placing them", "inside.", "=====[ID]====="}, true),
    FIRST_UPGRADE(Material.SMALL_AMETHYST_BUD, "Portal Upgrade 1", new String[]{"This upgrades one portal to Level 2", "That Portal only need two Enderperls", "to fully recharge!"}, true),
    SECOND_UPGRADE(Material.MEDIUM_AMETHYST_BUD, "Portal Upgrade 2", new String[]{"This upgrades one portal to Level 3", "That Portal only need one Enderperls", "to fully recharge!"}, true),
    THIRD_UPGRADE(Material.LARGE_AMETHYST_BUD, "Portal Upgrade 3", new String[]{"This upgrades one portal to Level 4", "That Portal does not need to be recharged!"}, true);


    private Material material;
    private String name;
    private String[] lore;
    private boolean enchanted;

    ITEMS(Material material,String name, String[] lore, boolean enchanted) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.enchanted = enchanted;
    }

    public String getName() {
        return name;
    }

    public String[] getLore() {
        return lore;
    }

    public boolean isEnchanted() {
        return enchanted;
    }

    public Material getMaterial() {
        return material;
    }
}
