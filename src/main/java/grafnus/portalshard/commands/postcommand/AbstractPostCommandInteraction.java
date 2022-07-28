package grafnus.portalshard.commands.postcommand;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * The implementing class will take all known information in the constructor und wait for the Respawn Anchor interaction to call the post execution method.
 *
 */

public abstract class AbstractPostCommandInteraction extends AbstractPostCommand {

    private Material material;

    public AbstractPostCommandInteraction(Player sender, Material material) {
        super(sender);
        this.material = material;
    }

    // DO NOT OVERRIDE THIS METHOD!!
    public boolean executeCommandInteraction(Block b) {
        if (!b.getType().equals(getMaterial())) {
            return false;
        }
        execute(b);
        return true;
    }

    // OVERRIDE THIS METHOD!!
    protected abstract void execute(Block anchor);

    public Material getMaterial() {
        return material;
    }
}
