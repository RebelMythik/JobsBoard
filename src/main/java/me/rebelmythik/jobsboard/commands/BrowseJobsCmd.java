package me.rebelmythik.jobsboard.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.guis.BrowseJobs;

public class BrowseJobsCmd implements CommandExecutor {

    JobsBoardMain plugin;
    public BrowseJobsCmd(JobsBoardMain plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("jobsboard.browse")) { sender.sendMessage(ChatColor.DARK_RED + "YOU ARE SUSSY BAKA"); return true; }
        if (!(sender instanceof Player)) {
                return false;
        }
        Player player = (Player) sender;
        BrowseJobs.BrowseGui(plugin, player);

        return true;
    }
}