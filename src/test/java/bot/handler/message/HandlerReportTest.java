package bot.handler.message;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.keyboard.inline.InLineKeyboard;
import bot.keyboard.reply.ReplyKeyboard;
import bot.message.Global;
import bot.message.Report;
import bot.state.State;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HandlerReportTest {

    private Bot bot = new Bot();
    private  User user = new User();

    @Before
    public void setUser() {
        user.setId((long) 100200300);
        user.setName("Test");
        bot.setUser(user);
    }

    @Test
    public void getReportTest_getMenu() {
        bot.setState(State.Start);
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(State.ReportMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.REPORT), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Report.CATEGORY, bot.getMessages().get(0).getText());
    }

    @Test
    public void getReportTest_getReportFinance() {
        bot.setState(State.ReportMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.REPORT.get(0));
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(State.PeriodSelection, bot.getState());
        assertEquals(true, bot.getMessageHasInLineKeyboaard());
        assertEquals(Keyboard.replyKeyboard.REPORT.get(0), bot.getSubCategory());
        assertEquals(InLineKeyboard.setKeyboard(Keyboard.report), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Report.PERIOD, bot.getMessages().get(0).getText());
    }

    @Test
    public void getReportTest_getReportBudget() {
        bot.setState(State.ReportMenu);
        bot.setUserMessageText(Keyboard.replyKeyboard.REPORT.get(2));
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(State.ReportMenu, bot.getState());
        assertEquals(null, bot.getSubCategory());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.REPORT), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Global.DEVELOP, bot.getMessages().get(0).getText());
    }

    @Test
    public void getReportTest_getReportError() {
        bot.setState(State.ReportMenu);
        bot.setUserMessageText("Test");
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(State.ReportMenu, bot.getState());
        assertEquals(ReplyKeyboard.getKeyboardMarkup(Keyboard.replyKeyboard.REPORT), bot.getMessages().get(0).getReplyMarkup());
        assertEquals(Report.CATEGORY_ERROR, bot.getMessages().get(0).getText());
    }
}