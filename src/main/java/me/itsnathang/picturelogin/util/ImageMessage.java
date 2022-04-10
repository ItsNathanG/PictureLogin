package me.itsnathang.picturelogin.util;

import de.themoep.minedown.MineDown;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageMessage {
    private final static char TRANSPARENT_CHAR = ' ';

    private String[] lines;

    public ImageMessage(BufferedImage image, int height, char imgChar) {
        Color[][] chatColors = toChatColorArray(image, height);
        lines = toImgMessage(chatColors, imgChar);
    }

    private Color[][] toChatColorArray(BufferedImage image, int height) {
        double ratio = (double) image.getHeight() / image.getWidth();
        int width = (int) (height / ratio);
        if (width > 10) width = 10;
        BufferedImage resized = resizeImage(image, width, height);

        Color[][] chatImg = new Color[resized.getWidth()][resized.getHeight()];
        for (int x = 0; x < resized.getWidth(); x++) {
            for (int y = 0; y < resized.getHeight(); y++) {
                int rgb = resized.getRGB(x, y);
                chatImg[x][y] = new Color(rgb, true);
            }
        }
        return chatImg;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        AffineTransform af = new AffineTransform();
        af.scale(
                width / (double) originalImage.getWidth(),
                height / (double) originalImage.getHeight());

        AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(originalImage, null);
    }

    private String[] toImgMessage(Color[][] colors, char imgchar) {
        lines = new String[colors[0].length];

        for (int y = 0; y < colors[0].length; y++) {
            StringBuilder line = new StringBuilder();
            for (Color[] value : colors) {
                Color color = value[y];
                // convert to minedown-styled color string
                if (color != null) {
                    line.append("&")
                            .append(colorToHex(value[y]))
                            .append("&")
                            .append(imgchar);
                } else {
                    line.append(TRANSPARENT_CHAR);
                }
            }
            lines[y] = line.toString() + ChatColor.RESET;
        }

        return lines;
    }

    private String colorToHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    public ImageMessage appendText(String... text) {
        for (int y = 0; y < lines.length; y++) {
            if (text.length > y) {
                lines[y] += " " + text[y];
            }
        }
        return this;
    }

    public ImageMessage appendCenteredText(String... text) {
        for (int y = 0; y < lines.length; y++) {
            if (text.length > y) {
                lines[y] += StringUtils.center(text[y], lines[y].length());
            } else {
                return this;
            }
        }
        return this;
    }

    public void sendToPlayer(Player player) {
        for (String line : lines) {
            player.spigot().sendMessage(MineDown.parse(line));
        }
    }
}
