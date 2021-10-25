package me.rebelmythik.requestboard.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.rebelmythik.requestboard.Requestboard;
import me.rebelmythik.requestboard.guis.Browsing;

public class openguicommand implements CommandExecutor {

    Requestboard plugin;
    public openguicommand(Requestboard plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("Requestboard.browse")) { sender.sendMessage("No u"); return true; }
        if (!(sender instanceof Player)) {
                return false;
        }
        Player player = (Player) sender;
        Browsing.BrowseGui(plugin, player);

        return true;
    }
}