package me.itsnathang.picturelogin.listeners;

import com.bobacadodl.imgmessage.ImageMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.util.PictureUtil;

public class JoinListener implements Listener {
	private PictureUtil pictureUtil;
	private ConfigManager config;
	private Player player;

	public JoinListener(PictureLogin plugin) {
		this.config = plugin.getConfigManager();
		this.pictureUtil = plugin.getPictureUtil();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.player = event.getPlayer();

		// only show message for players with picturelogin.show permission
		if(!checkPermission()) return;

		// block the default join message
		if (config.getBoolean("block-join-message", false))
			event.setJoinMessage(null);

		ImageMessage pictureMessage = getMessage();

		if (pictureMessage == null) return;

		// send only to the player that joined?
		if (config.getBoolean("player-only", true)) {
			pictureMessage.sendToPlayer(player);
			return;
		}

		pictureUtil.sendOutPictureMessage(pictureMessage);
	}

	private boolean checkPermission() {
		return (!config.getBoolean("require-permission", true) ||
				player.hasPermission("picturelogin.show"));
	}

	private ImageMessage getMessage() {
		String msgType;

		// if it's a player's first time and feature is enabled, show different message
		if (config.getBoolean("show-first-join", true) && !player.hasPlayedBefore())
			msgType = "first-join-messages";
		else
			msgType = "messages";

		return pictureUtil.createPictureMessage(player, config.getStringList(msgType));
	}
}
