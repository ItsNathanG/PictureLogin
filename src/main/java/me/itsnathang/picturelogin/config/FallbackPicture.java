package me.itsnathang.picturelogin.config;

import me.itsnathang.picturelogin.PictureLogin;

import java.io.File;

public class FallbackPicture {
    private final PictureLogin plugin;

    public FallbackPicture(PictureLogin plugin) {
        this.plugin = plugin;
    }

    public File get() {
        final String FALLBACK_PATH = plugin.getDataFolder() + File.separator + "fallback.png";

        File image = new File(FALLBACK_PATH);

        if (!image.exists()) {
            plugin.saveResource("fallback.png", false);
        }

        return plugin.getConfigManager().getBoolean("fallback", true) ? new File(FALLBACK_PATH) : null;
    }

}
