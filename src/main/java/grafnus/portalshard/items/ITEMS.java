package grafnus.portalshard.items;

import org.bukkit.Material;

public enum ITEMS {
    KEY(Material.AMETHYST_CLUSTER, "Portal Key", new String[]{"This Key connects two portals", "with each other by placing them", "inside.", "=====[ID]====="}, true),
    FIRST_UPGRADE(Material.AMETHYST_SHARD, "Upgrade", new String[]{"Bla", "Bla"}, true),
    SECOND_UPGRADE(Material.AMETHYST_SHARD, "Upgrade", new String[]{"Bla", "Bla"}, true),
    THIRD_UPGRADE(Material.AMETHYST_SHARD, "Upgrade", new String[]{"Bla", "Bla"}, true);


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
