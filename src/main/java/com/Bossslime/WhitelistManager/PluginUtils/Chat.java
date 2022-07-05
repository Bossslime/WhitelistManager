package com.Bossslime.WhitelistManager.PluginUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Chat {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void tellConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(Chat.color(message));
    }
}
