package ua.study.command.util.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by dima on 06.05.17.
 */
public class Localization {

    private static final String BUNDLE = "messages";

    public String getMessage(String key, Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, locale);
        return resourceBundle.getString(key);
    }
}
