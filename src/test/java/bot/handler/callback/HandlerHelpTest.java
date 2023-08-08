package bot.handler.callback;

import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.keyboard.inline.InLineKeyboard;
import bot.message.Help;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HandlerHelpTest {
    
    private Bot bot = new Bot();

    @Test
    public void getAnswerTest_helpPrivat() {
        bot.setCallbackData("HelpPrivat"); 
        HandlerHelp handlerHelp = new HandlerHelp(bot);
        bot = handlerHelp.getAnswer();
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.help), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Help.INFO_SAVE, bot.getMessages().get(0).getText());
    }

    @Test
    public void getAnswerTest_helpSave() {
        bot.setCallbackData("HelpSave"); 
        HandlerHelp handlerHelp = new HandlerHelp(bot);
        bot = handlerHelp.getAnswer();
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.help), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Help.SAVE, bot.getMessages().get(0).getText());
    }

    @Test
    public void getAnswerTest_helpDelete() {
        bot.setCallbackData("HelpDelete"); 
        HandlerHelp handlerHelp = new HandlerHelp(bot);
        bot = handlerHelp.getAnswer();
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.help), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Help.DELETE, bot.getMessages().get(0).getText());
    }

    @Test
    public void getAnswerTest_helpWrit() {
        bot.setCallbackData("HelpWrit"); 
        HandlerHelp handlerHelp = new HandlerHelp(bot);
        bot = handlerHelp.getAnswer();
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.help), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Help.WRITE, bot.getMessages().get(0).getText());
    }
}