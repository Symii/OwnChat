package me.symi.chat.utils;

import org.bukkit.Bukkit;

public class VersionUtil {

    public static boolean isAPIlevel1_13()
    {
        String bukkit_version = Bukkit.getBukkitVersion();
        if(bukkit_version.contains("1.13") || bukkit_version.contains("1.14")
                || bukkit_version.contains("1.15") || bukkit_version.contains("1.16"))
        {
            return true;
        }

        return false;
    }




}
