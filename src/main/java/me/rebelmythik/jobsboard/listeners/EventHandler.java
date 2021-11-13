package me.rebelmythik.jobsboard.listeners;

import me.rebelmythik.jobsboard.JobsBoardMain;
import org.bukkit.Bukkit;

public class EventHandler {

    Event_join event_join = new Event_join();
    Event_inventory event_inventory = new Event_inventory();

    public void load() {
        Bukkit.getPluginManager().registerEvents(event_join, JobsBoardMain.getPluginInstance());
        Bukkit.getPluginManager().registerEvents(event_inventory, JobsBoardMain.getPluginInstance());
    }

}
