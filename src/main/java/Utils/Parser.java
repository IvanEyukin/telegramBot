package Utils;

import java.math.BigDecimal;


public class Parser {

    public static final String regNumberValid = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
    public static final String regNumberNoValid = "((-|\\+)?[0-9]+(\\,[0-9]+)?)+";

    private static BigDecimal splitStringPlus(String integer) {

        BigDecimal result = new BigDecimal("0");

        String[] splitPlus = integer.split("\\+");
        for (String plus : splitPlus) {
            result = result.add(new BigDecimal(plus));
        }

        return result;

    }

    private static BigDecimal splitStringMinus(String integer) {

        BigDecimal result = new BigDecimal("0");

        String[] splitPlus = integer.split("\\-");
        for (String plus : splitPlus) {

            if (result.compareTo(new BigDecimal("0")) == 0) {
                result = result.subtract(new BigDecimal(plus)).abs();
            } else {
                result = result.subtract(new BigDecimal(plus));
            }
            
        }

        return result;

    }

    public static BigDecimal parseIntToString(String number) {

        BigDecimal result = new BigDecimal("0");

        if (number.contains(",")) {
            number.replace(",", ".");
        }

        if (number.substring(0, 1).equals("-")|| number.substring(0, 1).equals("+")) {
            number = number.substring(1);
        }

        if (number.contains("-") && number.contains("+")) {

            String[] splitPlus = number.split("\\+");
            for (String plus : splitPlus) {

                if (!plus.contains("-")) {
                    result = result.add(new BigDecimal(plus));
                } else {
                    result = result.add(splitStringMinus(plus));
                }

            }

        } else if (number.contains("-")) {
            result = result.add(splitStringMinus(number));
        } else if (number.contains("+")) {
            result = result.add(splitStringPlus(number));
        } else {
            result = result.add(new BigDecimal(number));
        }

        return result;

    }

    public static Integer parseTimetoInteger (String time, int number) {

        Integer timeElement = Integer.parseInt(time.split(" ")[number]);

        return timeElement;

    }

}