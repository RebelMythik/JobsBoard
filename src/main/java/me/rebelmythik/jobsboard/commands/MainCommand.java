package me.rebelmythik.jobsboard.commands;

import me.rebelmythik.jobsboard.commands.subcommands.PluginSubCommand;
import me.rebelmythik.jobsboard.utils.ItemHelper;
import me.rebelmythik.jobsboard.utils.RangeHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name="jb", requiresPlayer = true)
public class MainCommand extends PluginCommand {

    private final List<String> possibleSubCommands = new ArrayList<>();

    @Override
    public void execute(Player player, String[] args){
        ArrayList<PluginSubCommand> subCommands = getSubCommands();

        for (PluginSubCommand sub : subCommands) possibleSubCommands.add(sub.getName());

        if (args.length > 0) {
            for (int i = 0; i < subCommands.size(); i++) {
                if (args[0].equalsIgnoreCase(subCommands.get(i).getName())) {
                    subCommands.get(i).perform(player, args);
                }
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            return possibleSubCommands;
        }
        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("create")) return ItemHelper.getItemList();
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) return RangeHelper.getItemAmountRange();
        }
        else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("create")) return RangeHelper.getRewardAmountRange();
        }

        return  null;
    }
}
