package me.itsnathang.picturelogin.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import com.bobacadodl.imgmessage.ImageChar;
import me.itsnathang.picturelogin.util.ImageMessage;
import me.itsnathang.picturelogin.PictureLogin;

public class ConfigManager {
	private final PictureLogin plugin;
	private LanguageManager languageManager;
	private YamlConfiguration config;
	
	public ConfigManager(PictureLogin plugin) {
		this.plugin = plugin;

		languageManager = new LanguageManager(plugin);
		
		reloadConfig();
	}
	
	public void reloadConfig() {
		File conf = new File(plugin.getDataFolder() + File.separator + "config.yml");
		
		if(!conf.exists())
			plugin.saveResource("config.yml", true);
        
        config = YamlConfiguration.loadConfiguration(conf);

		languageManager.reloadLanguage();
	}
	
	private char getChar() {
		try {
			return ImageChar.valueOf(config.getString("character").toUpperCase()).getChar();
		} catch (IllegalArgumentException e) {
			return ImageChar.BLOCK.getChar();
		}
	}
	
	public ImageMessage getMessage(List<String> messages, BufferedImage image) {
		int imageDimensions = 8, count = 0;
		ImageMessage imageMessage = new ImageMessage(image, imageDimensions, getChar());
		String[] msg = new String[imageDimensions];

		for (String message : messages) {
			if (count > msg.length) break;
			msg[count++] = message;
		}

		while (count < imageDimensions) {
			msg[count++] = "";
		}

		if (config.getBoolean("center-text", false))
			return imageMessage.appendCenteredText(msg);

		return imageMessage.appendText(msg);
	}

	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	public boolean getBoolean(String key, Boolean def) { return config.getBoolean(key, def); }

	public List<String> getStringList(String key) {
		return config.getStringList(key);
	}

	public String getURL() {
		String url = config.getString("url");

		if (url == null) {
			plugin.getLogger().log(Level.SEVERE, "Could not read picture url from config.yml!");

			return "https://minepic.org/avatar/8/%uuid%";
		}

		return url;
	}
	
}
