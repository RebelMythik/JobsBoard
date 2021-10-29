package me.rebelmythik.jobsboard.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemHelper {

    public static List<String> getItemList() {
        List<String> blocks = new ArrayList<>();
        for (Material mat : Material.values()) if (mat.isBlock()) blocks.add(mat.name().toLowerCase());
        return blocks;
    }

}
