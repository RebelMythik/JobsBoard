package me.rebelmythik.jobsboard.guis;

import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import me.rebelmythik.jobsboard.api.Job;
import me.rebelmythik.jobsboard.JobsBoardMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreateJob {

    public static void createNewRequest(JobsBoardMain plg, Player plr, ItemStack itm, int cnt, int prc, int tm) {
        JobsBoardMain plugin = plg;
        Player player = plr;
        ItemStack item;
        if (itm != null) {
            item = itm;
        } else {
            item = new ItemStack(Material.DIRT);
        }
        int count = cnt;
        int price = prc;
        int time = tm;

        //Gui Layout
        String[] BrowseGuiSetup = {
                "ccccccccc",
                "c1     2c",
                "c3     4c",
                "c   5   c",
                "c6     7c",
                "ccccccccc"
        };

        //Create Inventory
        InventoryGui browsegui = new InventoryGui(plugin, null, "Create Request", BrowseGuiSetup);

        //Close Menu
        browsegui.addElement(new StaticGuiElement('6', new ItemStack(Material.BARRIER), 1, click -> {
            browsegui.close();
            return true;
        }, ChatColor.GREEN + "Close Menu"));
        //Submit Button
        browsegui.addElement(new StaticGuiElement('5', new ItemStack(Material.EMERALD_BLOCK), 1, click -> {
            plugin.jobList.add(new Job(player.getUniqueId(), player.getName(), item, count, price));
            browsegui.close();
            BrowseJobs.BrowseGui(plugin, player);
            return true;
        }, ChatColor.GREEN + "Submit"));
        //Set Item Button
        browsegui.addElement(new StaticGuiElement('1', item, 1, click -> {
            if (click.getEvent().getCursor() != null) {
                item.setType(click.getEvent().getCursor().getType());
            }
            return true;
        }, ChatColor.GREEN + "Set Item (Drag item onto this slot)"));
        //Set Count Button
        browsegui.addElement(new StaticGuiElement('2', new ItemStack(Material.BOOK), 1, click -> {
            //Get number input


            return true;
        }, ChatColor.GREEN + "Item Count (Capped at 64); Current: " + count));
        //Set Price Button
        browsegui.addElement(new StaticGuiElement('3', new ItemStack(Material.BOOK), 1, click -> {
            //Get number input


            return true;
        }, ChatColor.GREEN + "Offered Price: " + price));
        //Set Time Button
        browsegui.addElement(new StaticGuiElement('4', new ItemStack(Material.BOOK), 1, click -> {
            //Get number input


            return true;
        }, ChatColor.GREEN + "Time until request expires in seconds XP (capped at 86400); Current: " + time + " seconds"));
        //Back to Browse Button
        browsegui.addElement(new StaticGuiElement('7', new ItemStack(Material.ARROW), 1, click -> {

            return true;
        }, ChatColor.GREEN + "Return to Browsing"));
        //Glass
        browsegui.addElement(new StaticGuiElement('c', new ItemStack(Material.BLUE_STAINED_GLASS_PANE), 1, click -> {
            return true;
        }, ChatColor.GREEN +"E"));
        browsegui.show(player);
    }


}
