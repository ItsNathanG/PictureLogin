package me.itsnathang.picturelogin.util;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import com.bobacadodl.imgmessage.ImageMessage;
import me.clip.placeholderapi.PlaceholderAPI;
import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.config.FallbackPicture;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static me.itsnathang.picturelogin.util.Translate.tl;

public class PictureUtil {
	private static PictureLogin plugin;
	private static boolean placeholder_api;
	
	public PictureUtil(PictureLogin plugin) {
		PictureUtil.plugin = plugin;

		placeholder_api = plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
	}
	
	private static URL newURL(String player_uuid) {
		String url = ConfigManager.getURL()
				.replace("%uuid%" , player_uuid);

		try {
			return new URL(url);
		} catch (Exception e) { return null; }
	}
	
	private static BufferedImage getImage(String player_uuid) {
		URL head_image = newURL(player_uuid);

		// URL Formatted correctly.
		if (head_image != null) {
            try {
                return ImageIO.read(head_image);
            } catch (Exception e) {
                plugin.getLogger().warning(tl("error_retrieving_avatar"));
            }
		}

		// Incorrectly formatted URL or couldn't load from URL
        try {
		    return ImageIO.read(FallbackPicture.get());
        } catch (Exception e) {
		    plugin.getLogger().warning(tl("error_fallback_img"));
		    return null;
        }
	}

	public static ImageMessage createPictureMessage(Player player, List<String> messages) {
		BufferedImage image = getImage(player.getUniqueId().toString());

		if (image == null) return null;

		messages.replaceAll((message) -> replaceThings(message, player));

		return ConfigManager.getMessage(messages, image);
	}

	public static void sendOutPictureMessage(ImageMessage picture_message) {
        plugin.getServer().getOnlinePlayers().forEach((online_player) -> {
            if (ConfigManager.getBoolean("clear-chat", false))
                clearChat(online_player);

            picture_message.sendToPlayer(online_player);
        });
    }

    // String Utility Functions

	private static String replaceThings(String m, Player player) {
		m = ChatColor.translateAlternateColorCodes('&', m);
		m = m.replace("%pname%", player.getName());
		m = m.replace("%uuid%", player.getUniqueId().toString());
		m = m.replace("%online%", String.valueOf(plugin.getServer().getOnlinePlayers().size()));
		m = m.replace("%max%", String.valueOf(plugin.getServer().getMaxPlayers()));
		m = m.replace("%motd%", plugin.getServer().getMotd());
		m = m.replace("%displayname%", player.getDisplayName());

		if (placeholder_api)
			m = PlaceholderAPI.setPlaceholders(player, m);

		return m;
	}

    private static void clearChat(Player player) {
        for (int i = 0; i < 20; i++) {
            player.sendMessage("");
        }
    }
	
}
