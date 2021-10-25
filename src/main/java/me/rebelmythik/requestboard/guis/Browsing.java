package me.rebelmythik.requestboard.guis;

import java.util.List;

import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.GuiPageElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import me.rebelmythik.requestboard.Requestboard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import me.rebelmythik.requestboard.api.Request;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Browsing {
    private Inventory inventory;

    public static void BrowseGui(Requestboard plugin, Player pl) {
        Player player = pl;
        List<Request> requestList = plugin.requestList;

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
        InventoryGui browsegui = new InventoryGui(plugin, null, "RequestBoard", BrowseGuiSetup);

        //Add close menu button
        browsegui.addElement(new StaticGuiElement('1', new ItemStack(Material.BOOK), 1, click -> {

            browsegui.close();

            return true;
        }, ChatColor.GREEN + "Close Menu"));

        //Add previous page button
        browsegui.addElement(new GuiPageElement('2', new ItemStack(Material.ARROW), GuiPageElement.PageAction.PREVIOUS, "Go to previous page (%prevpage%)"));

        //Add next page button
        browsegui.addElement(new GuiPageElement('3', new ItemStack(Material.ARROW), GuiPageElement.PageAction.NEXT, "Go to next page (%nextpage%)"));

        //Add first page button
        browsegui.addElement(new GuiPageElement('5', new ItemStack(Material.ARROW), GuiPageElement.PageAction.FIRST, "Go to first page (%nextpage%)"));

        //Add last page button
        browsegui.addElement(new GuiPageElement('6', new ItemStack(Material.ARROW), GuiPageElement.PageAction.LAST, "Go to last page (%nextpage%)"));

        //Add create request button
        browsegui.addElement(new StaticGuiElement('4', new ItemStack(Material.PAPER), 1, click -> {
            browsegui.close();
            if (!player.hasPermission("Requestboard.createrequest")) { player.sendMessage("No u"); return true; }
            CreateRequest.createNewRequest(plugin, player, null, 1, 1, 1);
            return true;
        }, ChatColor.GREEN + "Create Request"));

        GuiElementGroup guiGroup = new GuiElementGroup('g');

        if (requestList.size() != 0) {
            for (int e = 0; e < requestList.size(); e++) {
                Request curReq = requestList.get(e);
                ItemStack item = curReq.getItem();
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<String>();
                lore.add(ChatColor.GREEN + "Requester: " + curReq.getOwnerName());
                lore.add(ChatColor.GREEN + String.valueOf(curReq.getCount()) + " x " + item.getType().toString());
                lore.add(ChatColor.GREEN + "Price: " + String.valueOf(curReq.getPrice()) + " moneys");
                lore.add(ChatColor.GREEN + "Time remaining: " + String.valueOf(curReq.getTime()) + " seconds");
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
