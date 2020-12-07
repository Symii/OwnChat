package me.symi.chat.config;

import me.symi.chat.OwnChat;
import me.symi.chat.utils.ChatUtil;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class ConfigManager {

    private OwnChat plugin;
    private String default_color, language;
    private boolean update_check, metrics;
    private HashMap<String, String[]> colors = new HashMap<>();
    private Sound click_sound;

    public ConfigManager(OwnChat plugin)
    {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reloadConfig();
        loadColors();
    }

    public void reloadConfig()
    {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        default_color = ChatUtil.fixColors(config.getString("default-chat-color"));
        update_check = config.getBoolean("update-checker");
        metrics = config.getBoolean("plugin-metrics");
        language = config.getString("language");
        try
        {
            click_sound = Sound.valueOf(config.getString("click-sound"));
        }
        catch(IllegalArgumentException e)
        {
            plugin.getLogger().warning("Invalid click-sound for this minecraft version!");
            plugin.getLogger().warning("You can change click-sound value in your config.yml file");
        }

    }

    public boolean isUpdate_check_enabled()
    {
        return update_check;
    }

    public boolean isMetrics_enabled()
    {
        return metrics;
    }

    public String getDefault_color()
    {
        return default_color;
    }

    public String getLanguage()
    {
        return language;
    }

    private void loadColors()
    {
        colors.clear();
        for(String cc : plugin.getConfig().getConfigurationSection("colors").getKeys(false))
        {
            String[] color = ChatUtil.fixColors(plugin.getConfig().getString("colors." + cc)).split(":");
            String[] array = {color[1], color[2]};
            colors.put(color[0], array);
        }
    }

    public HashMap<String, String[]> getColors()
    {
        return colors;
    }

    public Sound getClick_sound()
    {
        return click_sound;
    }

}
