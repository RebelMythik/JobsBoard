package me.rebelmythik.jobsboard.commands;

import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.guis.CreateJob;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "createjob", permission = "jobsboard.command.create", requiresPlayer = true)
public class CreateJobCmd extends PluginCommand {

    JobsBoardMain plugin;
    public CreateJobCmd(JobsBoardMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        me.rebelmythik.jobsboard.guis.CreateJob.createNewRequest(plugin, player, null, 1, 1, 1);
    }

}
