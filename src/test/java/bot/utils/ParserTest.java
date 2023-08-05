package bot.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;

public class ParserTest {

    @Test
    public void calculationNumberFromStringTest1() {
        String number = "200,5";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("200.5"), result);
    }
    @Test
    public void calculationNumberFromStringTest2() {
        String number = "200.5";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("200.5"), result);
    }
    @Test
    public void calculationNumberFromStringTest3() {
        String number = "200";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("200"), result);
    }
    @Test
    public void calculationNumberFromStringTest4() {
        String number = "200+100";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("300"), result);
    }
    @Test
    public void calculationNumberFromStringTest5() {
        String number = "200-100";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("100"), result);
    }
    @Test
    public void calculationNumberFromStringTest6() {
        String number = "200/100";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("2.00"), result);
    }
    @Test
    public void calculationNumberFromStringTest7() {
        String number = "200*100";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("20000"), result);
    }
    @Test
    public void calculationNumberFromStringTest8() {
        String number = "10/2+5*2-10";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("5.00"), result);
    }
    @Test
    public void calculationNumberFromStringTest9() {
        String number = "100-50/5+20*3";
        BigDecimal result = Parser.calculationNumberFromString(number);
        assertEquals(new BigDecimal("150.00"), result);
    }

    @Test
    public void parseTimetoIntegerTest1() {
        String time = "10 05 00";
        int number = 0;
        Integer result = Parser.parseTimetoInteger(time, number);
        assertEquals((Integer) 10, result);
    }
    @Test
    public void parseTimetoIntegerTest2() {
        String time = "1005 05";
        int number = 1;
        Integer result = Parser.parseTimetoInteger(time, number);
        assertEquals((Integer) 5, result);
    }
    @Test
    public void parseTimetoIntegerTest3() {
        String time = "10 05 11";
        int number = 2;
        Integer result = Parser.parseTimetoInteger(time, number);
        assertEquals((Integer) 11, result);
    }

    @Test
    public void checkAndConcatFirstCharTest1() {
        String number = "200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals("+200+300+500", result);
    }
    @Test
    public void checkAndConcatFirstCharTest2() {
        String number = "-200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals(number, result);
    }
    @Test
    public void checkAndConcatFirstCharTest3() {
        String number = "+200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals(number, result);
    }
    @Test
    public void checkAndConcatFirstCharTest4() {
        String number = "*200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals(number, result);
    }
    @Test
    public void checkAndConcatFirstCharTest5() {
        String number = "/200+300+500";
        String result = Parser.checkAndConcatFirstChar(number);
        assertEquals(number, result);
    }
}