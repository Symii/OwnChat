package me.symi.chat.listeners;

import me.symi.chat.OwnChat;
import me.symi.chat.color.ChatColor;
import me.symi.chat.config.ConfigManager;
import me.symi.chat.config.LanguageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryListeners implements Listener {

    private final OwnChat plugin;
    private LanguageManager languageManager;
    private ConfigManager configManager;

    public InventoryListeners(OwnChat plugin)
    {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event)
    {
        if(event.getClickedInventory() == null)
        {
            return;
        }

        if(event.getView().getTitle().equalsIgnoreCase(languageManager.getMessage(configManager.getLanguage(), "inventory-title")))
        {
            event.setCancelled(true);
            ItemStack current_item = event.getCurrentItem();
            if(current_item == null || current_item.hasItemMeta() == false)
            {
                return;
            }

            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();
            HashMap<String, String[]> colors = configManager.getColors();

            for(String[] array : colors.values())
            {
                if(current_item.getItemMeta().getDisplayName().equalsIgnoreCase(languageManager.getMessage(configManager.getLanguage(), array[1])))
                {
                    ChatColor chatColor = new ChatColor(player, array[0]);
                    plugin.getPlayerDataManager().addPlayer(player.getName(), chatColor);
                    player.sendMessage(languageManager.getMessage(configManager.getLanguage(), "color-choose"));
                    player.closeInventory();
                    player.playSound(player.getLocation(), configManager.getClick_sound(), 1.25f, 0.8f);
                    break;
                }
            }

            if(event.getSlot() == 17)
            {
                ChatColor chatColor = new ChatColor(player, configManager.getDefault_color());
                plugin.getPlayerDataManager().addPlayer(player.getName(), chatColor);
                player.playSound(player.getLocation(), configManager.getClick_sound(), 1.25f, 0.8f);
                player.sendMessage(languageManager.getMessage(configManager.getLanguage(), "color-reset"));
                player.closeInventory();
            }


        }
    }

}
