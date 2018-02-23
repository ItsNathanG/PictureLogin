package me.itsnathang.picturelogin.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import me.itsnathang.picturelogin.PictureLogin;

public class ConfigManager {
	private static YamlConfiguration config;
	private static PictureLogin plugin;
	
	public ConfigManager(PictureLogin plugin) {
		ConfigManager.plugin = plugin;

		new LanguageManager(plugin);
		new FallbackPicture(plugin);
		
		reloadConfig();
	}
	
	public static void reloadConfig() {
		File conf = new File(plugin.getDataFolder() + File.separator + "config.yml");
		
		if(!conf.exists())
			plugin.saveResource("config.yml", true);
        
        config = YamlConfiguration.loadConfiguration(conf);

        FallbackPicture.reload();
	}
	
	private static char getChar() {
		try {
			return ImageChar.valueOf(config.getString("character").toUpperCase()).getChar();
		} catch (IllegalArgumentException e) {
			return ImageChar.BLOCK.getChar();
		}
	}
	
	public static ImageMessage getMessage(List<String> messages, BufferedImage image) {
		ImageMessage imageMessage = new ImageMessage(image, 8, getChar());
		String[] msg = new String[messages.size()];

		if (config.getBoolean("center-text", false))
			return imageMessage.appendCenteredText(messages.toArray(msg));

		return imageMessage.appendText(messages.toArray(msg));
	}

	public static boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	public static boolean getBoolean(String key, Boolean def) { return config.getBoolean(key, def); }

	public static List<String> getStringList(String key) {
		return config.getStringList(key);
	}

	public static String getURL() { return config.getString("url"); }
	
}
