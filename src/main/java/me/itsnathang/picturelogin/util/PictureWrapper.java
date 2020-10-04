package me.itsnathang.picturelogin.util;

import me.itsnathang.picturelogin.util.ImageMessage;
import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.config.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PictureWrapper extends BukkitRunnable {
    private PictureUtil pictureUtil;
    private ConfigManager config;
    private Player player;

    public PictureWrapper(PictureLogin plugin, Player player) {
        this.pictureUtil = plugin.getPictureUtil();
        this.config      = plugin.getConfigManager();
        this.player      = player;
    }

    @Override
    public void run() {
        sendImage();
    }

    private boolean checkPermission() {
        if (!config.getBoolean("require-permission", true))
            return true;

        return player.hasPermission("picturelogin.show");
    }

    private ImageMessage getMessage() {
        String msgType;

        // if it's a player's first time and feature is enabled, show different message
        if (config.getBoolean("show-first-join", true) && !player.hasPlayedBefore())
            msgType = "first-join-messages";
        else
            msgType = "messages";

        return pictureUtil.createPictureMessage(player, config.getStringList(msgType));
    }

    private void sendImage() {
        // only show message for players with picturelogin.show permission
        if(!checkPermission()) return;

        ImageMessage pictureMessage = getMessage();

        if (pictureMessage == null) return;

        // send only to the player that joined?
        if (config.getBoolean("player-only", true)) {
            if (config.getBoolean("clear-chat", false))
                pictureUtil.clearChat(player);

            pictureMessage.sendToPlayer(player);
            return;
        }

        pictureUtil.sendOutPictureMessage(pictureMessage);
    }

}
