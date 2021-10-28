package me.rebelmythik.jobsboard.commands.subcommands;

import me.rebelmythik.jobsboard.commands.subcommands.SubcommandInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public abstract class PluginSubCommand {

    final SubcommandInfo subInfo;

    public PluginSubCommand() {
        subInfo = getClass().getDeclaredAnnotation(SubcommandInfo.class);
        Objects.requireNonNull(subInfo, "You need a SubcommandInfo annotation for this class!");
    }

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player player, String[] args);

    public boolean checkPermission(CommandSender sender, Command command, String label, String[] args) {
        if (!subInfo.permission().isEmpty()){
            if (!sender.hasPermission(subInfo.permission())) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
        }
        if (subInfo.requiresPlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
                return true;
            }
            perform((Player) sender, args);
        }
        return true;
    }
}
