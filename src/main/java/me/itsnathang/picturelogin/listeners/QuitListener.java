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
	private static PictureLogin plugin;

	public QuitListener(PictureLogin plugin) {
	  QuitListener.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(!player.hasPermission("picturelogin.show") || !plugin.getConfig().getBoolean("show-leave-message"))
			return;
		
		if (plugin.getConfig().getBoolean("block-leave-message"))
			event.setQuitMessage(null);

		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			BufferedImage picture = PictureUtil.getImage(player);
			List<String> picture_message;

			picture_message = plugin.getConfig().getStringList("leave-messages");

			// TODO: Add default picture backup.
			if (picture == null)
				return;

			// TODO: Implement PlaceholderAPI
			// Replace messages with our variables
			picture_message.replaceAll(message -> PictureUtil.replaceThings(message, player));

			// Send picture message to all online players
			Bukkit.getOnlinePlayers().forEach((online_player) -> {
				if (plugin.getConfig().getBoolean("clear-chat"))
					for(int i=0;i<20;i++)
						online_player.sendMessage("");

				ConfigManager.getMessage(picture_message, picture).sendToPlayer(online_player);
			});
		});
	}
}
