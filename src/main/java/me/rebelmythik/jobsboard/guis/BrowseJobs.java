package me.rebelmythik.jobsboard.guis;

import java.util.List;

import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.GuiPageElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import me.rebelmythik.jobsboard.JobsBoardMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import me.rebelmythik.jobsboard.api.Job;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BrowseJobs {
    private Inventory inventory;

    public static void BrowseGui(JobsBoardMain plugin, Player pl) {
        Player player = pl;
        List<Job> jobList = plugin.jobList;

        //Gui Layout
        String[] BrowseGuiSetup = {
                "ggggggggg",
                "ggggggggg",
                "ggggggggg",
                "ggggggggg",
                "ggggggggg",
                "125 4  63"

        };

        // Creates the Inventories

        //Create Inventory Base
        InventoryGui browsegui = new InventoryGui(plugin, null, "JobsBoard", BrowseGuiSetup);

        //Add close menu button
        browsegui.addElement(new StaticGuiElement('1', new ItemStack(Material.BOOK), 1, click -> {

            browsegui.close();

            return true;
        }, ChatColor.GREEN + "Close Menu"));

        //Add previous page button
        browsegui.addElement(new GuiPageElement('2', new ItemStack(Material.ARROW), GuiPageElement.PageAction.PREVIOUS, "Previous Page (%prevpage%)"));

        //Add next page button
        browsegui.addElement(new GuiPageElement('3', new ItemStack(Material.ARROW), GuiPageElement.PageAction.NEXT, "Next Page (%nextpage%)"));

        //Add first page button
        browsegui.addElement(new GuiPageElement('5', new ItemStack(Material.ARROW), GuiPageElement.PageAction.FIRST, "First Page (%nextpage%)"));

        //Add last page button
        browsegui.addElement(new GuiPageElement('6', new ItemStack(Material.ARROW), GuiPageElement.PageAction.LAST, "Last Page (%nextpage%)"));

        //Add create request button
        browsegui.addElement(new StaticGuiElement('4', new ItemStack(Material.PAPER), 1, click -> {
            browsegui.close();
            if (!player.hasPermission("jobsboard.createrequest")) { player.sendMessage("No u"); return true; }
            CreateJob.createNewRequest(plugin, player, null, 1, 1, 1);
            return true;
        }, ChatColor.GREEN + "Create Request"));

        GuiElementGroup guiGroup = new GuiElementGroup('g');

        if (jobList.size() != 0) {
            for (int e = 0; e < jobList.size(); e++) {
                Job curReq = jobList.get(e);
                ItemStack item = curReq.getItem();
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<String>();
                lore.add(ChatColor.GREEN + "Posted By: " + curReq.getOwnerName());
                lore.add(ChatColor.GREEN + String.valueOf(curReq.getCount()) + " x " + item.getType().toString());
                lore.add(ChatColor.GREEN + "Reward: " + String.valueOf(curReq.getPrice()) + " moneys");
                lore.add(ChatColor.GREEN + String.valueOf(curReq.getTime()) + " Seconds Remaining");
                meta.setLore(lore);
                item.setItemMeta(meta);
                guiGroup.addElement(new StaticGuiElement('i', item, 1, click -> {

                    //open the request menu

                    return true;
                }));

            }
        }
        browsegui.addElement(guiGroup);
        //show the player page
        browsegui.show(player);
    }
}
//find a way to save the index of each page
