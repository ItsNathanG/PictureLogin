package me.itsnathang.picturelogin.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import me.itsnathang.picturelogin.PictureLogin;

public class ConfigManager {
	static YamlConfiguration config;
	private PictureLogin plugin;
	
	public ConfigManager(PictureLogin p) {
		plugin = p;
		
		reloadConfig();
	}
	
	public void reloadConfig() {
		File conf = new File(plugin.getDataFolder() + File.separator + "config.yml");
		
		if(!conf.exists())
			plugin.saveResource("config.yml", true);
        
        config = YamlConfiguration.loadConfiguration(conf);
	}
	
	public static char getChar() {
		String character = config.getString("character");
		
		if (character.equalsIgnoreCase("block"))
			return(ImageChar.BLOCK.getChar());
		
		else if (character.equalsIgnoreCase("dark_shade"))
			return(ImageChar.DARK_SHADE.getChar());
		
		else if (character.equalsIgnoreCase("medium_shade"))
			return(ImageChar.MEDIUM_SHADE.getChar());
		
		else if (character.equalsIgnoreCase("light_shade"))
			return(ImageChar.LIGHT_SHADE.getChar());
		
		else
			return(ImageChar.BLOCK.getChar());
	}
	
	public static ImageMessage getMessage(List<String> messages, BufferedImage image) {
		if (config.getBoolean("center-text"))
			return new ImageMessage(image, 8, getChar()).appendCenteredText(messages.get(0),messages.get(1),messages.get(2),messages.get(3),messages.get(4),messages.get(5),messages.get(6),messages.get(7));
		else
			return new ImageMessage(image, 8, getChar()).appendText(messages.get(0),messages.get(1),messages.get(2),messages.get(3),messages.get(4),messages.get(5),messages.get(6),messages.get(7));
	}
	
}
