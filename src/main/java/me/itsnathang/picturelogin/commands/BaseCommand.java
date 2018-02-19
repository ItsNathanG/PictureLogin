package me.itsnathang.picturelogin.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.itsnathang.picturelogin.PictureLogin;

import static me.itsnathang.picturelogin.config.Language.tl;

public class BaseCommand implements CommandExecutor {
	private PictureLogin plugin;

	public BaseCommand(PictureLogin plugin) {
	  this.plugin = plugin;
	}
	  
	
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("picturelogin")) {
			if (args.length > 0) {
				if (!s.hasPermission("picturelogin.main")) {
					s.sendMessage(tl("no_permission"));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("reload")) {
					File f = new File(plugin.getDataFolder() + File.separator + "config.yml");
					try {
						plugin.getConfig().load(f);
					} catch (Exception e) {
						s.sendMessage(tl("error_reload_config"));
						e.printStackTrace();
						return false;
					}
					s.sendMessage(tl("reload_config"));
					return true;
				}
			} else {
			    s.sendMessage(ChatColor.GREEN + "PictureLogin " + ChatColor.GRAY + "v" +
                                 ChatColor.GREEN + plugin.getDescription().getVersion() + ChatColor.GRAY + " by " +
                                 ChatColor.GREEN + "NathanG");
			    s.sendMessage(tl("reload_config_help"));

				return true;
			}
		}
		return false;
	}
}
