package grafnus.portalshard.commands.subcommands;

import grafnus.portalshard.commands.AbstractCommand;
import grafnus.portalshard.commands.postcommand.*;
import grafnus.portalshard.engine.PortalEngine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            sender.sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You need to set the value to <true/false>");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You need to be a player");
            return true;
        }
        Player p = (Player) sender;

        UpdatePortalFFA ffa = new UpdatePortalFFA(p, value);
        PortalEngine.getInstance().getPostCommandHandler().addPostCommandAction(ffa, 200L, ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + "Your interaction has been " + ChatColor.RED + "canceled" + ChatColor.LIGHT_PURPLE + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Timeout" + ChatColor.DARK_GRAY + "]");
        p.sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You have " + ChatColor.RED + "10" + ChatColor.LIGHT_PURPLE + " seconds to right click the respawn anchor of a portal!");

        return true;
    }

    public void sendToFewArguments(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You need to give more arguments:");
        sender.sendMessage("/portal ffa <true/false>");
    }

}
