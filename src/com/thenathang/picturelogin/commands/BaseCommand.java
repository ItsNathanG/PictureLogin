package com.thenathang.picturelogin.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.thenathang.picturelogin.PictureLogin;

public class BaseCommand implements CommandExecutor {
	PictureLogin plugin;

	public BaseCommand(PictureLogin plugin) {
	  this.plugin = plugin;
	}
	  
	
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("picturelogin")) {
			if (args.length > 0) {
				if (!s.hasPermission("picturelogin.main")) {
					s.sendMessage(ChatColor.RED + "[PL] You don't have permission to do this!");
					return true;
				}
				
				if (args[0].equalsIgnoreCase("reload")) {
					File f = new File(plugin.getDataFolder() + File.separator + "config.yml");
					try {
						plugin.getConfig().load(f);
					} catch (Exception e) {
						s.sendMessage("Couldn't reload config. :(");
						e.printStackTrace();
						return false;
					}
					s.sendMessage("Config reloaded!");
					return true;
				}
			} else {
				s.sendMessage(ChatColor.DARK_GREEN + "Picture Login by TheNathanG");
				s.sendMessage(ChatColor.DARK_GREEN + "Use /picturelogin reload to reload the config!");
				return true;
			}
		}
		return false;
	}
}
