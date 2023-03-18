package me.itsnathang.picturelogin.util;

import de.themoep.minedown.MineDown;
import me.itsnathang.picturelogin.PictureLogin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

public class ImageMessage {
    private final static char TRANSPARENT_CHAR = ' ';
    private String[] lines; // The 8 lines next to the playerhead

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

        double newWidth = width / (double) originalImage.getWidth();
        double newHeight = height / (double) originalImage.getHeight();

        af.scale(newWidth, newHeight);

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
                    line.append("&").append(colorToHex(value[y])).append("&").append(imgchar);
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
                lines[y] += centerMessage(text[y]);
            } else {
                return this;
            }
        }
        return this;
    }

    /*
    Credit to https://www.spigotmc.org/members/sirspoodles.109063/ for this method
    https://www.spigotmc.org/threads/free-code-sending-perfectly-centered-chat-message.95872/
     */
    public String centerMessage(String message) {
        if (message == null || message.equals("")) {
            message = "";
        }

        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                var dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int CENTER_PX = 154;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        int offset = 8; // account for the player head (8 wide)
        for (int j = 0; j < offset; j++) {
            int anIndex = sb.length() - 1;
            if (anIndex >= 0) {
                sb.deleteCharAt(anIndex);
            }
        }

        return sb + message;
    }

    public void sendToPlayer(Player player) {
        for (String line : lines) {
            player.spigot().sendMessage(MineDown.parse(line));
        }
    }

}
