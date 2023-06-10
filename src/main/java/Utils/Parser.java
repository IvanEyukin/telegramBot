package Utils;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.FileReader;
import java.io.IOException;
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

    public static JSONObject parseJsonFile(String fileName) throws JSONException, IOException {

        JSONTokener jsonToken = new JSONTokener(new FileReader(fileName));
        JSONObject jsonFile = new JSONObject(jsonToken);

        return jsonFile;

    }

    public static JSONArray parseJsonArray(JSONObject jsonArray, String key) throws JSONException {

        JSONArray resultList = jsonArray.getJSONArray(key);
        return resultList;

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

}
