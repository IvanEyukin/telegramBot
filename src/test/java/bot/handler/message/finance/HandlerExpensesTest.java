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


public class HandlerExpensesTest {

    private Bot bot = new Bot();

    @Test
    public void financeProcessTest_getMenu() {
        bot.setState(State.Start);
        HandlerExpenses HandlerExpenses = new HandlerExpenses(bot);
        bot = HandlerExpenses.financeProcess();
        assertEquals(State.ExpensesMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.EXPENSES), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Finance.EXPENSES, bot.getMessages().get(0).getText());
    }

    @Test
    public void financeProcessTest_setSubCategory() {
        bot.setState(State.ExpensesMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.EXPENSES.get(0));
        HandlerExpenses HandlerExpenses = new HandlerExpenses(bot);
        bot = HandlerExpenses.financeProcess();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(Keyboard.replyKeyboard.EXPENSES.get(0), bot.getSubCategory());
        assertEquals(Finance.NUMBER.concat(Keyboard.replyKeyboard.EXPENSES.get(0)), bot.getMessages().get(0).getText());
    }
    
    @Test
    public void financeProcessTest_setSubCategoryOther() {
        bot.setState(State.ExpensesMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.EXPENSES.get(5));
        HandlerExpenses HandlerExpenses = new HandlerExpenses(bot);
        bot = HandlerExpenses.financeProcess();
        assertEquals(State.WaitingComment, bot.getState());
        assertEquals(Keyboard.replyKeyboard.EXPENSES.get(5), bot.getSubCategory());
        assertEquals(Finance.SAVE_OTHER, bot.getMessages().get(0).getText());
    }

    @Test
    public void financeProcessTest_setComment() {
        bot.setState(State.WaitingComment);
        bot.setSubCategory(Keyboard.replyKeyboard.EXPENSES.get(4));
        bot.setUserMessageText("Test");
        HandlerExpenses HandlerExpenses = new HandlerExpenses(bot);
        bot = HandlerExpenses.financeProcess();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(Keyboard.replyKeyboard.EXPENSES.get(4), bot.getSubCategory());
        assertEquals("Test", bot.getComment());
        assertEquals(Finance.NUMBER.concat(Keyboard.replyKeyboard.EXPENSES.get(4)), bot.getMessages().get(0).getText());
    }

    @Test
    public void financeProcessTest_setSum() {
        bot.setState(State.WaitingSum);
        bot.setSubCategory(Keyboard.replyKeyboard.EXPENSES.get(4));
        bot.setUserMessageText("100");
        HandlerExpenses HandlerExpenses = new HandlerExpenses(bot);
        bot = HandlerExpenses.financeProcess();
        assertEquals(State.WaitCallbackSaveOrDelete, bot.getState());
        assertEquals(Keyboard.replyKeyboard.EXPENSES.get(4), bot.getSubCategory());
        assertEquals(new BigDecimal("100"), bot.getSum());
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.finance), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(String.format(Finance.SAVE_QUESTION, "100"), bot.getMessages().get(0).getText());
    }
    
    @Test
    public void financeProcessTest_setSumError() {
        bot.setState(State.WaitingSum);
        bot.setSubCategory(Keyboard.replyKeyboard.EXPENSES.get(4));
        bot.setUserMessageText("Test");
        HandlerExpenses HandlerExpenses = new HandlerExpenses(bot);
        bot = HandlerExpenses.financeProcess();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(Keyboard.replyKeyboard.EXPENSES.get(4), bot.getSubCategory());
        assertEquals(Finance.NUMBER_ERROR, bot.getMessages().get(0).getText());
    }

    @Test
    public void financeProcessTest_error() {
        bot.setState(State.ExpensesMenu);
        bot.setSubCategory(null);
        bot.setUserMessageText("100");
        HandlerExpenses HandlerExpenses = new HandlerExpenses(bot);
        bot = HandlerExpenses.financeProcess();
        assertEquals(State.ExpensesMenu, bot.getState());
        assertEquals(null, bot.getSum());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.EXPENSES), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Finance.CATEGORY_ERROR, bot.getMessages().get(0).getText());
    }
}