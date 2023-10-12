package uz.istart.kafedra.admin.utils;

import java.util.*;

/**
 * Created by Xurshidbek on 04.06.2018.
 */
/*
Quyidagi ko`rinishda ishlatiladi!!!

public class TestApp {
    public static void main(String[] args) {

        ResultEnum.getList(CategoryType.class, R.bundle(new Locale("uz")));

    }
}*/

public class ResultEnum {

    //    private final static ResourceBundle configBundle = ResourceBundle.getBundle("default");
    private final static ResourceBundle configBundle = null;

    private final static String I18N_FORMAT = "enum.%s.%s"; // messages.properties dagi formati:: enum.[enum_name].[enum_constant_name]
    private final static String COLOR_FORMAT = "enum.%s.%s"; // default.properties dagi formati:: enum.[enum_name].[enum_constant_name]

    private int ordinal;
    private String name;
    private String text;    // i18n localization
    private String color;

    public ResultEnum(int ordinal, String name, String text) {
        this(ordinal, name, text, null);
    }

    public ResultEnum(int ordinal, String name, String text, String color) {
        this.ordinal = ordinal;
        this.name = name;
        this.text = text;
        this.color = color;
    }

    // Getter and Setter

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @param e
     * @param text
     * @return
     */
    public static ResultEnum create(Enum e, String text) {
        return new ResultEnum(e.ordinal(), e.name(), text);
    }

    /**
     * @param e
     * @param text
     * @param color
     * @return
     */
    public static ResultEnum create(Enum e, String text, String color) {
        return new ResultEnum(e.ordinal(), e.name(), text, color);
    }

    public static ResultEnum create(Enum anEnum, ResourceBundle bundle) {
        return create(anEnum, bundle, configBundle);
    }

    public static ResultEnum create(Enum anEnum, ResourceBundle bundle, ResourceBundle configBundle) {
        Class<? extends Enum> aClass = anEnum.getClass();

        String i18nKey = String.format(I18N_FORMAT, aClass.getSimpleName(), anEnum.name());
        String text = bundle.getString(i18nKey);

        String color = null;
        if (configBundle != null) {
            String colorKey = String.format(COLOR_FORMAT, aClass.getSimpleName(), anEnum.name());
            try {
                color = configBundle.getString(colorKey);
            } catch (Exception e) {
            }
        }

        return new ResultEnum(anEnum.ordinal(), anEnum.name(), text, color);
    }

    /**
     * berilgan Enum class ni constantalarini qaytaradi
     *
     * @param aClass - Enum class beriladi
     * @return
     */
    public static List<Enum> list(Class<? extends Enum> aClass) {
        return Arrays.asList(aClass.getEnumConstants());
    }

//    public static List<ResultEnum> list(Class<? extends Enum> aClass, Locale locale) {
//        ResourceBundle bundle = R.bundle(locale);
//        return list(aClass, bundle);
//    }

    /**
     * berilgan Enum class ni constantalarini qaytaradi,
     * i18n localization bilan birga
     *
     * @param aClass - Enum class beriladi
     * @param bundle - ResourceBundle i18n uchun
     * @return
     */
    public static List<ResultEnum> list(Class<? extends Enum> aClass, ResourceBundle bundle) {
        return list(aClass, bundle, configBundle);
    }

    public static List<ResultEnum> list(Class<? extends Enum> aClass, ResourceBundle bundle, ResourceBundle configBundle) {
        List<ResultEnum> result = new ArrayList<>();

        for (Enum anEnum : aClass.getEnumConstants()) {
            result.add(ResultEnum.create(anEnum, bundle, configBundle));
        }

        return result;
    }

    public static List<ResultEnum> list(List<? extends Enum> list, ResourceBundle bundle) {
        return list(list, bundle, configBundle);
    }

    public static List<ResultEnum> list(List<? extends Enum> list, ResourceBundle bundle, ResourceBundle configBundle) {
        List<ResultEnum> result = new ArrayList<>();

        for (Enum anEnum : list) {
            result.add(ResultEnum.create(anEnum, bundle, configBundle));
        }

        return result;
    }

    public static Map<String, ResultEnum> map(Class<? extends Enum> aClass, ResourceBundle bundle) {
        return map(aClass, bundle, configBundle);
    }

    public static Map<String, ResultEnum> map(Class<? extends Enum> aClass, ResourceBundle bundle, ResourceBundle configBundle) {
        Map<String, ResultEnum> result = new HashMap<>();

        for (Enum anEnum : aClass.getEnumConstants()) {
            result.put(anEnum.name(), ResultEnum.create(anEnum, bundle, configBundle));
        }

        return result;
    }

    public static Map<Integer, ResultEnum> mapOrdinal(Class<? extends Enum> aClass, ResourceBundle bundle) {
        return mapOrdinal(aClass, bundle, configBundle);
    }


    public static Map<Integer, ResultEnum> mapOrdinal(Class<? extends Enum> aClass, ResourceBundle bundle, ResourceBundle configBundle) {
        Map<Integer, ResultEnum> result = new HashMap<>();

        for (Enum anEnum : aClass.getEnumConstants()) {
            result.put(anEnum.ordinal(), ResultEnum.create(anEnum, bundle, configBundle));
        }

        return result;
    }

//    public static List<ResultEnum> listByFilter(Class<? extends Enum> aClass, Locale locale, List<String> filterByName) {
//        ResourceBundle bundle = R.bundle(locale);
//        return listByFilter(aClass, bundle, filterByName);
//    }

    /**
     * berilgan Enum class ni constantalarini qaytaradi,
     * i18n localization bilan birga
     *
     * @param aClass       - Enum class beriladi
     * @param bundle       - ResourceBundle i18n uchun
     * @param filterByName - Enum name bo`yicha olib chiqadi.
     * @return
     */
    public static List<ResultEnum> listByFilter(Class<? extends Enum> aClass, ResourceBundle bundle, List<String> filterByName) {
        return listByFilter(aClass, bundle, configBundle, filterByName);
    }

    public static List<ResultEnum> listByFilter(Class<? extends Enum> aClass, ResourceBundle bundle, ResourceBundle configBundle, List<String> filterByName) {
        List<ResultEnum> result = new ArrayList<>();

        for (Enum anEnum : aClass.getEnumConstants()) {

            if (filterByName.contains(anEnum.name()))
                result.add(ResultEnum.create(anEnum, bundle, configBundle));
        }

        return result;
    }
}
