package me.rebelmythik.jobsboard.commands;

import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.guis.CreateJob;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateJobCmd implements CommandExecutor {

    JobsBoardMain plugin;
    public CreateJobCmd(JobsBoardMain plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("jobsboard.createrequest")) { sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to run this command."); return true; }
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;

        // args: /jb create ITEM AMOUNT REWARD






        me.rebelmythik.jobsboard.guis.CreateJob.createNewRequest(plugin, player, null, 1, 1, 1);
        return true;
    }
}
