package me.rebelmythik.jobsboard.database;

import me.rebelmythik.jobsboard.JobsBoardMain;


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

}
