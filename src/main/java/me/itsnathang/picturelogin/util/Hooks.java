package me.itsnathang.picturelogin.util;

import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

public class Hooks {
    private PluginManager plugins;
    private Logger logger;

    public static boolean AUTHME;
    public static boolean PLACEHOLDER_API;

    public Hooks(PluginManager manager, Logger log) {
        plugins = manager;
        logger = log;

        hookAuthMe();
        hookPlaceHolderAPI();
    }

    private boolean hookPlugin(String plugin) {
        if (!plugins.isPluginEnabled(plugin))
            return false;

        logger.info(() -> "Hooked into: " + plugin);
        return true;
    }

    private void hookAuthMe() {
        AUTHME = hookPlugin("AuthMe");
    }

    private void hookPlaceHolderAPI() {
        PLACEHOLDER_API = hookPlugin("PlaceholderAPI");
    }
}
