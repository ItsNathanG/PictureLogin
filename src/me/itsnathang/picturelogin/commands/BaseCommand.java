package me.itsnathang.picturelogin.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.itsnathang.picturelogin.PictureLogin;

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
					s.sendMessage(ChatColor.GREEN + "PictureLogin " + ChatColor.GRAY + "» Configuration reloaded!");
					return true;
				}
			} else {
			    s.sendMessage(ChatColor.GREEN + "PictureLogin " + ChatColor.GRAY + "v" +
                                 ChatColor.GREEN + plugin.getDescription().getVersion() + ChatColor.GRAY + " by " +
                                 ChatColor.GREEN + "NathanG");
			    s.sendMessage(ChatColor.GRAY + "» " + ChatColor.GREEN + "/picturelogin reload" +
                                 ChatColor.GRAY + " to reload the config file.");

				return true;
			}
		}
		return false;
	}
}
