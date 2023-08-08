package bot.handler.message;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.keyboard.inline.InLineKeyboard;
import bot.keyboard.reply.ReplyKeyboard;
import bot.message.Setting;
import bot.state.State;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HandlerSettingTest {

    private Bot bot = new Bot();
    private User user = new User();

    @Before
    public void setUser() {
        user.setId((long) 100200300);
        user.setName("Test");
        bot.setUser(user);
    }

    @Test
    public void getSettingTest_getMenu() {
        bot.setState(State.Start);
        HandlerSetting handlerSetting = new HandlerSetting(bot);
        bot = handlerSetting.getSetting();
        assertEquals(State.SettingMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.SETTING), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Setting.MENU, bot.getMessages().get(0).getText());
    }

    @Test
    public void getSettingTest_getSettig() {
        bot.setState(State.SettingMenu);
        HandlerSetting handlerSetting = new HandlerSetting(bot);
        bot = handlerSetting.getSetting();
        assertEquals(State.ReminderOptionsSelection, bot.getState());
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.setting), bot.getMessages().get(0).getReplyMarkup());
        assertEquals("Test, сейчас напоминания выключены", bot.getMessages().get(0).getText());
    }
}