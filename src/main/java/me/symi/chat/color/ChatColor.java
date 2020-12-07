package me.symi.chat.color;

import me.symi.chat.OwnChat;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatColor {

    private Player player;
    private String color;

    public ChatColor(Player player)
    {
        this.player = player;
        setData();
    }

    public ChatColor(Player player, String color)
    {
        this.player = player;
        this.color = color;
    }

    public Player getPlayer()
    {
        return player;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }


    public void save()
    {
        try
        {
            String query = "UPDATE players SET data=? WHERE playername=?";
            PreparedStatement statement = OwnChat.getInstance().getDatabase().getConnection().prepareStatement(query);
            statement.setString(1, color);
            statement.setString(2, player.getName());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void saveAsync()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String query = "UPDATE players SET data=? WHERE playername=?";
                    PreparedStatement statement = OwnChat.getInstance().getDatabase().getConnection().prepareStatement(query);
                    statement.setString(1, color);
                    statement.setString(2, player.getName());
                    statement.executeUpdate();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(OwnChat.getInstance());
    }

    private void setData()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                String data = OwnChat.getInstance().getConfigManager().getDefault_color();

                try
                {
                    String query = "SELECT data FROM players WHERE playername=?";
                    PreparedStatement statement = OwnChat.getInstance().getDatabase().getConnection().prepareStatement(query);
                    statement.setString(1, player.getName());
                    ResultSet row = statement.executeQuery();
                    if(row.next())
                    {
                        data = row.getString("data");
                    }
                    else
                    {
                        PreparedStatement insert = OwnChat.getInstance().getDatabase().getConnection().prepareStatement("INSERT INTO players (id,playername,data) VALUES(NULL,?,?);");
                        insert.setString(1, player.getName());
                        insert.setString(2, data);
                        insert.executeUpdate();
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    setColor(data);
                    OwnChat.getInstance().getPlayerDataManager().addPlayer(player.getName(), ChatColor.this);
                }

            }
        }.runTaskAsynchronously(OwnChat.getInstance());
    }

}
