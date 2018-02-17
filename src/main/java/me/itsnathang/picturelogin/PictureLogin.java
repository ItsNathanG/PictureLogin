package me.itsnathang.picturelogin;

import me.itsnathang.picturelogin.listeners.JoinListener;
import me.itsnathang.picturelogin.listeners.QuitListener;
import me.itsnathang.picturelogin.commands.BaseCommand;
import me.itsnathang.picturelogin.config.ConfigManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.MetricsLite;

import me.itsnathang.picturelogin.util.PictureUtil;


public class PictureLogin extends JavaPlugin implements CommandExecutor {
	@Override
	public void onEnable() {
		// Load config file
		new ConfigManager(this);

		getServer().getPluginManager().registerEvents(new JoinListener(this), this);

		// Only register listener if enabled in config
		if (getConfig().getBoolean("show-leave-message"))
			getServer().getPluginManager().registerEvents(new QuitListener(this), this);

		// /picturelogin command
		getCommand("picturelogin").setExecutor(new BaseCommand(this));

		new PictureUtil(this);

		// bStats integration
		if (ConfigManager.getMetrics())
			new MetricsLite(this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
}
