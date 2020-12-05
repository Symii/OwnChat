package me.symi.chat;

import me.symi.chat.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class FabledChat extends JavaPlugin {

    @Override
    public void onEnable()
    {
        setupMetrics();
    }

    private void setupMetrics()
    {
        Metrics metrics = new Metrics(this, 9566);
        metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () ->
        {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            String javaVersion = System.getProperty("java.version");
            Map<String, Integer> entry = new HashMap<>();
            entry.put(javaVersion, 1);
            if (javaVersion.startsWith("1.7"))
            {
                map.put("Java 1.7", entry);
            }
            else if (javaVersion.startsWith("1.8"))
            {
                map.put("Java 1.8", entry);
            }
            else if (javaVersion.startsWith("1.9"))
            {
                map.put("Java 1.9", entry);
            }
            else
            {
                map.put("Other", entry);
            }
            return map;
        }));
    }

}
