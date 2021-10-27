package me.rebelmythik.jobsboard.commands;

import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.guis.BrowseJobs;
import org.bukkit.entity.Player;

@CommandInfo(name = "jobsboard", permission = "jobsboard.command.browse", requiresPlayer = true)
public class BrowseJobsCmd extends PluginCommand {

    @Override
    public void execute(Player player, String[] args) {
        BrowseJobs.BrowseGui(JobsBoardMain.getPluginInstance(), player);
    }
}