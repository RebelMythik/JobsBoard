package me.rebelmythik.requestboard.commands;

import me.rebelmythik.requestboard.Requestboard;
import me.rebelmythik.requestboard.guis.CreateRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateRequestCommand implements CommandExecutor {

    Requestboard plugin;
    public CreateRequestCommand(Requestboard plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("Requestboard.createrequest")) { sender.sendMessage("No u"); return true; }
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        CreateRequest.createNewRequest(plugin, player, null, 1, 1, 1);
        return true;
    }
}
