package me.itsnathang.picturelogin.util;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import me.clip.placeholderapi.PlaceholderAPI;
import me.itsnathang.picturelogin.PictureLogin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static me.itsnathang.picturelogin.util.Translate.tl;

public class PictureUtil {
	private static PictureLogin plugin;
	private static boolean placeholder_api_enabled;
	
	public PictureUtil(PictureLogin p, Boolean placeholder_api) {
		plugin = p;
		placeholder_api_enabled = placeholder_api;
	}
	
	private static URL newURL(Player player) throws Exception {
		String url = plugin.getConfig().getString("url");
		url = url.replace("%pname%", player.getName()).replace("%uuid%", player.getUniqueId().toString());
		return new URL(url);
	}
	
	public static BufferedImage getImage(Player player) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(newURL(player));
		} catch (Exception e) {
			Bukkit.getLogger().warning(tl("error_retrieving_avatar"));
		}
		
		return image;
	}
	
	public static String replaceThings(String m, Player player) {
		m = ChatColor.translateAlternateColorCodes('&', m);
		m = m.replace("%pname%", player.getName());
		m = m.replace("%uuid%", player.getUniqueId().toString());
		m = m.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
		m = m.replace("%max%", String.valueOf(Bukkit.getMaxPlayers()));
		m = m.replace("%motd%", Bukkit.getMotd());
		m = m.replace("%displayname%", player.getDisplayName());

		if (placeholder_api_enabled)
			m = PlaceholderAPI.setPlaceholders(player, m);

		return m;
	}
	
}
