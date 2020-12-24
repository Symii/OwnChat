package me.symi.chat.config;

import me.symi.chat.OwnChat;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private final OwnChat plugin;
    private FileConfiguration en_EN;
    private FileConfiguration pl_PL;
    private File en_EN_file;
    private File pl_PL_file;

    public FileManager(OwnChat plugin)
    {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        createConfigs();
    }

    public FileConfiguration getEn_EN()
    {
        return en_EN;
    }

    public FileConfiguration getPl_PL()
    {
        return pl_PL;
    }

    private void createConfigs()
    {
        pl_PL_file = new File(plugin.getDataFolder() + File.separator + "language", "pl_PL.yml");
        if(!pl_PL_file.exists())
        {
            pl_PL_file.getParentFile().mkdir();
            plugin.saveResource("pl_PL.yml", false);
            File createdFile = new File(plugin.getDataFolder(), "pl_PL.yml");
            createdFile.renameTo(new File(plugin.getDataFolder() + File.separator + "language" + File.separator + "pl_PL.yml"));
        }

        en_EN_file = new File(plugin.getDataFolder() + File.separator + "language", "en_EN.yml");
        if(!en_EN_file.exists())
        {
            en_EN_file.getParentFile().mkdir();
            plugin.saveResource("en_EN.yml", false);
            File createdFile = new File(plugin.getDataFolder(), "en_EN.yml");
            createdFile.renameTo(new File(plugin.getDataFolder() + File.separator + "language" + File.separator + "en_EN.yml"));
        }

        pl_PL = new YamlConfiguration();
        en_EN = new YamlConfiguration();

        try
        {
            pl_PL.load(pl_PL_file);
            en_EN.load(en_EN_file);
        }
        catch(IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }

    }

    public File getFile(String language)
    {
        switch(language)
        {
            case "pl_PL":
                return pl_PL_file;
            default:
                return en_EN_file;
        }
    }

}
