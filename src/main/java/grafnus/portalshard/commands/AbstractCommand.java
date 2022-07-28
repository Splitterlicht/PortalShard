package grafnus.portalshard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class AbstractCommand {

    private String cmd;
    private String description;

    private ArrayList<AbstractCommand> subCommands = new ArrayList<>();

    public AbstractCommand(String cmd, String description) {
        this.cmd = cmd;
        this.description = description;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<AbstractCommand> getSubCommands() {
        return subCommands;
    }

    public void addSubCommand(AbstractCommand subCommand) {
        subCommands.add(subCommand);
    }

    public void removeSubCommand(AbstractCommand subCommand) {
        subCommands.remove(subCommand);
    }

    public boolean execute(CommandSender sender, String cmd, String[] args) {

        sender.sendMessage("Command help:");
        for (int i = 0; i < subCommands.size(); i++) {
            AbstractCommand sc = subCommands.get(i);
            sender.sendMessage("[" + (i + 1) + "] /" + sc.getCmd() + ": " + sc.getDescription());
        }


        return true;
    }
}
