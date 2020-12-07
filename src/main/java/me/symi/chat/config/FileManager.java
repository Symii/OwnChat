package me.symi.chat.config;

import me.symi.chat.OwnChat;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    private final OwnChat plugin;
    private FileConfiguration en_EN;
    private FileConfiguration pl_PL;
    private File en_EN_file;
    private File pl_PL_file;

    public FileManager(OwnChat plugin)
    {
        this.plugin = plugin;
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
        pl_PL = new YamlConfiguration();
        try
        {
            pl_PL.load(pl_PL_file);
        }
        catch(IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }

        en_EN_file = new File(plugin.getDataFolder() + File.separator + "language", "en_EN.yml");
        if(!en_EN_file.exists())
        {
            en_EN_file.getParentFile().mkdir();
            plugin.saveResource("en_EN.yml", false);
            File createdFile = new File(plugin.getDataFolder(), "en_EN.yml");
            createdFile.renameTo(new File(plugin.getDataFolder() + File.separator + "language" + File.separator + "en_EN.yml"));
        }
        en_EN = new YamlConfiguration();
        try
        {
            en_EN.load(en_EN_file);
        }
        catch(IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }

    }

    public List<FileConfiguration> getConfigurations()
    {
        return Arrays.asList(en_EN, pl_PL);
    }

    public List<File> getFiles()
    {
        return Arrays.asList(en_EN_file, pl_PL_file);
    }

}
