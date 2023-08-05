package bot.handler;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.handler.message.HandlerStart;
import bot.state.State;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;


public class HandlerStartTest {

    private Bot bot = new Bot();
    private  User user = new User();

    @Test
    public void getStartTest1() {      
        user.setName("Test1");
        bot.setUser(user);
        HandlerStart handlerStart = new HandlerStart(bot);
        bot = handlerStart.getStart();
        assertEquals(null, bot.getSum());
        assertEquals(null, bot.getCategory());
        assertEquals(null, bot.getSubCategory());
        assertEquals(State.Start, bot.getState());
    }
    @Test
    public void getStartTest2() {      
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
    }
}