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
	private static PictureLogin plugin;

	public JoinListener(PictureLogin plugin) {
	  JoinListener.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(!player.hasPermission("picturelogin.show"))
			return;
		
		if (plugin.getConfig().getBoolean("block-join-message"))
			event.setJoinMessage(null);

		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			BufferedImage picture = PictureUtil.getImage(player);
			List<String> picture_message;

			// Show a different message on a first player join
			if(!player.hasPlayedBefore() && plugin.getConfig().getBoolean("show-first-join"))
				picture_message = plugin.getConfig().getStringList("first-join-messages");
			else
				picture_message = plugin.getConfig().getStringList("messages");

			// TODO: Add default picture backup.
			if (picture == null)
				return;

			// TODO: Implement PlaceholderAPI
			// Replace messages with our variables
			picture_message.replaceAll(message -> PictureUtil.replaceThings(message, player));

			// Only show picture message to the player that logged in
			if (plugin.getConfig().getBoolean("player-only")) {
				ConfigManager.getMessage(picture_message, picture).sendToPlayer(player);
				return;
			}

			// Send picture message to all online players
			Bukkit.getOnlinePlayers().forEach((online_player) -> {
				if (plugin.getConfig().getBoolean("clear-chat"))
					for(int i=0;i<20;i++)
						online_player.sendMessage("");
				
				ConfigManager.getMessage(picture_message, picture).sendToPlayer(online_player);
				return;
			});

		});
	}
}
