package me.rebelmythik.jobsboard.commands;

import me.rebelmythik.jobsboard.commands.subcommands.PluginSubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@CommandInfo(name="jb", requiresPlayer = true)
public class MainCommand extends PluginCommand {

    @Override
    public void execute(Player player, String[] args){
        ArrayList<PluginSubCommand> subCommands = getSubCommands();

        if (args.length > 0) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    getSubCommands().get(i).perform(player, args);
                }
            }
        }
    }



}
