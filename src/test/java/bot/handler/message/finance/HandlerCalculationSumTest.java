package bot.handler.message.finance;

import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.keyboard.inline.InLineKeyboard;
import bot.message.Finance;
import bot.state.State;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;


public class HandlerCalculationSumTest {
    
    private Bot bot = new Bot();

    @Test
    public void getCalculationTest_numberNegative() {
        bot.setState(State.WaitingSum);
        bot.setUserMessageText("-100");
        HandlerCalculationSum handlerCalculationSum = new HandlerCalculationSum(bot);
        bot = handlerCalculationSum.getCalculation();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(null, bot.getSum());
        assertEquals(Finance.NUMBER_NEGATIVE, bot.getMessages().get(0).getText());
    }

    @Test
    public void getCalculationTest_numberZero() {
        bot.setState(State.WaitingSum);
        bot.setUserMessageText("0");
        HandlerCalculationSum handlerCalculationSum = new HandlerCalculationSum(bot);
        bot = handlerCalculationSum.getCalculation();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(null, bot.getSum());
        assertEquals(Finance.NUMBER_ZERO, bot.getMessages().get(0).getText());
    }    

    @Test
    public void getCalculationTest_setSum() {
        bot.setState(State.WaitingSum);
        bot.setUserMessageText("100");
        HandlerCalculationSum handlerCalculationSum = new HandlerCalculationSum(bot);
        bot = handlerCalculationSum.getCalculation();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(new BigDecimal("100"), bot.getSum());
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.finance), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(String.format(Finance.SAVE_QUESTION, "100"), bot.getMessages().get(0).getText());
    }

    @Test
    public void parseNumberTest_addNumberPlus() {
        bot.setState(State.WaitingSum);
        bot.setPreviousState(State.WaitCallbackSaveOrDelete);
        bot.setSum(new BigDecimal("200"));
        bot.setUserMessageText("100");
        HandlerCalculationSum handlerCalculationSum = new HandlerCalculationSum(bot);
        bot = handlerCalculationSum.getCalculation();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(new BigDecimal("300"), bot.getSum());
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.finance), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(String.format(Finance.SAVE_QUESTION, "300"), bot.getMessages().get(0).getText());
    }

    @Test
    public void parseNumberTest_addNumberMinus() {
        bot.setState(State.WaitingSum);
        bot.setPreviousState(State.WaitCallbackSaveOrDelete);
        bot.setSum(new BigDecimal("200"));
        bot.setUserMessageText("-100+50");
        HandlerCalculationSum handlerCalculationSum = new HandlerCalculationSum(bot);
        bot = handlerCalculationSum.getCalculation();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(new BigDecimal("150"), bot.getSum());
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.finance), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(String.format(Finance.SAVE_QUESTION, "150"), bot.getMessages().get(0).getText());
    }
}