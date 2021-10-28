package me.rebelmythik.jobsboard.commands.subcommands;

import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.guis.BrowseJobs;
import org.bukkit.entity.Player;

@SubcommandInfo(name = "browse", permission = "jobsboard.command.browse", requiresPlayer = true)
public class BrowseSubcommand extends PluginSubCommand {

    @Override
    public String getName() {
        return "browse";
    }

    @Override
    public String getDescription() {
        return "Opens the jobs board.";
    }

    @Override
    public String getSyntax() {
        return "/jb browse";
    }

    @Override
    public void perform(Player player, String[] args){
        BrowseJobs.BrowseGui(JobsBoardMain.getPluginInstance(), player);
    }
}
