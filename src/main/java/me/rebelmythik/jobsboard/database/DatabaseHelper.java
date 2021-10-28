package me.rebelmythik.jobsboard.database;

import me.rebelmythik.jobsboard.JobsBoardMain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private final DatabaseManager manager;

    private final JobsBoardMain plugin;

    public DatabaseHelper(JobsBoardMain plugin, DatabaseManager manager) throws SQLException {
        this.plugin = plugin;
        this.manager = manager;

    }

    /** - Creates a table in the database for name and appends the SQL parameters specified in fields. */

    public void createTaxTable(String name, String fields) {
        String sqlString = "CREATE TABLE " + plugin.getDbPrefix()
                + name + " (" + fields + ");";
        manager.runInstantTask(new DatabaseTask(sqlString));
    }

    /** - Selects everything in a table by name of table. */

    public WarpedResultSet selectAll(String table) throws SQLException {
        DatabaseConnection databaseConnection = manager.getDatabase().getConnection();
        Statement st = databaseConnection.get().createStatement();
        String selectAllTaxes = "SELECT * FROM " + plugin.getDbPrefix() + table;
        ResultSet resultSet = st.executeQuery(selectAllTaxes);
        return new WarpedResultSet(st, resultSet, databaseConnection);
    }

    /** - Runs an SQL operation raw. You'll need to supply the exact SQL. */
    public void executeSQLWithoutReturn(String sqlString) {
        manager.addDelayTask(new DatabaseTask(sqlString, (ps) -> {}));
    }

    /** - Runs an SQL operation raw with a return. You'll need to supply the exact SQL. */

    public ResultSet executeSQLWithReturn(String sqlString) {
        ResultSet returnable = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = manager.getDatabase().getConnection();
        Statement st = null;
        try {
            st = databaseConnection.get().createStatement();
        } catch (SQLException e) {
            // ahhh shit
            e.printStackTrace();
            databaseConnection.release();
            return null;
        }
        try {
            returnable = st.executeQuery(sqlString);
        } catch (SQLException e) {
            // ahhh shitttttttt
            e.printStackTrace();
            databaseConnection.release();
            return null;
        }

        databaseConnection.release();
        return returnable;
    }
}

