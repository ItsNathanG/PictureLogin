package me.itsnathang.picturelogin.util;

import me.itsnathang.picturelogin.config.ConfigManager;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

public class Hooks {
    private final PluginManager plugins;
    private final ConfigManager config;
    private final Logger logger;

    public static boolean AUTHME;
    public static boolean PLACEHOLDER_API;

    public Hooks(PluginManager plugins, ConfigManager config, Logger logger) {
        this.plugins = plugins;
        this.config = config;
        this.logger = logger;

        AUTHME = hookPlugin("AuthMe");
        PLACEHOLDER_API = hookPlugin("PlaceholderAPI");
    }

    private boolean hookPlugin(String plugin) {
        if (!plugins.isPluginEnabled(plugin)) {
            return false;
        }

        // Make sure user wants to hook into the plugin
        if (!config.getBoolean("hooks." + plugin, true)) {
            return false;
        }

        logger.info(() -> "Hooked into: " + plugin);
        return true;
    }

}
