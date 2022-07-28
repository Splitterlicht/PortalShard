package grafnus.portalshard.commands.postcommand;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class AbstractPostCommand {

    private Player sender;

    public AbstractPostCommand(Player sender) {
        this.sender = sender;
    }

    public Player getSender() {
        return sender;
    }
}
