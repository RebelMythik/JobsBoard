package me.rebelmythik.jobsboard.database;

import me.rebelmythik.jobsboard.JobsBoardMain;
import me.rebelmythik.jobsboard.api.Job;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DbCommands {



    public static void AddJobToDb(String owner_uuid, String owner_name, String item, String quantity, String price, String expiration_date)
    {
        DatabaseHelper helper = JobsBoardMain.getPluginInstance().getDatabaseHelper();
        String prefix = JobsBoardMain.getPluginInstance().getDbPrefix();

        helper.executeSQLWithoutReturn(
                "INSERT INTO " + prefix + "activejobs (owner_uuid, owner_name, item, quantity, price, expiration_date) VALUES ( " +
                        owner_uuid + ", " + owner_name + ", " + item + ", " + quantity + ", " + price + ", " + expiration_date + ");"
        );
    }

//    public static List<Job> GetJobsFromUUID(String UUID) throws SQLException {
//        List<Job> jobs = new ArrayList<>();
//        DatabaseHelper helper = JobsBoardMain.getPluginInstance().getDatabaseHelper();
//        String prefix = JobsBoardMain.getPluginInstance().getDbPrefix();
//
//        var results = helper.executeSQLWithReturn(String.format("SELECT * FROM %sactivejobs WHERE owner_uuid = %s", prefix, UUID));
//
//        while(results.next()) {
//            String owner_uuid = results.getString("uuid");
//            String owner_name = results.getString("name");
//            String item = results.getString("item");
//            String quantity = results.getString("quantity");
//            String reward = results.getString("reward");
//            String exp_date = results.getString("expiration_date");
//
//        }
//
//    }
}
