package me.itsnathang.picturelogin.util;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import me.itsnathang.picturelogin.PictureLogin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PictureUtil {
	private static PictureLogin plugin;
	
	public PictureUtil(PictureLogin  p) {
		plugin = p;
	}
	
	public static URL newURL(Player player) throws Exception {
		String url = plugin.getConfig().getString("url");
		url = url.replace("%pname%", player.getName()).replace("%uuid%", player.getUniqueId().toString());
		return new URL(url);
	}
	
	public static BufferedImage getImage(Player player) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(newURL(player));
		} catch (Exception e) {
			Bukkit.getLogger().warning("[PictureLogin] Could not retrieve avatar from URL. Please check your config file.");
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
		return m;
	}
	
}
