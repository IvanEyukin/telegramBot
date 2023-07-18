package bot.utils;

import org.apache.commons.lang3.math.NumberUtils;
import java.math.BigDecimal;


public class Parser {
    public static final String regNumber = "((-|\\+|\\*|/)?[0-9]+(\\.|\\,[0-9]+)?)+";

    private static String replaceComma(String number) {
        if (number.contains(",")) {
           return number.replace(",", ".");
        } else {
            return number;
        }
    }

    private static String deleteFirstChar(String number) {
        String firstChar = number.substring(0, 1);

        if (firstChar.equals("-") || firstChar.equals("+") || firstChar.equals("*") || firstChar.equals("/")) {
            number = number.substring(1);
        }
        return number;
    }

    private static BigDecimal calculation(String numbers) {
        BigDecimal result = new BigDecimal("0"); 
        BigDecimal numberFirst = new BigDecimal("0"); 
        BigDecimal numberSecond = new BigDecimal("0"); 

        if (numbers.contains("+")) {
            String[] number = numbers.split("\\+", 2);
            numberFirst = NumberUtils.isCreatable(number[0]) == true ? new BigDecimal(number[0]) : calculation(number[0]);
            numberSecond = NumberUtils.isCreatable(number[1]) == true ? new BigDecimal(number[1]) : calculation(number[1]);
            result = numberFirst.add(numberSecond);
        } else if (numbers.contains("-")) {
            String[] number = numbers.split("\\-", 2);
            numberFirst = NumberUtils.isCreatable(number[0]) == true ? new BigDecimal(number[0]) : calculation(number[0]);
            numberSecond = NumberUtils.isCreatable(number[1]) == true ? new BigDecimal(number[1]) : calculation(number[1]);
            result = numberFirst.subtract(numberSecond);
        } else if (numbers.contains("*")) {
            String[] number = numbers.split("\\*", 2);
            numberFirst = NumberUtils.isCreatable(number[0]) == true ? new BigDecimal(number[0]) : calculation(number[0]);
            numberSecond = NumberUtils.isCreatable(number[1]) == true ? new BigDecimal(number[1]) : calculation(number[1]);
            result = numberFirst.multiply(numberSecond);
        } else if (numbers.contains("/")) {
            String[] number = numbers.split("\\/", 2);
            numberFirst = NumberUtils.isCreatable(number[0]) == true ? new BigDecimal(number[0]) : calculation(number[0]);
            numberSecond = NumberUtils.isCreatable(number[1]) == true ? new BigDecimal(number[1]) : calculation(number[1]);
            result = numberFirst.divide(numberSecond);
        }
        return result;
    }

    public static BigDecimal calculationNumberFromString(String number) {
        number = replaceComma(number);
        number = deleteFirstChar(number);
        BigDecimal result = NumberUtils.isCreatable(number) == true ? new BigDecimal(number) : calculation(number);
        return result;
    }

    public static Integer parseTimetoInteger (String time, int number) {
        Integer timeElement = Integer.parseInt(time.split(" ")[number]);
        return timeElement;
    }
}