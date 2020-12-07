package me.symi.chat.config;

import me.symi.chat.OwnChat;

import me.symi.chat.utils.ChatUtil;
import me.symi.chat.utils.VersionUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {

    private Map<String, Map<String, String>> messages = new HashMap<>();
    private OwnChat plugin;
    private FileManager fileManager;
    private String plugin_prefix;

    public LanguageManager(OwnChat plugin)
    {
        this.plugin = plugin;
        this.fileManager = plugin.getFileManager();
    }

    public String getMessage(String locale, String message_name)
    {
        if (!locale.equalsIgnoreCase("en_EN") && !locale.equalsIgnoreCase("pl_PL"))
        {
            plugin.getLogger().severe("Invalid language file specified in your config.yml !!!");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return "";
        }

        return messages.get(locale).get(message_name).replace("%prefix%", plugin_prefix);
    }

    public void loadMessages()
    {
        for(File file : fileManager.getFiles())
        {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            Map<String, String> localeMessages = new HashMap<>();

            for(String key : config.getKeys(false))
            {
                String text = ChatUtil.fixColors(config.getString(key));
                localeMessages.put(key, text);
            }
            messages.put(file.getName().substring(0, 5), localeMessages);
        }
        plugin_prefix = messages.get(OwnChat.getInstance().getConfigManager().getLanguage()).get("plugin-prefix");

        ConfigManager configManager = OwnChat.getInstance().getConfigManager();
        if(!VersionUtil.isAPIlevel1_13() && getMessage(configManager.getLanguage(), "inventory-title").length() > 24)
        {
            messages.get(configManager.getLanguage()).put("inventory-title", getMessage(configManager.getLanguage(), "inventory-title").substring(0, 24));
        }
    }

}
