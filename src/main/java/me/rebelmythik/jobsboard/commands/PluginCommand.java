package me.rebelmythik.jobsboard.commands;

import me.rebelmythik.jobsboard.commands.subcommands.BrowseSubcommand;
import me.rebelmythik.jobsboard.commands.subcommands.CreateSubcommand;
import me.rebelmythik.jobsboard.commands.subcommands.PluginSubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class PluginCommand implements CommandExecutor, TabCompleter {

    private final CommandInfo commandInfo;
    private final ArrayList<PluginSubCommand> subCommands = new ArrayList<>();

    public PluginCommand() {
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(commandInfo, "Commands must have CommandInfo annotations");
        subCommands.add(new BrowseSubcommand());
        subCommands.add(new CreateSubcommand());
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    public ArrayList<PluginSubCommand> getSubCommands(){
        return subCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!commandInfo.permission().isEmpty()){
            if (!sender.hasPermission(commandInfo.permission())) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
        }
        if (commandInfo.requiresPlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
                return true;
            }
            execute((Player) sender, args);
        }
        execute(sender, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return null;
    }

    public void execute(Player player, String[] args) {}
    public void execute(CommandSender sender, String[] args) {};
    public List<String> onTabComplete() { return null; }
}
