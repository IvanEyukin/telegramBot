package bot.handler.message.finance;

import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.keyboard.inline.InLineKeyboard;
import bot.keyboard.reply.ReplyKeyboard;
import bot.message.Finance;
import bot.state.State;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;


public class HandlerIncomeTest {

    private Bot bot = new Bot();

    @Test
    public void financeProcessTest_getMenu() {
        bot.setState(State.Start);
        HandlerIncome handlerIncome = new HandlerIncome(bot);
        bot = handlerIncome.financeProcess();
        assertEquals(State.IncomeMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.INCOME), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Finance.INCOME, bot.getMessages().get(0).getText());
    }

    @Test
    public void financeProcessTest_setSubCategory() {
        bot.setState(State.IncomeMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.INCOME.get(0));
        HandlerIncome handlerIncome = new HandlerIncome(bot);
        bot = handlerIncome.financeProcess();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(Keyboard.replyKeyboard.INCOME.get(0), bot.getSubCategory());
        assertEquals(Finance.NUMBER.concat(Keyboard.replyKeyboard.INCOME.get(0)), bot.getMessages().get(0).getText());
    }
    
    @Test
    public void financeProcessTest_setSubCategoryOther() {
        bot.setState(State.IncomeMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.INCOME.get(3));
        HandlerIncome handlerIncome = new HandlerIncome(bot);
        bot = handlerIncome.financeProcess();
        assertEquals(State.WaitingComment, bot.getState());
        assertEquals(Keyboard.replyKeyboard.INCOME.get(3), bot.getSubCategory());
        assertEquals(Finance.SAVE_OTHER, bot.getMessages().get(0).getText());
    }

    @Test
    public void financeProcessTest_setComment() {
        bot.setState(State.WaitingComment);
        bot.setSubCategory(Keyboard.replyKeyboard.INCOME.get(3));
        bot.setUserMessageText("Test");
        HandlerIncome handlerIncome = new HandlerIncome(bot);
        bot = handlerIncome.financeProcess();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(Keyboard.replyKeyboard.INCOME.get(3), bot.getSubCategory());
        assertEquals("Test", bot.getComment());
        assertEquals(Finance.NUMBER.concat(Keyboard.replyKeyboard.INCOME.get(3)), bot.getMessages().get(0).getText());
    }

    @Test
    public void financeProcessTest_setSum() {
        bot.setState(State.WaitingSum);
        bot.setSubCategory(Keyboard.replyKeyboard.INCOME.get(3));
        bot.setUserMessageText("100");
        HandlerIncome handlerIncome = new HandlerIncome(bot);
        bot = handlerIncome.financeProcess();
        assertEquals(State.WaitCallbackSaveOrDelete, bot.getState());
        assertEquals(Keyboard.replyKeyboard.INCOME.get(3), bot.getSubCategory());
        assertEquals(new BigDecimal("100"), bot.getSum());
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.finance), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(String.format(Finance.SAVE_QUESTION, "100"), bot.getMessages().get(0).getText());
    }
    
    @Test
    public void financeProcessTest_setSumError() {
        bot.setState(State.WaitingSum);
        bot.setSubCategory(Keyboard.replyKeyboard.INCOME.get(3));
        bot.setUserMessageText("Test");
        HandlerIncome handlerIncome = new HandlerIncome(bot);
        bot = handlerIncome.financeProcess();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(Keyboard.replyKeyboard.INCOME.get(3), bot.getSubCategory());
        assertEquals(Finance.NUMBER_ERROR, bot.getMessages().get(0).getText());
    }

    @Test
    public void financeProcessTest_error() {
        bot.setState(State.IncomeMenu);
        bot.setSubCategory(null);
        bot.setUserMessageText("100");
        HandlerIncome handlerIncome = new HandlerIncome(bot);
        bot = handlerIncome.financeProcess();
        assertEquals(State.IncomeMenu, bot.getState());
        assertEquals(null, bot.getSum());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.INCOME), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Finance.CATEGORY_ERROR, bot.getMessages().get(0).getText());
    }
}