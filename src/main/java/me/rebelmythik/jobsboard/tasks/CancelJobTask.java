package me.rebelmythik.jobsboard.tasks;

import me.rebelmythik.jobsboard.JobsBoardMain;
import org.bukkit.scheduler.BukkitRunnable;

public class CancelJobTask extends BukkitRunnable {

    JobsBoardMain plugin;

    public CancelJobTask(JobsBoardMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // the code inside this function will run when the task is executed
        System.out.println("sussy baka");
    }
}
