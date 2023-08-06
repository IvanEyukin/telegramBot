package bot.handler.message;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.keyboard.reply.ReplyKeyboard;
import bot.message.Admin;
import bot.state.State;


public class HandlerAdminTest {

    private Bot bot = new Bot();
    private  User user = new User();

    @Before
    public void setUser() {
        user.setName("Test");
        bot.setUser(user);
    }

    @Test
    public void adminMenuTest_getMenu() {
        bot.setState(State.Start);
        HandlerAdmin handlerAdmin = new HandlerAdmin(bot);
        bot = handlerAdmin.adminMenu();
        assertEquals(State.AdminMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.ADMIN), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Admin.MENU, bot.getMessages().get(0).getText());
    }

    @Test
    public void adminMenuTest_getNotification() {
        bot.setState(State.AdminMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.ADMIN.get(0));
        HandlerAdmin handlerAdmin = new HandlerAdmin(bot);
        bot = handlerAdmin.adminMenu();
        assertEquals(State.WaitingMessageMailings, bot.getState());
        assertEquals(Admin.NOTIFICATION, bot.getMessages().get(0).getText());
    }

    @Test
    public void adminMenuTest_getUsersReport() {
        bot.setState(State.AdminMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.ADMIN.get(1));
        HandlerAdmin handlerAdmin = new HandlerAdmin(bot);
        bot = handlerAdmin.adminMenu();
        assertEquals(State.Start, bot.getState());
        assertEquals("", bot.getMessages().get(0).getText());
    }

    @Test
    public void adminMenuTest_error() {
        bot.setState(State.AdminMenu);
        bot.setUserMessageText("Test");
        HandlerAdmin handlerAdmin = new HandlerAdmin(bot);
        bot = handlerAdmin.adminMenu();
        assertEquals(State.AdminMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.ADMIN), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Admin.ERROR, bot.getMessages().get(0).getText());
    }

    @Test
    public void adminMenuTest_messageNotification() {
        bot.setState(State.WaitingMessageMailings);
        bot.setUserMessageText("Test");
        HandlerAdmin handlerAdmin = new HandlerAdmin(bot);
        bot = handlerAdmin.adminMenu();
        assertEquals(State.Start, bot.getState());
        assertEquals(Admin.NOTIFICATION_START, bot.getMessages().get(0).getText());
    }
}