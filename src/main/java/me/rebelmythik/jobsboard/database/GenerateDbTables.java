package me.rebelmythik.jobsboard.database;

import me.rebelmythik.jobsboard.JobsBoardMain;

public class GenerateDbTables {

    /** throw all the database tables that need to be generated for the plugin to function inside of this function **/
    public static void MakeDbTables(){
        DatabaseHelper helper = JobsBoardMain.getPluginInstance().getDatabaseHelper();
        String prefix = JobsBoardMain.getPluginInstance().getDbPrefix();

        /** jb_activejobs **/
        helper.executeSQLWithoutReturn(
                String.format("""
                CREATE TABLE IF NOT EXISTS %sactivejobs (
                    id integer PRIMARY KEY,
                    uuid text NOT NULL,
                    name text NOT NULL,
                    item text NOT NULL,
                    quanity integer NOT NULL,
                    reward real NOT NULL,
                    expiration_date integer NOT NULL
                );
                """, prefix)
        );
    }



}
