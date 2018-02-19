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
		
		if(!player.hasPermission("picturelogin.show"))
			return;
		
		if (ConfigManager.getBoolean("block-join-message"))
			event.setJoinMessage(null);

		List<String> messages;

		if (ConfigManager.getBoolean("show-first-join") && !player.hasPlayedBefore())
			messages = ConfigManager.getStringList("first-join-messages");
		else
			messages = ConfigManager.getStringList("messages");

		ImageMessage picture_message = PictureUtil.createPictureMessage(player, messages);

		if (ConfigManager.getBoolean("player-only")) {
			picture_message.sendToPlayer(player);
			return;
		}

		plugin.getServer().getOnlinePlayers().forEach(picture_message :: sendToPlayer);
	}
}
