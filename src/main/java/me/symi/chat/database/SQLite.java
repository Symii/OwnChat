package me.symi.chat.database;

import me.symi.chat.OwnChat;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLite {

    private final OwnChat plugin;
    private Connection connection;

    public SQLite(OwnChat plugin)
    {
        this.plugin = plugin;
        connect();
    }

    public void connect()
    {
        try
        {
            File file = new File(plugin.getDataFolder(), "database.db");
            if(!file.exists())
            {
                new File(plugin.getDataFolder().getPath()).mkdir();
            }
            String URL = "jdbc:sqlite:" + file;

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            plugin.getLogger().info("connected into SQLite database successfully");
            createTable();
        }
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try
        {
            connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void createTable()
    {
        String query = "CREATE TABLE IF NOT EXISTS players(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "playername TEXT NOT NULL, " +
                "data TEXT NOT NULL" +
                ");";
        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void onDisable()
    {
        disconnect();
    }

    public Connection getConnection()
    {
        return connection;
    }

}
