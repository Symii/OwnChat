package me.symi.chat.gui;

import me.symi.chat.OwnChat;
import me.symi.chat.config.LanguageManager;
import me.symi.chat.utils.HeadUtil;
import me.symi.chat.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class ChatInventory {

    public static Inventory getChatInventory()
    {
        LanguageManager languageManager = OwnChat.getInstance().getLanguageManager();
        FileConfiguration config = OwnChat.getInstance().getConfig();
        Inventory inventory = Bukkit.createInventory(null, 27, languageManager.getMessage("inventory-title"));
        int counter = 0;

        for(String key : config.getConfigurationSection("colors").getKeys(false))
        {
            if(config.getBoolean("colors." + key + ".enabled"))
            {
                ItemStack head = HeadUtil.getHead(config.getString("colors." + key + ".head-skin-value"));
                SkullMeta head_meta = (SkullMeta) head.getItemMeta();
                head_meta.setDisplayName(languageManager.getMessage(key + "-color-name"));
                head_meta.setLore(Arrays.asList(languageManager.getMessage(key + "-color-lore")));
                head.setItemMeta(head_meta);
                inventory.setItem(counter, head);
                counter++;
            }
        }

        ItemStack reset_button = XMaterial.BARRIER.parseItem();
        ItemMeta reset_button_meta = reset_button.getItemMeta();
        reset_button_meta.setDisplayName(languageManager.getMessage("reset-color-name"));
        reset_button_meta.setLore(Arrays.asList(languageManager.getMessage("reset-color-lore")));
        reset_button.setItemMeta(reset_button_meta);

        inventory.setItem(inventory.getSize() - 1, reset_button);
        return inventory;
    }


}
