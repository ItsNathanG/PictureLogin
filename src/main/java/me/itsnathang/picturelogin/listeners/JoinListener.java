package me.itsnathang.picturelogin.listeners;

import java.util.List;

import com.bobacadodl.imgmessage.ImageMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

		// only show message for players with picturelogin.show permission
		if(!player.hasPermission("picturelogin.show"))
			return;

		// block the default join message
		if (ConfigManager.getBoolean("block-join-message"))
			event.setJoinMessage(null);

		List<String> messages;

		// if it's a player's first time and feature is enabled, show different message
		if (ConfigManager.getBoolean("show-first-join") && !player.hasPlayedBefore())
			messages = ConfigManager.getStringList("first-join-messages");
		else
			messages = ConfigManager.getStringList("messages");

		ImageMessage picture_message = PictureUtil.createPictureMessage(player, messages);

		// send only to the player that joined?
		if (ConfigManager.getBoolean("player-only")) {
			picture_message.sendToPlayer(player);
			return;
		}

		// send message to everyone on the server
		plugin.getServer().getOnlinePlayers().forEach(picture_message :: sendToPlayer);
	}
}
