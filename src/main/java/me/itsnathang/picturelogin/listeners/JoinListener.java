package me.itsnathang.picturelogin.listeners;

import com.bobacadodl.imgmessage.ImageMessage;
import fr.xephi.authme.api.v3.AuthMeApi;
import me.itsnathang.picturelogin.util.Hooks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.util.PictureUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinListener implements Listener {
	private PictureLogin plugin;
	private PictureUtil pictureUtil;
	private ConfigManager config;
	private Player player;

	public JoinListener(PictureLogin plugin) {
		this.plugin = plugin;
		this.config = plugin.getConfigManager();
		this.pictureUtil = plugin.getPictureUtil();
	}

	@EventHandler (priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent event) {
		this.player = event.getPlayer();

		// block the default join message
		if (config.getBoolean("block-join-message", false))
			event.setJoinMessage(null);

		if (Hooks.AUTHME) {
			authMeLogin();
			return;
		}

		sendImage();
	}

	private void sendImage() {
		// only show message for players with picturelogin.show permission
		if(!checkPermission()) return;

		ImageMessage pictureMessage = getMessage();

		if (pictureMessage == null) return;

		// send only to the player that joined?
		if (config.getBoolean("player-only", true)) {
			if (config.getBoolean("clear-chat", false))
				pictureUtil.clearChat(player);

			pictureMessage.sendToPlayer(player);
			return;
		}

		pictureUtil.sendOutPictureMessage(pictureMessage);
	}

	private boolean checkPermission() {
		if (!config.getBoolean("require-permission", true))
			return true;

		return player.hasPermission("picturelogin.show");
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

	private void authMeLogin() {
		new BukkitRunnable() {

			@Override
			public void run() {
				// Stop if player doesn't exist anymore
				if (player == null) {
					plugin.getLogger().info("Could not access player.");
					this.cancel();
				}
				// Check for authentication
				if (AuthMeApi.getInstance().isAuthenticated(player)) {
					plugin.getLogger().info("Sending picture message for " + player.getName());
					sendImage();
					this.cancel();
				}
			}

		}.runTaskTimer(plugin, 0L, 20L);
	}
}
