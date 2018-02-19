package me.itsnathang.picturelogin.config;

import me.itsnathang.picturelogin.PictureLogin;

import java.io.File;

public class FallbackPicture {
    private static File fallback_image;
    private static PictureLogin plugin;

    FallbackPicture(PictureLogin plugin) {
        FallbackPicture.plugin = plugin;

        reload();
    }

    public static void reload() {
        File image = new File(plugin.getDataFolder() + File.separator + "fallback.png");

        if (!image.exists())
            plugin.saveResource("fallback.png", false);

        fallback_image = new File(plugin.getDataFolder() + File.separator + "fallback.png");
    }

    public static File get() {
        return fallback_image;
    }

}
