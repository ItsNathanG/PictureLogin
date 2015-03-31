package com.thenathang.picturelogin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import com.thenathang.picturelogin.Listeners.JoinListener;
import com.thenathang.picturelogin.Listeners.QuitListener;
import com.thenathang.picturelogin.commands.BaseCommand;
import com.thenathang.picturelogin.config.ConfigManager;
import com.thenathang.picturelogin.util.PictureUtil;


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
