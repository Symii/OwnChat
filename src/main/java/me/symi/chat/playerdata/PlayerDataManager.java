package me.symi.chat.playerdata;

import me.symi.chat.OwnChat;
import me.symi.chat.color.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {

    private final Map<String, ChatColor> playerDataStorage = new HashMap<>();

    public void addPlayer(String player_name, ChatColor chatColor)
    {
        playerDataStorage.put(player_name, chatColor);
    }

    public void removePlayer(String player_name)
    {
        if(playerDataStorage.get(player_name) != null)
        {
            playerDataStorage.get(player_name).saveAsync();
            playerDataStorage.remove(player_name);
        }
    }

    public String getChatColor(String player_name)
    {
        if(playerDataStorage.containsKey(player_name))
        {
            return playerDataStorage.get(player_name).getColor();
        }

        return OwnChat.getInstance().getConfigManager().getDefault_color();
    }

    public void onDisable()
    {
        for(ChatColor data : playerDataStorage.values())
        {
            data.save();
        }
        playerDataStorage.clear();
    }

}
