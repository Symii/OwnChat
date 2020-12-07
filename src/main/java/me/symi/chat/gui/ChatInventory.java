package me.symi.chat.gui;

import me.symi.chat.OwnChat;
import me.symi.chat.config.ConfigManager;
import me.symi.chat.config.LanguageManager;
import me.symi.chat.utils.ChatUtil;
import me.symi.chat.utils.VersionUtil;
import me.symi.chat.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ChatInventory {

    public static Inventory getInventory() {

        ConfigManager configManager = OwnChat.getInstance().getConfigManager();
        LanguageManager languageManager = OwnChat.getInstance().getLanguageManager();

        Inventory inventory = Bukkit.createInventory(null, 18, languageManager.getMessage(configManager.getLanguage(), "inventory-title"));
        inventory.setItem(0, createItem(XMaterial.GREEN_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "green-color-name"), languageManager.getMessage(configManager.getLanguage(), "green-color-lore")));
        inventory.setItem(1, createItem(XMaterial.BLUE_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "blue-color-name"), languageManager.getMessage(configManager.getLanguage(), "blue-color-lore")));
        inventory.setItem(2, createItem(XMaterial.CYAN_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "cyan-color-name"), languageManager.getMessage(configManager.getLanguage(), "cyan-color-lore")));
        inventory.setItem(3, createItem(XMaterial.WHITE_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "white-color-name"), languageManager.getMessage(configManager.getLanguage(), "white-color-lore")));
        inventory.setItem(4, createItem(XMaterial.YELLOW_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "yellow-color-name"), languageManager.getMessage(configManager.getLanguage(), "yellow-color-lore")));
        inventory.setItem(5, createItem(XMaterial.ORANGE_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "orange-color-name"), languageManager.getMessage(configManager.getLanguage(), "orange-color-lore")));
        inventory.setItem(6, createItem(XMaterial.GRAY_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "gray-color-name"), languageManager.getMessage(configManager.getLanguage(), "gray-color-lore")));
        inventory.setItem(7, createItem(XMaterial.PURPLE_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "purple-color-name"), languageManager.getMessage(configManager.getLanguage(), "purple-color-lore")));
        inventory.setItem(8, createItem(XMaterial.LIGHT_GRAY_DYE.parseItem(), languageManager.getMessage(configManager.getLanguage(), "light-gray-color-name"), languageManager.getMessage(configManager.getLanguage(), "light-gray-color-lore")));
        inventory.setItem(inventory.getSize() - 1, createItem(XMaterial.BARRIER.parseItem(), languageManager.getMessage(configManager.getLanguage(), "reset-color-name"), languageManager.getMessage(configManager.getLanguage(), "reset-color-lore")));

        return inventory;
    }

    private static ItemStack createItem(ItemStack itemStack, String name, String lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtil.fixColors(name));
        itemMeta.setLore(Arrays.asList(ChatUtil.fixColors(lore)));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static ItemStack createItem(Material material, String name, String lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtil.fixColors(name));
        itemMeta.setLore(Arrays.asList(ChatUtil.fixColors(lore)));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
