package uz.istart.kafedra.admin.utils;


import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Xurshidbek on 08.01.2016.
 */
public class R {

    public static URL getURL(String name) {
        return R.class.getResource(name);
    }

    public static ResourceBundle bundle(Locale locale) {
        return ResourceBundle.getBundle("messages", locale, new UTF8Control());
    }

    public static ResourceBundle bundle(String langCode) {
        Locale locale = new Locale(StringUtils.isEmpty(langCode) ? "ru" : langCode);
        return ResourceBundle.getBundle("messages", locale, new UTF8Control());
    }

    public static ResourceBundle bundle() {
        return ResourceBundle.getBundle("messages", new Locale("ru"), new UTF8Control());
    }

    public static String getString(Locale locale, String key) {
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale, new UTF8Control());

            return bundle.getString(key);

        } catch (Exception ex) {
            return key;
        }
    }


}
