package me.rebelmythik.jobsboard.commands.subcommands;

import com.earth2me.essentials.Essentials;
import com.google.common.collect.Lists;
import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.database.DbCommands;
import me.rebelmythik.jobsboard.utils.NumberHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.ess3.api.IItemDb;

import java.util.*;

@SubcommandInfo(name = "browse", permission = "jobsboard.command.create", requiresPlayer = true)
public class CreateSubcommand extends PluginSubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates a new job request.";
    }

    @Override
    public String getSyntax() {
        return "/jb create <item> <amount> <reward>";
    }

    private void sendCorrectUsageMessage(@NotNull Player player) {
        player.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
    }

    private int getExpirationDate(int expDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR, expDays * 24);
        return (int) ((c.getTimeInMillis())/1000);
    }

    @Override
    public void perform(Player player, String @NotNull [] args){

        FileConfiguration config = JobsBoardMain.getPluginInstance().getConfig();
        int expDays = config.getInt("jobs.days_before_expiration");
        int minItemAmount = config.getInt("jobs.item_limits.min");
        int maxItemAmount = config.getInt("jobs.item_limits.max");
        double minReward = config.getDouble("jobs.reward_limits.min");
        double maxReward = config.getDouble("jobs.reward_limits.max");

        if (args.length != 4) {
            sendCorrectUsageMessage(player);
            return;
        }
        else if (Material.getMaterial(args[1].toUpperCase()) == null) {
            player.sendMessage(ChatColor.RED + "Please input a valid item name.");
            sendCorrectUsageMessage(player);
            return;
        }
        else if (!NumberHelper.isInt(args[2])) {
            player.sendMessage(ChatColor.RED + "Please input a valid integer for item amount.");
            sendCorrectUsageMessage(player);
            return;
        }
        else if (Integer.parseInt(args[2]) < minItemAmount || Integer.parseInt(args[2]) > maxItemAmount) {
            player.sendMessage(ChatColor.RED + "You may only request between " + minItemAmount + " and " + maxItemAmount + " items per job.");
            return;
        }
        else if (!NumberHelper.isDouble(args[3])) {
            player.sendMessage(ChatColor.RED + "Please input a valid number for the job reward. Example: 100.00");
            return;
        }
        else if (Double.parseDouble(args[3]) < minReward || Double.parseDouble(args[3]) > maxReward) {
            player.sendMessage(ChatColor.RED + "You may only set your reward between $" + minReward + " and $" + maxReward);
            return;
        }
        else {
            DbCommands.AddJobToDb(player.getUniqueId().toString(), player.getName(), args[1].toUpperCase(), args[2], args[3], Integer.toString(getExpirationDate(expDays)));
            player.sendMessage(ChatColor.GREEN + "Job Request Submitted!");
        }
    }
}

