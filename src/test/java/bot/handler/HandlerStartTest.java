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
    public void getStartTest_emptyBot() {   
        String greeting = """
            Здравствуй Test1,
            я твой персональный финансовый ассистент.
            Надеюсь, я смогу помочь тебе тратить меньше денег.
            Не забывай, что ты моя сладкая булочка!
            """;
        String menu = "Нажми на кнопку Меню и выбери, что тебя интересует";   
        user.setName("Test1");
        bot.setUser(user);
        HandlerStart handlerStart = new HandlerStart(bot);
        bot = handlerStart.getStart();
        assertEquals(null, bot.getSum());
        assertEquals(null, bot.getCategory());
        assertEquals(null, bot.getSubCategory());
        assertEquals(State.Start, bot.getState());
        assertEquals(greeting, bot.getMessages().get(0).getText());
        assertEquals(menu, bot.getMessages().get(1).getText());
    }
    @Test
    public void getStartTest_filledBot() {   
        String greeting = """
            Здравствуй Test2,
            я твой персональный финансовый ассистент.
            Надеюсь, я смогу помочь тебе тратить меньше денег.
            Не забывай, что ты моя сладкая булочка!
            """;
        String menu = "Нажми на кнопку Меню и выбери, что тебя интересует";    
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
        assertEquals(greeting, bot.getMessages().get(0).getText());
        assertEquals(menu, bot.getMessages().get(1).getText());
    }
}