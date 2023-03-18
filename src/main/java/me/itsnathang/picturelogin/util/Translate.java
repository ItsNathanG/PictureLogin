package me.itsnathang.picturelogin.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translate {
    public static YamlConfiguration messages;

    private Translate() {
        throw new IllegalStateException("Utility Class");
    }

    public static String translateString(String key) {
        final String message = messages.getString(key)
                .replace("%prefix%", messages.getString("prefix"))
                .replace("%new_line%", "\n");

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String translateHexColor(final String message) {
        final char colorChar = ChatColor.COLOR_CHAR;

        // Added optional catch for & because some plugins (Essentials?) require
        // or allow the & symbol before hex.
        Pattern pattern = Pattern.compile("&?#([a-fA-F0-9]{6})");
        final Matcher matcher = pattern.matcher(message);
        final StringBuilder buffer = new StringBuilder(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }

}
