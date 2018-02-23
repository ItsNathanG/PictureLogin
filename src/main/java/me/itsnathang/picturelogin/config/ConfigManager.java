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
	private final PictureLogin plugin;
	private YamlConfiguration config;
	
	public ConfigManager(PictureLogin plugin) {
		this.plugin = plugin;

		new LanguageManager(plugin);
		new FallbackPicture(plugin);
		
		reloadConfig();
	}
	
	public void reloadConfig() {
		File conf = new File(plugin.getDataFolder() + File.separator + "config.yml");
		
		if(!conf.exists())
			plugin.saveResource("config.yml", true);
        
        config = YamlConfiguration.loadConfiguration(conf);

	}
	
	private char getChar() {
		try {
			return ImageChar.valueOf(config.getString("character").toUpperCase()).getChar();
		} catch (IllegalArgumentException e) {
			return ImageChar.BLOCK.getChar();
		}
	}
	
	public ImageMessage getMessage(List<String> messages, BufferedImage image) {
		ImageMessage imageMessage = new ImageMessage(image, 8, getChar());
		String[] msg = new String[messages.size()];

		if (config.getBoolean("center-text", false))
			return imageMessage.appendCenteredText(messages.toArray(msg));

		return imageMessage.appendText(messages.toArray(msg));
	}

	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	public boolean getBoolean(String key, Boolean def) { return config.getBoolean(key, def); }

	public List<String> getStringList(String key) {
		return config.getStringList(key);
	}

	public String getURL() { return config.getString("url"); }
	
}
