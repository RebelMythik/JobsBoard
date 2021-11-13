package me.rebelmythik.jobsboard.listeners;

import de.themoep.inventorygui.InventoryGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Event_inventory implements Listener {

    @EventHandler
    private void onClick(InventoryClickEvent e) {
        //InventoryGui gui = InventoryGui.get(e.getWhoClicked());
        //e.getWhoClicked().sendMessage(gui.getTitle());
    }
}
