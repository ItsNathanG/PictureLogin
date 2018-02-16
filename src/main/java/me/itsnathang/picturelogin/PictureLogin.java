package me.itsnathang.picturelogin;

import me.itsnathang.picturelogin.listeners.JoinListener;
import me.itsnathang.picturelogin.listeners.QuitListener;
import me.itsnathang.picturelogin.commands.BaseCommand;
import me.itsnathang.picturelogin.config.ConfigManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import me.itsnathang.picturelogin.util.PictureUtil;


public class PictureLogin extends JavaPlugin implements CommandExecutor {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);
		getServer().getPluginManager().registerEvents(new QuitListener(this), this);
		getCommand("picturelogin").setExecutor(new BaseCommand(this));
		
		new PictureUtil(this);
		new ConfigManager(this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
}
