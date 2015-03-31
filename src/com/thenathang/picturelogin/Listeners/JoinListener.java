package com.thenathang.picturelogin.Listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.thenathang.picturelogin.PictureLogin;
import com.thenathang.picturelogin.config.ConfigManager;
import com.thenathang.picturelogin.util.PictureUtil;

public class JoinListener implements Listener {
	static PictureLogin plugin;

	public JoinListener(PictureLogin plugin) {
	  JoinListener.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		if(!player.hasPermission("picturelogin.show"))
			return;
		
		if (plugin.getConfig().getBoolean("block-login-message") == true)
			event.setJoinMessage(null);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				
				if(player.hasPlayedBefore() == false && plugin.getConfig().getBoolean("show-first-join") == true) {
						new BukkitRunnable() {
				            @Override
				            public void run() {
								List<String> messages = plugin.getConfig().getStringList("first-join-messages");
								int num = 0;
								
								for (String string : messages) {
									string = PictureUtil.replaceThings(string, player);
									messages.set(num, string);
									num++;
								}
								
								if (plugin.getConfig().getBoolean("player-only") == false) {
									for (Player p : Bukkit.getServer().getOnlinePlayers()) {
										
										if (plugin.getConfig().getBoolean("clear-chat") == true) {
											for(int i=0;i<20;i++) {
												player.sendMessage("");
											}
										}
										
										ConfigManager.getMessage(messages, PictureUtil.getImage(player)).sendToPlayer(p);
										
									}        
								} else {
									if (plugin.getConfig().getBoolean("clear-chat") == true) {
										for(int i=0;i<20;i++) {
											event.getPlayer().sendMessage("");
										}
									}

									ConfigManager.getMessage(messages, PictureUtil.getImage(player)).sendToPlayer(player);
									
								}
								return;
				            }
				        }.runTaskAsynchronously(JoinListener.plugin);
				} else {
					new BukkitRunnable() {
			            @Override
			            public void run() {
					
			            	List<String> messages = plugin.getConfig().getStringList("messages");
			            	int num = 0;
					
			            	for (String string : messages) {
			            		string = PictureUtil.replaceThings(string, player);
			            		messages.set(num, string);
			            		num++;
			            	}
			            	
							if (plugin.getConfig().getBoolean("player-only") == false) {
								for (Player p : Bukkit.getServer().getOnlinePlayers()) {
									if (plugin.getConfig().getBoolean("clear-chat") == true) {
										for(int i=0;i<20;i++) {
											player.sendMessage("");
										}
									}
		
									ConfigManager.getMessage(messages, PictureUtil.getImage(player)).sendToPlayer(p);
								}    
								
							} else {
								
								if (plugin.getConfig().getBoolean("clear-chat") == true) {
									for(int i=0;i<20;i++) {
										event.getPlayer().sendMessage("");
									}
								}

								ConfigManager.getMessage(messages, PictureUtil.getImage(player)).sendToPlayer(player);
							}
			            }
			        }.runTaskAsynchronously(JoinListener.plugin);
				}
			            
			}
		}.runTaskLater(plugin, 3);
	}
}
