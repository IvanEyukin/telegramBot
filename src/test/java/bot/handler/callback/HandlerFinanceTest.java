package bot.handler.callback;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.keyboard.reply.ReplyKeyboard;
import bot.message.Finance;
import bot.state.State;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;


public class HandlerFinanceTest {

    private Bot bot = new Bot();
    private User user = new User();

    @Before
    public void setUser() {
        user.setId((long) 100200300);
        user.setName("Test");
        user.setDateMessage(1000000000);
        bot.setUser(user);
    }

    @Test
    public void handlerStartTest_saveExpenses() {
        bot.setCallbackData("Save");
        bot.setCategory("Расходы");
        bot.setSubCategory("Прочее");
        bot.setComment("Test");
        bot.setSum(new BigDecimal("777"));
        HandlerFinance handlerFinance = new HandlerFinance(bot);
        bot = handlerFinance.handlerStart();
        assertEquals(State.ExpensesMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.EXPENSES), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(String.format(Finance.SAVE, "777"), bot.getMessages().get(0).getText());
    }

    @Test
    public void handlerStartTest_saveIncome() {
        bot.setCallbackData("Save");
        bot.setCategory("Доходы");
        bot.setSubCategory("Прочее");
        bot.setComment("Test");
        bot.setSum(new BigDecimal("777"));
        HandlerFinance handlerFinance = new HandlerFinance(bot);
        bot = handlerFinance.handlerStart();
        assertEquals(State.IncomeMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.INCOME), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(String.format(Finance.SAVE, "777"), bot.getMessages().get(0).getText());
    }

    @Test
    public void handlerStartTest_delete() {
        bot.setCallbackData("Delete");
        bot.setPreviousState(State.WaitingSum);
        bot.setCategory("Доходы");
        bot.setSubCategory("Прочее");
        bot.setComment("Test");
        bot.setSum(new BigDecimal("777"));
        HandlerFinance handlerFinance = new HandlerFinance(bot);
        bot = handlerFinance.handlerStart();
        assertEquals(State.WaitingSum, bot.getState());
        assertEquals(null, bot.getSum());
        assertEquals(String.format(Finance.DELETE, "777"), bot.getMessages().get(0).getText());
    }
}