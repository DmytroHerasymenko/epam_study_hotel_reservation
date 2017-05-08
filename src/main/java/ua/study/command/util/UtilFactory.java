package ua.study.command.util;

import ua.study.command.util.localization.Localization;
import ua.study.command.util.validation.Validator;

/**
 * Created by dima on 08.05.17.
 */
public class UtilFactory {

    private static final UtilFactory instance = new UtilFactory();

    private final Validator validator = new Validator();
    private final Localization localization = new Localization();

    private UtilFactory(){}

    public static UtilFactory getInstance() {
        return instance;
    }

    public Validator getValidator() {
        return validator;
    }

    public Localization getLocalization() {
        return localization;
    }
}
