package me.itsnathang.picturelogin.listeners;

import java.awt.image.BufferedImage;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.util.PictureUtil;

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
		
		if (plugin.getConfig().getBoolean("block-join-message"))
			event.setJoinMessage(null);

		new BukkitRunnable() {
			public void run() {
				
				if(!player.hasPlayedBefore() && plugin.getConfig().getBoolean("show-first-join")) {
						new BukkitRunnable() {
				            public void run() {
								List<String> messages = plugin.getConfig().getStringList("first-join-messages");
								int num = 0;
                                BufferedImage skin = PictureUtil.getImage(player);

                                if (skin == null)
                                    return;
								
								for (String string : messages) {
									string = PictureUtil.replaceThings(string, player);
									messages.set(num, string);
									num++;
								}
								
								if (!plugin.getConfig().getBoolean("player-only")) {
									for (Player p : Bukkit.getServer().getOnlinePlayers()) {
										
										if (plugin.getConfig().getBoolean("clear-chat")) {
											for(int i=0;i<20;i++) {
												player.sendMessage("");
											}
										}
										
										ConfigManager.getMessage(messages, skin).sendToPlayer(p);
										
									}        
								} else {
									if (plugin.getConfig().getBoolean("clear-chat")) {
										for(int i=0;i<20;i++) {
											event.getPlayer().sendMessage("");
										}
									}

									ConfigManager.getMessage(messages, skin).sendToPlayer(player);
									
								}
				            }
				        }.runTaskAsynchronously(JoinListener.plugin);
				} else {
					new BukkitRunnable() {
			            public void run() {
					
			            	List<String> messages = plugin.getConfig().getStringList("messages");
			            	int num = 0;
                            BufferedImage skin = PictureUtil.getImage(player);

                            if (skin == null)
                                return;
					
			            	for (String string : messages) {
			            		string = PictureUtil.replaceThings(string, player);
			            		messages.set(num, string);
			            		num++;
			            	}
			            	
							if (plugin.getConfig().getBoolean("player-only")) {
								for (Player p : Bukkit.getServer().getOnlinePlayers()) {
									if (plugin.getConfig().getBoolean("clear-chat")) {
										for(int i=0;i<20;i++) {
											player.sendMessage("");
										}
									}
		
									ConfigManager.getMessage(messages, skin).sendToPlayer(p);
								}    
								
							} else {
								
								if (plugin.getConfig().getBoolean("clear-chat")) {
									for(int i=0;i<20;i++) {
										event.getPlayer().sendMessage("");
									}
								}

								ConfigManager.getMessage(messages, skin).sendToPlayer(player);
							}
			            }
			        }.runTaskAsynchronously(JoinListener.plugin);
				}
			            
			}
		}.runTaskLater(plugin, 3);
	}
}
