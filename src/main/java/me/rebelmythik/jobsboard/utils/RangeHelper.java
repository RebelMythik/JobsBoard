package me.rebelmythik.jobsboard.utils;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.AbstractIntList;
import me.rebelmythik.jobsboard.JobsBoardMain;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class RangeHelper {

    public static List<String> getItemAmountRange() {
        FileConfiguration config = JobsBoardMain.getPluginInstance().getConfig();
        return Lists.newArrayList(Integer.toString(config.getInt("jobs.item_limits.min")),
                Integer.toString(config.getInt("jobs.item_limits.max")));
    }

    public static List<String> getRewardAmountRange() {
        FileConfiguration config = JobsBoardMain.getPluginInstance().getConfig();
        return Lists.newArrayList(Double.toString(config.getDouble("jobs.reward_limits.min")),
                Double.toString(config.getDouble("jobs.reward_limits.max")));

    }
}
