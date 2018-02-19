package me.itsnathang.picturelogin.listeners;

import com.bobacadodl.imgmessage.ImageMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.util.PictureUtil;

public class QuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (!ConfigManager.getBoolean("show-leave-message", true))
			return;

		Player player = event.getPlayer();
		
		if(!player.hasPermission("picturelogin.show") && ConfigManager.getBoolean("require-permission", true))
			return;
		
		if (ConfigManager.getBoolean("block-leave-message", true))
			event.setQuitMessage(null);

		ImageMessage picture_message = PictureUtil.createPictureMessage(player, ConfigManager.getStringList("leave-messages"));

		if (picture_message == null) return;

		PictureUtil.sendOutPictureMessage(picture_message);
	}

}
