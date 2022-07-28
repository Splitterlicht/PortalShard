package grafnus.portalshard.commands;

import grafnus.portalshard.commands.subcommands.FFACommand;
import grafnus.portalshard.commands.subcommands.PlayerCommand;
import grafnus.portalshard.items.ITEMS;
import grafnus.portalshard.items.ItemFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BaseCommand extends AbstractCommand implements CommandExecutor {
    public BaseCommand() {
        super("portal", "Base Command of PortalShard");
        addSubCommand(new PlayerCommand());
        addSubCommand(new FFACommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            for (AbstractCommand c : getSubCommands()) {

                String cm = args[0];

                List<String> list = new ArrayList<>(List.of(args));
                list.remove(0);

                if (c.getCmd().equalsIgnoreCase(cm)) {
                    return c.execute(sender, cm, list.toArray(new String[0]));
                }
            }
        }
        return super.execute(sender, getCmd(), args);
    }
}
