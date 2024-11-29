package me.itsnathang.picturelogin;

import me.itsnathang.picturelogin.commands.BaseCommand;
import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.listeners.JoinListener;
import me.itsnathang.picturelogin.listeners.QuitListener;
import me.itsnathang.picturelogin.util.Hooks;
import me.itsnathang.picturelogin.util.PictureUtil;
import me.itsnathang.picturelogin.util.Updater;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

/*
Updated to work with Minecraft 1.18.2 by _NickV on 4/7/2022
 */
public class PictureLogin extends JavaPlugin {
    private ConfigManager configManager;
    private PictureUtil pictureUtil;

    @Override
    public void onEnable() {
        // load config & languages file
        configManager = new ConfigManager(this);

        // Register Plugin Hooks
        new Hooks(getServer().getPluginManager(), configManager, getLogger());

        // Initialize Picture Utility
        pictureUtil = new PictureUtil(this);

        // register Listeners
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        // (only register leave listener if enabled in config)
        if (configManager.getBoolean("show-leave-message", false)) {
            getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        }

        // register /picturelogin command
        getCommand("picturelogin").setExecutor(new BaseCommand(this));

        // Update Checker
        if (configManager.getBoolean("update-check", true)) {
            new Updater(getLogger(), getDescription().getVersion());
        }

        // bStats integration
        if (configManager.getBoolean("metrics", true)) {
            new Metrics(this, 14892); // 2225 is the bStats plugin ID for PictureLogin
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PictureUtil getPictureUtil() {
        return pictureUtil;
    }
}
