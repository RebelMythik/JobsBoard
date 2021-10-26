package me.rebelmythik.jobsboard.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.guis.BrowseJobs;

@CommandInfo(name = "jobsboard", permission = "jobsboard.command.browse", requiresPlayer = true)
public class BrowseJobsCmd extends PluginCommand {

    JobsBoardMain plugin;
    public BrowseJobsCmd(JobsBoardMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        BrowseJobs.BrowseGui(plugin, player);
    }
}