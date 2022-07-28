package grafnus.portalshard.commands.subcommands;

import grafnus.portalshard.commands.AbstractCommand;
import grafnus.portalshard.commands.postcommand.UpdatePlayerPermissionUse;
import grafnus.portalshard.engine.PortalEngine;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand extends AbstractCommand {
    public PlayerCommand() {
        super("player", "Modifying players permissions");
    }

    @Override
    public boolean execute(CommandSender sender, String cmd, String[] args) {

        if (args.length < 3) {
            sendToFewArguments(sender);
            return true;
        }

        OfflinePlayer player = Bukkit.getPlayer(args[1]);



        if (player == null) {
            sender.sendMessage("The player " + args[1] + " is not online.");
            return true;
        }
        boolean value = false;
        if (args[2].equalsIgnoreCase("true")) {
            value = true;
        } else if (args[2].equalsIgnoreCase("false")) {
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

        switch (args[0]) {
            case "use":
                UpdatePlayerPermissionUse use = new UpdatePlayerPermissionUse(p, player, value);
                PortalEngine.getInstance().getPostCommandHandler().addPostCommandAction(use, 200L, "Your interaction has been canceled! (Timeout)");

                break;
            case "charge":
                //Update charge
                break;
            case "upgrade":
                //Update upgrade
                break;
            case "destroy":
                //Update destroy
                break;
            case "remove":
                //Update destroy
                break;
            default:
                sendToFewArguments(sender);
                break;
        }


        return true;
    }

    public void sendToFewArguments(CommandSender sender) {
        sender.sendMessage("You need to give more arguments:");
        sender.sendMessage("/portal player <use/charge/upgrade/destroy/remove> [player name] <true/false>");
    }

}
