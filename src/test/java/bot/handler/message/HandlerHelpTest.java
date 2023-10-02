package bot.handler.message;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.keyboard.inline.InLineKeyboard;
import bot.keyboard.reply.ReplyKeyboard;
import bot.message.Help;
import bot.state.State;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HandlerHelpTest {

    private Bot bot = new Bot();
    private User user = new User();

    @Before
    public void setUser() {
        user.setName("Test");
        bot.setUser(user);
    }

    @Test
    public void getHelpTest_getMenu() {
        bot.setState(State.Start);
        HandlerHelp handlerHelp = new HandlerHelp(bot);
        bot = handlerHelp.getHelp();
        assertEquals(State.HelpMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.HELP), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(String.format(Help.START, user.getName()), bot.getMessages().get(0).getText());
    }

    @Test
    public void getHelpTest_getInfoBot() {
        bot.setState(State.HelpMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.HELP.get(0));
        HandlerHelp handlerHelp = new HandlerHelp(bot);
        bot = handlerHelp.getHelp();
        assertEquals(State.HelpMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.HELP), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Help.INFO_BOT, bot.getMessages().get(0).getText());
    }

    @Test
    public void getHelpTest_getInfoQuestion() {
        bot.setState(State.HelpMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.HELP.get(1));
        HandlerHelp handlerHelp = new HandlerHelp(bot);
        bot = handlerHelp.getHelp();
        assertEquals(State.InformationRetentionQuestionsSelection, bot.getState());
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.help), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Help.INFO_QUESTION, bot.getMessages().get(0).getText());
    }

    @Test
    public void getHelpTest_getInfoError() {
        bot.setState(State.HelpMenu);
        bot.setUserMessageText("Test");
        HandlerHelp handlerHelp = new HandlerHelp(bot);
        bot = handlerHelp.getHelp();
        assertEquals(State.HelpMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.HELP), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Help.ERROR, bot.getMessages().get(0).getText());
    }
}