package uz.istart.kafedra.admin.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public final class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    public static final BigDecimal HUNDRED = new BigDecimal("100");
    public static final BigDecimal ONE = new BigDecimal("1");

    public static final BigDecimal ZERO_DOLLARS = new BigDecimal("0.00");

    public static BigDecimal asQuantity(BigDecimal bd) {
        if (isIntValue(bd))
            return bd.setScale(0, BigDecimal.ROUND_UNNECESSARY);
        else
            return bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal asDollars(BigDecimal bd) {
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal asPercent(BigDecimal decimal) {
        return asQuantity(decimal.multiply(ONE).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    private static boolean isIntValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    public static String toStringAsCurrency(BigDecimal bd) {
        NumberFormat nf = DecimalFormat.getInstance();
        DecimalFormatSymbols customSymbol = new DecimalFormatSymbols();
        customSymbol.setDecimalSeparator(',');
        customSymbol.setGroupingSeparator(' ');
        ((DecimalFormat) nf).setDecimalFormatSymbols(customSymbol);
        nf.setGroupingUsed(true);
        return nf.format(asQuantity(bd).doubleValue());
    }

}
