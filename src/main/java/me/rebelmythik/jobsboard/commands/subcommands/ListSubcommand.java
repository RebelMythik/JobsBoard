package me.rebelmythik.jobsboard.commands.subcommands;

import org.bukkit.entity.Player;

@SubcommandInfo(name = "list", permission = "jobsboard.command.list", requiresPlayer = true)
public class ListSubcommand extends PluginSubCommand {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Shows your active job requests.";
    }

    @Override
    public String getSyntax() {
        return "/jb list";
    }

    @Override
    public void perform(Player player, String[] args) {

    }
}
