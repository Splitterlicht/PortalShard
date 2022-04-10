package grafnus.portalshard.commands;

import grafnus.portalshard.items.ITEMS;
import grafnus.portalshard.items.ItemFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BaseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;

        p.sendMessage("Portal Key Dropped");
        p.getLocation().getWorld().dropItem(p.getLocation(), ItemFactory.buildItem(ITEMS.KEY));
        return true;
    }
}
