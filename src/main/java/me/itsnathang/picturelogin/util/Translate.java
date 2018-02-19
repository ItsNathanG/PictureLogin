package me.itsnathang.picturelogin.util;

import me.itsnathang.picturelogin.config.LanguageManager;

public class Translate {

    public static String translate(String key) {
        return LanguageManager.getFilteredTranslation(key);
    }

    public static String tl(String key) {
        return translate(key);
    }

}
