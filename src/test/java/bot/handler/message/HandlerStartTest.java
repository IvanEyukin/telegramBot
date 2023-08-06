package bot.handler.message;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.message.Global;
import bot.state.State;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;


public class HandlerStartTest {

    private Bot bot = new Bot();
    private  User user = new User();

    @Before
    public void setUser() {
        user.setName("Test");
        bot.setUser(user);
    }

    @Test
    public void getStartTest_emptyBot() {   
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