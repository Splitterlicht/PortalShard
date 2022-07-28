package grafnus.portalshard.commands.postcommand;

import grafnus.portalshard.PortalShard;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.sound.sampled.Port;
import java.util.ArrayList;

public class PostCommandHandler {

    ArrayList<AbstractPostCommand> waitingActions = new ArrayList<>();

    public AbstractPostCommandInteraction checkForInteractionAndGet(Player player) {
        for (AbstractPostCommand a : waitingActions) {
            if (player.getUniqueId().toString().equals(a.getSender().getUniqueId().toString())) {
                if (a instanceof AbstractPostCommandInteraction) {
                    return (AbstractPostCommandInteraction) a;
                }
            }
        }
        return null;
    }

    public boolean executeInteraction(AbstractPostCommandInteraction action, Block block) {
        return action.executeCommandInteraction(block);
    }

    public boolean checkForInteractionAndExecute(Player player, Block block) {
        AbstractPostCommandInteraction action = checkForInteractionAndGet(player);
        if (action == null) {
            return false;
        }


        return executeInteraction(action, block);

    }

    public void addPostCommandAction(AbstractPostCommand action, long delay, String removeMsg) {
        waitingActions.add(action);

        BukkitRunnable removeAction = new BukkitRunnable() {
            @Override
            public void run() {
                if (waitingActions.contains(action)) {
                    action.getSender().sendMessage(removeMsg);
                    waitingActions.remove(action);
                }
            }
        };
        removeAction.runTaskLater(PortalShard.getInstance(), delay);
    }

}
