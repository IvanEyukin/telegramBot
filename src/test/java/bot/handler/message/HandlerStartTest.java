package bot.handler.message;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.message.Global;
import bot.state.State;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;


public class HandlerStartTest {

    private Bot bot = new Bot();
    private  User user = new User();

    @Test
    public void getStartTest_emptyBot() {   
        user.setName("Test1");
        bot.setUser(user);
        HandlerStart handlerStart = new HandlerStart(bot);
        bot = handlerStart.getStart();
        assertEquals(null, bot.getSum());
        assertEquals(null, bot.getCategory());
        assertEquals(null, bot.getSubCategory());
        assertEquals(State.Start, bot.getState());
        assertEquals(String.format(Global.GREETING, user.getName()), bot.getMessages().get(0).getText());
        assertEquals(Global.MENU, bot.getMessages().get(1).getText());
    }
    @Test
    public void getStartTest_filledBot() {      
        user.setName("Test2");
        bot.setUser(user);
        bot.setSum(new BigDecimal("100"));
        bot.setCategory("TestCategory");
        bot.setSubCategory("TestSubCategory");
        bot.setState(State.ExpensesMenu);
        HandlerStart handlerStart = new HandlerStart(bot);
        bot = handlerStart.getStart();
        assertEquals(null, bot.getSum());
        assertEquals(null, bot.getCategory());
        assertEquals(null, bot.getSubCategory());
        assertEquals(State.Start, bot.getState());
        assertEquals(String.format(Global.GREETING, user.getName()), bot.getMessages().get(0).getText());
        assertEquals(Global.MENU, bot.getMessages().get(1).getText());
    }
}