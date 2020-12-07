package me.symi.chat.listeners;

import me.symi.chat.OwnChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListeners implements Listener {

    private final OwnChat plugin;

    public ChatListeners(OwnChat plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerCHat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        if(player.hasPermission("ownchat.use"))
        {
            String chatColor = plugin.getPlayerDataManager().getChatColor(player.getName());
            event.setMessage(chatColor + event.getMessage());
        }
    }

}
