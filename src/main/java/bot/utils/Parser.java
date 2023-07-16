package bot.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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

    private static List<String> splitString(String number, String mathematicalOperation) {
        List<String> result = new ArrayList<String>();
        
        String[] strings = number.split(mathematicalOperation);
        for (String string : strings) {
            result.add(string);
        }
        return result;
    }

    private static BigDecimal addition(List<String> numbers) {
        BigDecimal result = new BigDecimal("0");

        for (String number : numbers) {
            result = result.add(new BigDecimal(number));
        }
        return result;
    }

    private static BigDecimal subtraction(List<String> numbers) {
        BigDecimal result = new BigDecimal("0");

        for (String number : numbers) {
            if (result.compareTo(new BigDecimal("0")) == 0) {
                result = result.subtract(new BigDecimal(number)).abs();
            } else {
                result = result.subtract(new BigDecimal(number));
            }
        }
        return result;
    }

    private static BigDecimal multiplication(List<String> numbers) {
        BigDecimal result = new BigDecimal("0");

        for (String number : numbers) {
            if (result.compareTo(new BigDecimal("0")) == 0) {
                result = result.add(new BigDecimal(number));
            } else {
                result = result.multiply(new BigDecimal(number));
            }
        }
        return result;
    }

    private static BigDecimal division(List<String> numbers) {
        BigDecimal result = new BigDecimal("0");

        for (String number : numbers) {
            if (result.compareTo(new BigDecimal("0")) == 0) {
                result = result.add(new BigDecimal(number));
            } else {
                result = result.divide(new BigDecimal(number));
            }
        }
        return result;
    }

    public static BigDecimal parseIntToString(String number) {
        BigDecimal result = new BigDecimal("0");
        number = replaceComma(number);
        number = deleteFirstChar(number);

        if (number.contains("+")) {
            List<String> numbersPlus = splitString(number, "\\+");
            for (String plus : numbersPlus) {

                if (plus.contains("-")) {
                    List<String> numbersMinus = splitString(plus, "\\-");
                    for (String minus : numbersMinus) {

                        if (minus.contains("*")) {
                            List<String> numbersMultiplication = splitString(minus, "\\*");
                            for (String multiply : numbersMultiplication) {

                                if (multiply.contains("/")) {
                                    numbersMultiplication.set(numbersMultiplication.indexOf(multiply), division(splitString(multiply, "\\/")).toEngineeringString());
                                }
                            }
                            numbersMinus.set(numbersMinus.indexOf(minus), multiplication(numbersMultiplication).toEngineeringString());
                        }
                    }
                    numbersPlus.set(numbersPlus.indexOf(plus), subtraction(numbersMinus).toEngineeringString());
                } else if (plus.contains("*")) {
                    List<String> numbersMultiplication = splitString(plus, "\\*");
                    for (String multiply : numbersMultiplication) {

                        if (multiply.contains("/")) {
                            numbersMultiplication.set(numbersMultiplication.indexOf(multiply), division(splitString(multiply, "\\/")).toEngineeringString());
                        }
                    }
                    numbersPlus.set(numbersPlus.indexOf(plus), multiplication(numbersMultiplication).toEngineeringString());
                } else if (plus.contains("/")) {
                    numbersPlus.set(numbersPlus.indexOf(plus), division(splitString(plus, "\\/")).toEngineeringString());
                }
            }
            result = addition(numbersPlus);
        } else if (number.contains("-")) {
            List<String> numbersMinus = splitString(number, "\\-");
            for (String minus : numbersMinus) {

                if (minus.contains("*")) {
                    List<String> numbersMultiplication = splitString(minus, "\\*");
                    for (String multiply : numbersMultiplication) {

                        if (multiply.contains("/")) {
                            numbersMultiplication.set(numbersMultiplication.indexOf(multiply), division(splitString(multiply, "\\/")).toEngineeringString());
                        }
                    }
                    numbersMinus.set(numbersMinus.indexOf(minus), multiplication(numbersMultiplication).toEngineeringString());
                } else if (minus.contains("/")) {
                    numbersMinus.set(numbersMinus.indexOf(minus), division(splitString(minus, "\\/")).toEngineeringString());
                }
            }
            result = subtraction(numbersMinus);
        } else if (number.contains("*")) {
            List<String> numbersMultiplication = splitString(number, "\\*");
            for (String multiply : numbersMultiplication) {

                if (multiply.contains("/")) {
                    numbersMultiplication.set(numbersMultiplication.indexOf(multiply), division(splitString(multiply, "\\/")).toEngineeringString());
                }
            }
            result = multiplication(numbersMultiplication);
        } else if (number.contains("/")) {
            result = division(splitString(number, "\\/"));
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