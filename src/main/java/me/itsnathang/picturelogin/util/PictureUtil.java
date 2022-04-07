package me.itsnathang.picturelogin.util;

import static me.itsnathang.picturelogin.util.Translate.tl;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;

import me.itsnathang.picturelogin.util.ImageMessage;
import me.clip.placeholderapi.PlaceholderAPI;
import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.config.FallbackPicture;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PictureUtil {
    private final PictureLogin plugin;
    private final ConfigManager config;

    public PictureUtil(PictureLogin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
    }

    private URL newURL(String player_uuid, String player_name) {
        String url = config.getURL()
                .replace("%uuid%", player_uuid)
                .replace("%pname%", player_name);
        try {
            return new URL(url);
        } catch (Exception e) {
            plugin.getLogger().warning("Could not read url from file.");
            return null;
        }
    }

    private BufferedImage getImage(Player player) {
        URL head_image = newURL(player.getUniqueId().toString(), player.getName());

        // URL Formatted correctly.
        if (head_image != null) {
            try {
                //User-Agent is needed for HTTP requests
                HttpURLConnection connection = (HttpURLConnection) head_image.openConnection();
                connection.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                return ImageIO.read(connection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                plugin.getLogger().warning(tl("error_retrieving_avatar"));
            }
        }

        // Incorrectly formatted URL or couldn't load from URL
        try {
            return ImageIO.read(new FallbackPicture(plugin).get());
        } catch (Exception e) {
            plugin.getLogger().warning(tl("error_fallback_img"));
            return null;
        }
    }

    public ImageMessage createPictureMessage(Player player, List<String> messages) {
        BufferedImage image = getImage(player);

        if (image == null) {
            return null;
        }

        messages.replaceAll((message) -> addPlaceholders(message, player));

        return config.getMessage(messages, image);
    }

    public void sendOutPictureMessage(ImageMessage picture_message) {
        plugin.getServer().getOnlinePlayers().forEach((online_player) -> {
            if (config.getBoolean("clear-chat", false))
                clearChat(online_player);

            picture_message.sendToPlayer(online_player);
        });
    }

    /*
    Here be String utility methods
     */

    private String addPlaceholders(String msg, Player player) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);

        msg = msg.replace("%pname%", player.getName());
        msg = msg.replace("%uuid%", player.getUniqueId().toString());
        msg = msg.replace("%online%", String.valueOf(plugin.getServer().getOnlinePlayers().size()));
        msg = msg.replace("%max%", String.valueOf(plugin.getServer().getMaxPlayers()));
        msg = msg.replace("%motd%", plugin.getServer().getMotd());
        msg = msg.replace("%displayname%", player.getDisplayName());

        if (Hooks.PLACEHOLDER_API) {
            msg = PlaceholderAPI.setPlaceholders(player, msg);
        }

        return msg;
    }

    public void clearChat(Player player) {
        for (int i = 0; i < 20; i++) {
            player.sendMessage("");
        }
    }

}
