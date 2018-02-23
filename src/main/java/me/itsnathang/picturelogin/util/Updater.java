package me.itsnathang.picturelogin.util;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import static me.itsnathang.picturelogin.util.Translate.tl;

public class Updater {

     public Updater(Logger log, String current_version) {
        final String USER_AGENT = "PictureLogin Plugin";
        final String PLUGIN_ID = "4514";

        try {
            // Connect to SpiGet
            URL url = new URL("https://api.spiget.org/v2/resources/" + PLUGIN_ID + "/versions/latest");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", USER_AGENT);// Set User-Agent
            // Read downloaded file
            JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();

            String latest_version = jsonObject.get("name").toString().replace("\"", "");

            // Compare current plugin version with downloaded one
            if (!current_version.equalsIgnoreCase(latest_version)) {
                log.info(tl("update_available").replace("%current%", current_version).replace("%new%", latest_version));
                log.info(tl("update_available_download"));
            }

        } catch (Exception e) {
            log.warning(tl("error_update_check"));
        }
    }

}
