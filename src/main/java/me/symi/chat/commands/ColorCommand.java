package me.symi.chat.commands;

import me.symi.chat.OwnChat;
import me.symi.chat.config.ConfigManager;
import me.symi.chat.config.LanguageManager;
import me.symi.chat.gui.ChatInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ColorCommand implements CommandExecutor {

    private LanguageManager languageManager;
    private ConfigManager configManager;
    private OwnChat plugin;

    public ColorCommand(OwnChat plugin)
    {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
        this.configManager = plugin.getConfigManager();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageManager.getMessage("player-only-command"));
            return true;
        }

        final Player player = (Player) sender;

        if (player.hasPermission("ownchat.use")) {
            configManager.openInventory(player);
        } else {
            player.sendMessage(languageManager.getMessage("permission-message"));
        }

        return true;
    }

}
