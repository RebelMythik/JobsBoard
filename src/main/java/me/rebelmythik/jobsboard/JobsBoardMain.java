package me.rebelmythik.jobsboard;

import me.rebelmythik.jobsboard.Vault.Vault;
import me.rebelmythik.jobsboard.api.Job;
import me.rebelmythik.jobsboard.commands.CreateJobCmd;
import me.rebelmythik.jobsboard.commands.BrowseJobsCmd;
import me.rebelmythik.jobsboard.database.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class JobsBoardMain extends JavaPlugin {

    public static JobsBoardMain self;
    private static FileConfiguration customConfig;
    private static File customConfigFile;

    public String dbPrefix = "dot";
    public static MySQLCore database;
    private static DatabaseManager databaseManager;
    private static DatabaseHelper databaseHelper;
    private static String host = "put-host-here"; // Host to connect for MySQL
    private static String user = "put-user-here"; // User to try and connect with for MySQL
    private static String pass = "put-pass-here"; // Password for above
    private static String databat = "put-database-name-here"; // Database name to use
    private static short port = 1234; // Port for the host for MySQL
    private boolean useSSL = false; // Use ssl for MySQL?

    public ArrayList<Job> jobList = new ArrayList<Job>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("jobsboard").setExecutor(new BrowseJobsCmd(this));
        getCommand("createjob").setExecutor(new CreateJobCmd(this));

        self = this;

        try {
            setupDatabase();
        } catch (Exception e) {
            // Ahh Shit Something Broke
            e.printStackTrace();
        }
        getLogger().info("Database Connected");

        createCustomConfig();
        if (!Vault.createEconomy()) {
            saveDefaultConfig();
        }

    }

    //make static method to create a sign
    private void createCustomConfig () {
        customConfigFile = new File(getDataFolder(), "mysql.yml");
        if (customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("mysql.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public static FileConfiguration getCustomConfig () {
        return customConfig;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("Database Closed");
    }
    /** - Gets the database prefix for usage in DatabaseHelper */
    public String getDbPrefix() {
        return dbPrefix;
    }

    /** - Getter for the database manager */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /** - Getter for the database helper */
    public DatabaseManager getDatabaseHelper() {
        return databaseManager;
    }

    /** - Sets up the database on init; Run on enable / plugin reload *if you want* */
    private boolean setupDatabase() {
        try {
            org.bukkit.configuration.ConfigurationSection dbCfg = getConfig().getConfigurationSection("database");
            AbstractDatabaseCore dbCore;
            // SQLite database - Doing this handles file creation
            dbCore = new SQLiteCore(this, new java.io.File(this.getDataFolder(), "database.db"));

            // TODO: Uncomment the below and comment the above to use MySQL. If you wanna make it switch based on config options, it should be pretty easy.

            //dbCore = new MySQLCore(this, host, user, pass, databat, Short.toString(port), useSSL);
            this.databaseManager = new DatabaseManager(this, ServiceInjector.getDatabaseCore(dbCore));
            // Make the database up to date
            this.databaseHelper = new DatabaseHelper(this, this.databaseManager);
        } catch (DatabaseManager.ConnectionException e) {
            getLogger().log(java.util.logging.Level.SEVERE, "Error when connecting to the database", e);
            getServer().getPluginManager().disablePlugin(this);
            return false;
        } catch (Exception e) {
            getLogger().log(java.util.logging.Level.SEVERE, "Error when setup database", e);
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }
}