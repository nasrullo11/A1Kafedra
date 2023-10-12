package uz.istart.kafedra.admin.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Saidolim on 28.05.15.
 */
public class Converters {

    public static final int DECIMALS = 10000;
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SPACE = " ";
    public static final String EMPTY = "";

    /**
     * Converts String type float value into Float.
     * Before convert, replaves all commas (,) to dots (.)
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return float value of result
     */
    public static Float strToFloat(String value, Float defaultValue) {
        try {
            String valueWithDot = value.replaceAll(COMMA, DOT);
            valueWithDot = valueWithDot.replaceAll(SPACE, EMPTY);
            Float f = Float.valueOf(valueWithDot);
            return f;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * Converts String type double value into Double.
     * Before convert, replaves all commas (,) to dots (.)
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return double value of result
     */
    public static Double strToDouble(String value, Double defaultValue) {
        try {
            String valueWithDot = value.replaceAll(COMMA, DOT);
            valueWithDot = valueWithDot.replaceAll(SPACE, EMPTY);
            Double f = Double.valueOf(valueWithDot);
            return f;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * Converts String type bigdecimal value into BigDecimal.
     * Before convert, replaves all commas (,) to dots (.)
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return bigdecimal value of result
     */
    public static BigDecimal strToBigDecimal(String value, BigDecimal defaultValue) {
        try {
            String valueWithDot = value.replaceAll(COMMA, DOT);
            valueWithDot = valueWithDot.replaceAll(SPACE, EMPTY);
            BigDecimal f = new BigDecimal(valueWithDot);
            return f;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * Converts String type int value into Integer.
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return Integer value of result
     */
    public static Integer strToInt(String value, Integer defaultValue) {
        try {
            String valueWithDot = value.replaceAll(SPACE, EMPTY);
            Integer v = Integer.valueOf(valueWithDot);
            return v;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * Converts String type int value into Long.
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return Long value of result
     */
    public static Long strToLong(String value, Long defaultValue) {
        try {
            String valueWithDot = value.replaceAll(SPACE, EMPTY);
            Long v = Long.valueOf(valueWithDot);
            return v;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * Converts String type timestamp value into Timestamp.
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return Timestamp value of result
     */
    public static Timestamp strToTimestamp(String value, Timestamp defaultValue) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(value);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            return timestamp;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static List<Integer> strToInt(String[] values, Integer defaultValue) {
        List<Integer> result = new ArrayList<>();
        for (String value : values) {
            result.add(strToInt(value, defaultValue));
        }
        return result;
    }

    public static Integer objToInt(Object obj, Integer defaultValue) {
        if (obj == null) return defaultValue;
        return strToInt(obj.toString(), defaultValue);
    }

    /**
     * Converts String type bigdecimal value into BigDecimal.
     * Before convert, replaves all commas (,) to dots (.)
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return bigdecimal value of result
     */
    public static BigDecimal objToBigDecimal(Object obj, BigDecimal defaultValue) {
        if (obj == null) return defaultValue;
        return strToBigDecimal(obj.toString(), defaultValue);
    }

    public static String objToStr(Object obj) {
        if (obj == null) return null;
        return obj.toString();
    }

    public static <T extends Enum> T strToEnum(Class<T> aClass, String value) {
        return strToEnum(aClass, value, null);
    }

    public static <T extends Enum> T strToEnum(Class<T> aClass, String value, T defaultValue) {
        try {
            for (T anEnum : aClass.getEnumConstants()) {
                if (anEnum.name().equals(value))
                    return anEnum;
            }
            return defaultValue;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * @param in
     * @param position
     * @return
     */
    public static int getBitValue(int in, int position) {
        return (in >> position) & 1;
    }

    /**
     * @param in
     * @param position
     * @param value
     * @return
     */
    public static int setBitValue(int in, int position, int value) {
        if (position > 0 && position < 31) {
            if (value == 0) {
                return in & (0xBFFFFFFD >> (30 - position));
            } else {
                return in | (1 << (position));
            }
        }
        return in;
    }

    public static <T> T orElse(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}

