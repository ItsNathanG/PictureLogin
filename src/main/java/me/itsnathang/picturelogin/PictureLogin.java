package me.itsnathang.picturelogin;

import me.itsnathang.picturelogin.listeners.JoinListener;
import me.itsnathang.picturelogin.listeners.QuitListener;
import me.itsnathang.picturelogin.commands.BaseCommand;
import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.util.PictureUtil;

import me.itsnathang.picturelogin.util.Updater;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.MetricsLite;

public class PictureLogin extends JavaPlugin implements CommandExecutor {

	@Override
	public void onEnable() {
		// load config & languages file
		new ConfigManager(this);

		// register Listeners
		this.getServer().getPluginManager().registerEvents(new JoinListener(this), this);

		// (only register leave listener if enabled in config)
		if (ConfigManager.getBoolean("show-leave-message", false))
			this.getServer().getPluginManager().registerEvents(new QuitListener(), this);

		// register /picturelogin command
		getCommand("picturelogin").setExecutor(new BaseCommand(this));

		// Initialize Picture Utility
		new PictureUtil(this);

		// Update Checker
		new Updater(this.getLogger(), this.getDescription().getVersion());

		// bStats integration
		if (ConfigManager.getBoolean("metrics", true))
			new MetricsLite(this);
	}
	
}
