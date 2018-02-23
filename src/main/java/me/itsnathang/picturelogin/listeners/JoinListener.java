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
	private final PictureLogin plugin;
	private Player player;

	public JoinListener(PictureLogin plugin) {
	  this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.player = event.getPlayer();

		// only show message for players with picturelogin.show permission
		if(!checkPermission()) return;

		// block the default join message
		if (ConfigManager.getBoolean("block-join-message", false))
			event.setJoinMessage(null);

		ImageMessage pictureMessage = getMessage();

		if (pictureMessage == null) return;

		// send only to the player that joined?
		if (ConfigManager.getBoolean("player-only", true)) {
			pictureMessage.sendToPlayer(player);
			return;
		}

		PictureUtil.sendOutPictureMessage(pictureMessage);
	}

	private boolean checkPermission() {
		return (!ConfigManager.getBoolean("require-permission", true) ||
				player.hasPermission("picturelogin.show"));
	}

	private ImageMessage getMessage() {
		List<String> messages;

		// if it's a player's first time and feature is enabled, show different message
		if (ConfigManager.getBoolean("show-first-join", true) && !player.hasPlayedBefore())
			messages = ConfigManager.getStringList("first-join-messages");
		else
			messages = ConfigManager.getStringList("messages");

		return PictureUtil.createPictureMessage(player, messages);
	}
}
