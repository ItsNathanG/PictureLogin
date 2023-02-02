package me.itsnathang.picturelogin.config;

import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.util.Translate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LanguageManager {
    private final PictureLogin plugin;

    LanguageManager(PictureLogin plugin) {
        this.plugin = plugin;

        reloadLanguage();
    }

    private YamlConfiguration loadLanguage() {
        File language_file = new File(plugin.getDataFolder() + File.separator + "messages.yml");

        if (!language_file.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        return YamlConfiguration.loadConfiguration(language_file);
    }

    public void reloadLanguage() {
        Translate.messages = loadLanguage();
    }

}
