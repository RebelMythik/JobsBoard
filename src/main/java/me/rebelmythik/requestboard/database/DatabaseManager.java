package me.rebelmythik.requestboard.database;

import me.rebelmythik.requestboard.Requestboard;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.scheduler.BukkitTask;

import java.sql.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

public class DatabaseManager {

    private final Queue<DatabaseTask> sqlQueue = new LinkedBlockingQueue<>();
    private final AbstractDatabaseCore database;
    private final Requestboard plugin;
    private final boolean useQueue;
    private BukkitTask task;

    /**
     * Queued database manager. Use queue to solve run SQL make server lagg issue.
     *
     * @param plugin plugin main class
     * @param dbCore database core
     * @throws ConnectionException when database connection failed
     */
    public DatabaseManager(Requestboard plugin, AbstractDatabaseCore dbCore) throws ConnectionException {
        this.plugin = plugin;
        DatabaseConnection connection = dbCore.getConnection();
        try {
            if (!connection.isValid()) {
                throw new DatabaseManager.ConnectionException("The database does not appear to be valid!");
            }
        } finally {
            connection.release();
        }

        this.database = dbCore;
        this.useQueue = plugin.getConfig().getBoolean("database.queue");

        if (useQueue) {
            try {
                task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> plugin.getDatabaseManager().runTask(), 1, plugin.getConfig().getLong("database.queue-commit-interval") * 20);
            } catch (IllegalPluginAccessException e) {
                plugin.getDatabaseManager().runTask();
            }
        }
    }

    /**
     * Returns true if the table exists
     *
     * @param table The table to check for
     * @return True if the table is found
     * @throws SQLException Throw exception when failed execute somethins on SQL
     */
    boolean hasTable(String table) throws SQLException {
        DatabaseConnection connection = database.getConnection();
        ResultSet rs = connection.get().getMetaData().getTables(null, null, "%", null);
        boolean match = false;
        while (rs.next()) {
            if (table.equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                match = true;
                break;
            }
        }
        rs.close();
        connection.release();
        return match;
    }

    /**
     * Returns true if the given table has the given column
     *
     * @param table  The table
     * @param column The column
     * @return True if the given table has the given column
     * @throws SQLException If the database isn't connected
     */
    public boolean hasColumn(String table, String column) throws SQLException {
        if (!hasTable(table)) {
            return false;
        }

        DatabaseConnection connection = database.getConnection();
        String query = "SELECT * FROM " + table + " LIMIT 1";
        boolean match = false;
        try {
            PreparedStatement ps = connection.get().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                if (metaData.getColumnLabel(i).equals(column)) {
                    match = true;
                    break;
                }
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            return match;
        } finally {
            connection.release();
        }
        return match; // Uh, wtf.
    }

    /**
     * Internal method, runTasks in queue.
     */
    private synchronized void runTask() { // synchronized for QUICKSHOP-WX
        synchronized (sqlQueue) {
            if (sqlQueue.isEmpty()) {
                return;
            }
            DatabaseConnection dbconnection = this.database.getConnection();
            Connection connection = dbconnection.get();
            try {
                //start our commit
                connection.setAutoCommit(false);

                while (true) {
                    if (!dbconnection.isValid()) {
                        return; // Waiting next crycle and hope it success reconnected.
                    }


                    DatabaseTask task = sqlQueue.poll();
                    if (task == null) {
                        break;
                    }
                    // Util.debugLog("Executing the SQL task: " + task);

                    task.run(connection);


                }
                if (!connection.getAutoCommit()) {
                    connection.commit();
                    connection.setAutoCommit(true);
                }

            } catch (SQLException sqle) {

                this.plugin
                        .getLogger()
                        .log(Level.WARNING, "Database connection may lost, we are trying reconnecting, if this message appear too many times, you should check your database file(sqlite) and internet connection(mysql).", sqle);
            } finally {
                dbconnection.release();
            }
        }

//        try {
//            this.database.getConnection().commit();
//        } catch (SQLException e) {
//            try {
//                this.database.getConnection().rollback();
//            } catch (SQLException ignored) {
//            }
//        }
    }

    /**
     * Add DatabaseTask to queue waiting flush to database,
     *
     * @param task The DatabaseTask you want add in queue.
     */
    public void runInstantTask(DatabaseTask task) {
        DatabaseConnection connection = database.getConnection();
        task.run(connection.get());
        connection.release();
    }

    /**
     * Add DatabaseTask to queue waiting flush to database,
     *
     * @param task The DatabaseTask you want add in queue.
     */
    public void addDelayTask(DatabaseTask task) {
        if (useQueue) {
            synchronized (sqlQueue) {
                sqlQueue.offer(task);
            }
        } else {
            runInstantTask(task);
        }
    }

    /**
     * Unload the DatabaseManager, run at onDisable()
     */
    public synchronized void unInit() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        plugin.getLogger().info("Please wait for the data to flush its data...");
        runTask();
        database.close();
    }


    /**
     * Represents a connection error, generally when the server can't connect to MySQL or something.
     */
    public static final class ConnectionException extends Exception {
        private static final long serialVersionUID = 8348749992936357317L;

        private ConnectionException(String msg) {
            super(msg);
        }

    }


    public AbstractDatabaseCore getDatabase() {
        // TODO Auto-generated method stub
        return database;
    }
}
