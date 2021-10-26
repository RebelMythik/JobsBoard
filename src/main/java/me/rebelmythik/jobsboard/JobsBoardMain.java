package me.rebelmythik.jobsboard;

import me.rebelmythik.jobsboard.Vault.Vault;
import me.rebelmythik.jobsboard.api.Job;
import me.rebelmythik.jobsboard.commands.CreateJobCmd;
import me.rebelmythik.jobsboard.commands.BrowseJobsCmd;
import me.rebelmythik.jobsboard.database.*;
import me.rebelmythik.jobsboard.tasks.CancelJobTask;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public final class JobsBoardMain extends JavaPlugin {

    public static JobsBoardMain self;
    private static FileConfiguration mySqlConfig;
    private static File mySqlYml;

    private static File mainConfigFile;
    private static FileConfiguration mainConfig;

    public String dbPrefix = "jb_";
    public static MySQLCore database;
    private static DatabaseManager databaseManager;
    private static DatabaseHelper databaseHelper;
    private static String host = "localhost"; // Host to connect for MySQL
    private static String user = "username"; // User to try and connect with for MySQL
    private static String pass = "passy"; // Password for above
    private static String databat = "database"; // Database name to use
    private static short port = 3306; // Port for the host for MySQL
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

        // register all listeners present in .listeners package
        RegisterListeners();
        
        // create config.yml in the plugin folder
        this.saveDefaultConfig();
        // create mysql.yml file in the plugin folder
        createMySqlYml();

        if (!Vault.createEconomy()) {
            saveDefaultConfig();
        }

        // on runTaskTimer, first param is the plugin it's running on, second param is delay before schedule starts
        // third parameter is length of time between tasks being ran. 20L = 1 second, 1L = 1 Tick and 20 ticks = 1 second
        BukkitTask jobCanceller = new CancelJobTask(this).runTaskTimer(this, 0L, 200L);
    }

    //make static method to create a sign
    private void createMySqlYml () {
        mySqlYml = new File(getDataFolder(), "mysql.yml");
        if (mySqlYml.exists()) {
            mySqlYml.getParentFile().mkdirs();
            saveResource("mysql.yml", false);
        }

        mySqlConfig = new YamlConfiguration();
        try {
            mySqlConfig.load(mySqlYml);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public static FileConfiguration getMySqlConfig () {
        return mySqlConfig;
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
    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
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

    // automatically registers listeners for each class in listeners package
    private void RegisterListeners() {
        String packageName = getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName + ".listeners")
                .getSubTypesOf(Listener.class)
        ) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor()
                        .newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}