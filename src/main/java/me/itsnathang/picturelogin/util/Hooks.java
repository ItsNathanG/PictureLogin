package me.itsnathang.picturelogin.util;

import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

public class Hooks {
    private static PluginManager plugins;
    private static Logger logger;

    public static boolean AUTHME;
    public static boolean PLACEHOLDER_API;

    public Hooks(PluginManager manager, Logger log) {
        plugins = manager;
        logger = log;

        hookAuthMe();
        hookPlaceHolderAPI();
    }

    private static boolean hookPlugin(String plugin) {
        if (!plugins.isPluginEnabled(plugin))
            return false;

        logger.info(() -> "Hooked into: " + plugin);
        return true;
    }

    private static void hookAuthMe() {
        AUTHME = hookPlugin("AuthMe");
    }

    private static void hookPlaceHolderAPI() {
        PLACEHOLDER_API = hookPlugin("PlaceholderAPI");
    }
}
