package grafnus.portalshard.commands.subcommands;

import grafnus.portalshard.commands.AbstractCommand;
import grafnus.portalshard.commands.postcommand.*;
import grafnus.portalshard.engine.PortalEngine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
            sender.sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " The player " + ChatColor.GOLD + args[1] + ChatColor.LIGHT_PURPLE + " is not online.");
            return true;
        }
        boolean value = false;
        if (args[2].equalsIgnoreCase("true")) {
            value = true;
        } else if (args[2].equalsIgnoreCase("false")) {
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

        switch (args[0]) {
            case "use":
                UpdatePlayerPermissionUse use = new UpdatePlayerPermissionUse(p, player, value);
                PortalEngine.getInstance().getPostCommandHandler().addPostCommandAction(use, 200L, ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " Your interaction has been " + ChatColor.RED + "canceled" + ChatColor.LIGHT_PURPLE + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Timeout" + ChatColor.DARK_GRAY + "]");

                break;
            case "charge":
                UpdatePlayerPermissionCharge charge = new UpdatePlayerPermissionCharge(p, player, value);
                PortalEngine.getInstance().getPostCommandHandler().addPostCommandAction(charge, 200L, ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " Your interaction has been " + ChatColor.RED + "canceled" + ChatColor.LIGHT_PURPLE + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Timeout" + ChatColor.DARK_GRAY + "]");
                break;
            case "upgrade":
                UpdatePlayerPermissionUpgrade upgrade = new UpdatePlayerPermissionUpgrade(p, player, value);
                PortalEngine.getInstance().getPostCommandHandler().addPostCommandAction(upgrade, 200L, ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " Your interaction has been " + ChatColor.RED + "canceled" + ChatColor.LIGHT_PURPLE + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Timeout" + ChatColor.DARK_GRAY + "]");
                break;
            case "destroy":
                UpdatePlayerPermissionDestroy destroy = new UpdatePlayerPermissionDestroy(p, player, value);
                PortalEngine.getInstance().getPostCommandHandler().addPostCommandAction(destroy, 200L, ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " Your interaction has been " + ChatColor.RED + "canceled" + ChatColor.LIGHT_PURPLE + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Timeout" + ChatColor.DARK_GRAY + "]");
                break;
            case "remove":
                RemovePlayerPermissions remove = new RemovePlayerPermissions(p, player);
                PortalEngine.getInstance().getPostCommandHandler().addPostCommandAction(remove, 200L, ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " Your interaction has been " + ChatColor.RED + "canceled" + ChatColor.LIGHT_PURPLE + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Timeout" + ChatColor.DARK_GRAY + "]");
                break;
            default:
                sendToFewArguments(sender);
                break;
        }

        p.sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You have " + ChatColor.RED + "10" + ChatColor.LIGHT_PURPLE + " seconds to right click the respawn anchor of a portal!");

        return true;
    }

    public void sendToFewArguments(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "[Portal]" + ChatColor.LIGHT_PURPLE + " You need to give more arguments:");
        sender.sendMessage("/portal player <use/charge/upgrade/destroy/remove> [player name] <true/false>");
    }

}
