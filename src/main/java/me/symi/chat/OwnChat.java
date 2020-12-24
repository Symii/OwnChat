package me.symi.chat;

import me.symi.chat.commands.ColorCommand;
import me.symi.chat.config.ConfigManager;
import me.symi.chat.config.FileManager;
import me.symi.chat.config.LanguageManager;
import me.symi.chat.database.SQLite;
import me.symi.chat.listeners.ChatListeners;
import me.symi.chat.listeners.InventoryListeners;
import me.symi.chat.listeners.JoinListeners;
import me.symi.chat.metrics.MetricsLite;
import me.symi.chat.playerdata.PlayerDataManager;
import me.symi.chat.utils.UpdateChecker;
import me.symi.chat.utils.VersionUtil;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class OwnChat extends JavaPlugin {

    private static OwnChat INSTACNE;
    private SQLite database;
    private PlayerDataManager playerDataManager;
    private FileManager fileManager;
    private LanguageManager languageManager;
    private ConfigManager configManager;
    private boolean update_available = false;

    @Override
    public void onLoad()
    {
        INSTACNE = this;
    }

    @Override
    public void onEnable()
    {
        fileManager = new FileManager(this);
        languageManager = new LanguageManager(this);
        languageManager.loadMessages();
        configManager = new ConfigManager(this);
        database = new SQLite(this);
        playerDataManager = new PlayerDataManager();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListeners(this), this);
        pluginManager.registerEvents(new JoinListeners(this), this);
        pluginManager.registerEvents(new InventoryListeners(this), this);

        if(configManager.isMetrics_enabled())
        {
            setupMetrics();
        }

        if(configManager.isUpdate_check_enabled())
        {
            checkForUpdate();
        }

        this.getCommand("color").setExecutor(new ColorCommand(this));
    }

    @Override
    public void onDisable()
    {
        if(playerDataManager != null)
            playerDataManager.onDisable();
        if(database != null)
            database.onDisable();
    }

    private void setupMetrics()
    {
        new MetricsLite(this, 9574);
    }

    private void checkForUpdate()
    {
        Logger logger = this.getLogger();

        new UpdateChecker(this, 86502).getVersion(version ->
        {
            if (this.getDescription().getVersion().equalsIgnoreCase(version))
            {
                logger.info("There is not a new update available.");
                this.update_available = false;
            }
            else
                {
                logger.warning("There is a new update available.");
                logger.warning("You can download it here https://www.spigotmc.org/resources/ownchat.86502/");
                this.update_available = true;
            }
        });
    }

    public static OwnChat getInstance()
    {
        return INSTACNE;
    }

    public SQLite getDatabase()
    {
        return database;
    }

    public PlayerDataManager getPlayerDataManager()
    {
        return playerDataManager;
    }

    public FileManager getFileManager()
    {
        return fileManager;
    }

    public LanguageManager getLanguageManager()
    {
        return languageManager;
    }

    public ConfigManager getConfigManager()
    {
        return configManager;
    }

    public boolean isUpdate_available()
    {
        return update_available;
    }

}
