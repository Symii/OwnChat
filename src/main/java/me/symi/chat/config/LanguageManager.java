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

    private final Map<String, String> messages = new HashMap<>();
    private final OwnChat plugin;
    private final FileManager fileManager;
    private String plugin_prefix;

    public LanguageManager(OwnChat plugin)
    {
        this.plugin = plugin;
        this.fileManager = plugin.getFileManager();
    }

    public String getMessage(String message_name)
    {
        return messages.get(message_name).replace("%prefix%", plugin_prefix);
    }

    public void loadMessages()
    {
        ConfigManager configManager = plugin.getConfigManager();
        String language = plugin.getConfig().getString("language");
        File file = fileManager.getFile(language);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for(String key : config.getKeys(false))
        {
            String text = ChatUtil.fixColors(config.getString(key));
            messages.put(key, text);
        }

        plugin_prefix = messages.get("plugin-prefix");

        if(!VersionUtil.isAPIlevel1_13() && getMessage("inventory-title").length() > 24)
        {
            messages.put("inventory-title", getMessage("inventory-title").substring(0, 24));
        }
    }

}
