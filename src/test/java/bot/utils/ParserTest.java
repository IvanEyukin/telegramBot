package bot.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;


public class ParserTest {

    @Test
    public void calculationNumberFromStringTest_replaceComma() {
        String number = "200,5";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("200.5"), result);
    }

    @Test
    public void calculationNumberFromStringTest_replaceDot() {
        String number = "200.5";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("200.5"), result);
    }

    @Test
    public void calculationNumberFromStringTest_integer() {
        String number = "200";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("200"), result);
    }

    @Test
    public void calculationNumberFromStringTest_addition() {
        String number = "200+100";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("300"), result);
    }

    @Test
    public void calculationNumberFromStringTest_subtraction() {
        String number = "200-100";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("100"), result);
    }

    @Test
    public void calculationNumberFromStringTest_division() {
        String number = "200/100";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("2.00"), result);
    }

    @Test
    public void calculationNumberFromStringTest_multiplication() {
        String number = "200*100";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("20000"), result);
    }

    @Test
    public void calculationNumberFromStringTest_mathematicalOperations1() {
        String number = "10/2+5*2-10";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("5.00"), result);
    }

    @Test
    public void calculationNumberFromStringTest_mathematicalOperations2() {
        String number = "100-50/5+20*3";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("150.00"), result);
    }

    @Test
    public void calculationNumberFromStringTest_subtraction_zero_error() {
        String number = "150-50-50";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("50.00"), result);
    }

    @Test
    public void parseTimetoIntegerTest_splitFirst() {
        String time = "10 05 00";
        int number = 0;
        Integer result = Parser.parseTimetoInteger(time, number);
        assertEquals((Integer) 10, result);
    }

    @Test
    public void parseTimetoIntegerTest_splitSecond() {
        String time = "1005 05";
        int number = 1;
        Integer result = Parser.parseTimetoInteger(time, number);
        assertEquals((Integer) 5, result);
    }

    @Test
    public void parseTimetoIntegerTest_splitThird() {
        String time = "10 05 11";
        int number = 2;
        Integer result = Parser.parseTimetoInteger(time, number);
        assertEquals((Integer) 11, result);
    }

    @Test
    public void checkAndConcatFirstCharTest_addPlus() {
        String number = "200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals("+200+300+500", result);
    }

    @Test
    public void checkAndConcatFirstCharTest_minusNoChange() {
        String number = "-200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals(number, result);
    }

    @Test
    public void checkAndConcatFirstCharTest_plusNoChange() {
        String number = "+200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals(number, result);
    }

    @Test
    public void checkAndConcatFirstCharTest_multiplyNoChange() {
        String number = "*200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals(number, result);
    }

    @Test
    public void checkAndConcatFirstCharTest_divideNoChange() {
        String number = "/200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals(number, result);
    }
}