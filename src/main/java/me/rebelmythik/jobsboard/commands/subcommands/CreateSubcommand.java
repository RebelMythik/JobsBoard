package me.rebelmythik.jobsboard.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public void perform(Player player, String @NotNull [] args){

        // user typed /jb create with no arguments
        if (args.length == 1) {
            sendCorrectUsageMessage(player);
        }
        else if (args.length > 1) {
            // user typed /jb create <item>
            if (args.length == 2) {
                sendCorrectUsageMessage(player);
            }
            // user typed /jb create <item> <amount>
            else if (args.length == 3) {
                sendCorrectUsageMessage(player);
            }
            // user typed /jb create <item> <amount> <reward> (allegedly)
            else if (args.length == 4) {

            }
            else {
                sendCorrectUsageMessage(player);
            }
        }
    }


}
