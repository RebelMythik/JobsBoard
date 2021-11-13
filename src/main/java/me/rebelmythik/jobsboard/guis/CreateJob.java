package me.rebelmythik.jobsboard.guis;

import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.api.Job;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreateJob {

    public static void createNewRequest(JobsBoardMain plugin, Player player, ItemStack item, int count, int price, int time) {
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
        //Set Item Button (DIRT IF NO JOB ITEM)
        if (item != null)
            browsegui.addElement(new StaticGuiElement('1', item,
                    ChatColor.GREEN + "Job Icon: " + StringUtils.capitalize(item.getType().name().toLowerCase())));
        else
            browsegui.addElement(new StaticGuiElement('1', new ItemStack(Material.DIRT), 1, click -> {
                ItemStack _item = click.getEvent().getCursor();
                if (_item != null) { //If item set, create a new UI
                    createNewRequest(plugin, player, _item, count, price, time);
                    click.getGui().close();
                }
                return true;
            }, ChatColor.RED + "Set Item (Drag/Click item onto this slot)"));
        //Set Count Button
        browsegui.addElement(new StaticGuiElement('2', new ItemStack(Material.BOOK), ChatColor.GREEN + "Item Count (Capped at 64); Current: " + count));
        //Set Price Button
        browsegui.addElement(new StaticGuiElement('3', new ItemStack(Material.BOOK), ChatColor.GREEN + "Offered Price: " + price));
        //Set Time Button
        browsegui.addElement(new StaticGuiElement('4', new ItemStack(Material.BOOK), ChatColor.GREEN + "Time until request expires in seconds XP (capped at 86400); Current: " + time + " seconds"));
        //Back to Browse Button
        browsegui.addElement(new StaticGuiElement('7', new ItemStack(Material.ARROW), ChatColor.GREEN + "Return to Browsing"));
        //Glass
        browsegui.addElement(new StaticGuiElement('c', new ItemStack(Material.BLUE_STAINED_GLASS_PANE), ChatColor.GREEN +"E"));


        browsegui.show(player);
    }

}
