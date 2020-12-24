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
    private final LanguageManager languageManager;
    private final ConfigManager configManager;

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

        if(event.getView().getTitle().equalsIgnoreCase(languageManager.getMessage("inventory-title")))
        {
            event.setCancelled(true);
            ItemStack current_item = event.getCurrentItem();
            if((!(event.getWhoClicked() instanceof Player)) || current_item == null
                    || current_item.hasItemMeta() == false)
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();
            HashMap<String, String[]> colors = configManager.getColors();

            for(String key : colors.keySet())
            {
                if(current_item.getItemMeta().getDisplayName().equalsIgnoreCase(colors.get(key)[0]))
                {
                    ChatColor chatColor = new ChatColor(player, colors.get(key)[1]);
                    plugin.getPlayerDataManager().addPlayer(player.getName(), chatColor);
                    player.sendMessage(languageManager.getMessage("color-choose"));
                    player.closeInventory();
                    player.playSound(player.getLocation(), configManager.getClick_sound(), 1.25f, 0.8f);
                    break;
                }
            }

            if(event.getSlot() == 26)
            {
                ChatColor chatColor = new ChatColor(player, configManager.getDefault_color());
                plugin.getPlayerDataManager().addPlayer(player.getName(), chatColor);
                player.playSound(player.getLocation(), configManager.getClick_sound(), 1.25f, 0.8f);
                player.sendMessage(languageManager.getMessage("color-reset"));
                player.closeInventory();
            }


        }
    }

}
