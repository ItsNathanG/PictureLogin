package me.itsnathang.picturelogin.config;

import me.itsnathang.picturelogin.PictureLogin;

import java.io.File;

public class FallbackPicture {
    private final PictureLogin plugin;

    public FallbackPicture(PictureLogin plugin) {
        this.plugin = plugin;
    }

    public File get() {
        File image = new File(plugin.getDataFolder() + File.separator + "fallback.png");

        if (!image.exists())
            plugin.saveResource("fallback.png", false);

        if (plugin.getConfigManager().getBoolean("fallback", true))
            image = new File(plugin.getDataFolder() + File.separator + "fallback.png");
        else
            image = null;

        return image;
    }

}
