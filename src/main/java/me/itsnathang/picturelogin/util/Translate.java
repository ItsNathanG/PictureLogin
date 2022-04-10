package me.itsnathang.picturelogin.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class Translate {
    public static YamlConfiguration messages;

    private Translate() {
        throw new IllegalStateException("Utility Class");
    }

    public static String tl(String key) {
        return translate(key);
    }

    private static String translate(String key) {
        return getFilteredTranslation(key);
    }

    private static String getFilteredTranslation(String key) {
        return color(messages.getString(key)
                .replace("%prefix%", messages.getString("prefix"))
                .replace("%new_line%", "\n")
        );
    }

    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
