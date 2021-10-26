package me.rebelmythik.jobsboard.Vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    public static Economy economy = null;
    public static EconomyResponse response;

    public static boolean getVault() {
        return Bukkit.getServer().getPluginManager().getPlugin("Vault") !=null;

    }

    public static boolean createEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;

    }

    public static Long getMoney(Player p) {
        if (p != null) {
            try {
                return (long) economy.getBalance(p);

            } catch (NullPointerException ignore) {

            }
        }
        return 0L;
    }
    public static void removeMoney(Player player, Long amount) {
        economy.withdrawPlayer(player, amount);
    }

    public static void removeMoney(OfflinePlayer player, Long amount) {
        economy.withdrawPlayer(player, amount);
    }

    public static void addMoney(Player player, Long amount) {
        economy.depositPlayer(player, amount);
    }

    public static void addMoney(OfflinePlayer player, Long amount) {
        economy.depositPlayer(player, amount);
    }


}
