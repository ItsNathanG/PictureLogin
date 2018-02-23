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

    private static void hookPlugin(String plugin) {
        logger.info("Hooked into " + plugin + ".");
    }

    private static void hookAuthMe() {
        if (AUTHME = plugins.isPluginEnabled("AuthMe"))
            hookPlugin("AuthMe");
    }

    private static void hookPlaceHolderAPI() {
        if (PLACEHOLDER_API = plugins.isPluginEnabled("PlaceholderAPI"))
            hookPlugin("PlaceholderAPI");
    }
}
