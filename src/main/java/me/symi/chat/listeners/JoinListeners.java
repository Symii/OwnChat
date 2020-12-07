package me.symi.chat.listeners;

import me.symi.chat.OwnChat;
import me.symi.chat.color.ChatColor;
import me.symi.chat.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListeners implements Listener {

    private final OwnChat plugin;

    public JoinListeners(OwnChat plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        if(player.hasPermission("ownchat.use"))
        {
            new ChatColor(player);
        }

        if(plugin.isUpdate_available() && player.hasPermission("ownchat.admin"))
        {
            player.sendMessage(ChatUtil.fixColors("&f&lOwn&9&lChat &8» &eThere is a new update available. You can download it here &6spigotmc.org"));
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        OwnChat.getInstance().getPlayerDataManager().removePlayer(player.getName());
    }

}
