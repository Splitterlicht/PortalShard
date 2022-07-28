package grafnus.portalshard.commands.subcommands;

import grafnus.portalshard.commands.AbstractCommand;
import grafnus.portalshard.commands.postcommand.*;
import grafnus.portalshard.engine.PortalEngine;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FFACommand extends AbstractCommand {
    public FFACommand() {
        super("ffa", "Modify the FFA option of portals");
    }

    @Override
    public boolean execute(CommandSender sender, String cmd, String[] args) {

        if (args.length < 1) {
            sendToFewArguments(sender);
            return true;
        }
        boolean value = false;
        if (args[0].equalsIgnoreCase("true")) {
            value = true;
        } else if (args[0].equalsIgnoreCase("false")) {
            value = false;
        } else {
            sender.sendMessage("You need to set the value to <true/false>");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("You need to be a player");
            return true;
        }
        Player p = (Player) sender;

        UpdatePortalFFA ffa = new UpdatePortalFFA(p, value);
        PortalEngine.getInstance().getPostCommandHandler().addPostCommandAction(ffa, 200L, "Your interaction has been canceled! (Timeout)");

        return true;
    }

    public void sendToFewArguments(CommandSender sender) {
        sender.sendMessage("You need to give more arguments:");
        sender.sendMessage("/portal ffa <true/false>");
    }

}
