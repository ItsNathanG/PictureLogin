package me.itsnathang.picturelogin.listeners;

import java.awt.image.BufferedImage;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.util.PictureUtil;

public class QuitListener implements Listener {
	static PictureLogin plugin;

	public QuitListener(PictureLogin plugin) {
	  QuitListener.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		
		if(!player.hasPermission("picturelogin.show"))
			return;
		
		if (plugin.getConfig().getBoolean("block-leave-message") || !plugin.getConfig().getBoolean("show-leave-message")) {
			event.setQuitMessage(null);
		}
		
		if (plugin.getConfig().getBoolean("show-leave-message")) {
			new BukkitRunnable() {
	            public void run() {
					BufferedImage skin = PictureUtil.getImage(player);

					if (skin == null)
						return;

	            	List<String> messages = plugin.getConfig().getStringList("leave-messages");
	    			int num = -1;
	    			
	    			for (String string : messages) {
	    				num++;
	    				string = PictureUtil.replaceThings(string, player);
	    				messages.set(num, string);
	    			}
	    			
	    			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
	    				
	    				if (plugin.getConfig().getBoolean("clear-chat")) {
	    					for(int i=0;i<20;i++) {
	    						player.sendMessage("");
	    					}
	    				}
	    				
	    				ConfigManager.getMessage(messages, skin);
	    				
	    			}
	            }
	        }.runTaskAsynchronously(QuitListener.plugin);
		}
	}
}
