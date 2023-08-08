package bot.handler.callback;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.message.Report;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;


public class HandlerReportTest {

    private Bot bot = new Bot();
    private User user = new User();
    LocalDate date = LocalDate.now();

    @Before
    public void setUser() {
        user.setId((long) 100200301);
        user.setName("Test");
        bot.setUser(user);
    }

    @Test
    public void getReportTest_ToDay() {
        bot.setCallbackData("ToDay");
        bot.setSubCategory("Доходы");
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(null, bot.getSubCategory());
        assertEquals(String.format(Report.RESULT, "Доходы", date, date.plusDays(1), "0.00"), bot.getMessages().get(0).getText());
    }

    @Test
    public void getReportTest_LastDay() {
        bot.setCallbackData("LastDay");
        bot.setSubCategory("Расходы");
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(null, bot.getSubCategory());
        assertEquals(String.format(Report.RESULT, "Расходы", date.minusDays(1), date.plusDays(1), "0.00"), bot.getMessages().get(0).getText());
    }

    @Test
    public void getReportTest_LastWeek() {
        bot.setCallbackData("LastWeek");
        bot.setSubCategory("Доходы");
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(null, bot.getSubCategory());
        assertEquals(String.format(Report.RESULT, "Доходы", date.minusWeeks(1), date.plusDays(1), "0.00"), bot.getMessages().get(0).getText());
    }

    @Test
    public void getReportTest_LastTwoWeek() {
        bot.setCallbackData("LastTwoWeek");
        bot.setSubCategory("Расходы");
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(null, bot.getSubCategory());
        assertEquals(String.format(Report.RESULT, "Расходы", date.minusWeeks(2), date.plusDays(1), "0.00"), bot.getMessages().get(0).getText());
    }

    @Test
    public void getReportTest_LastMonth() {
        bot.setCallbackData("LastMonth");
        bot.setSubCategory("Доходы");
        HandlerReport handlerReport = new HandlerReport(bot);
        bot = handlerReport.getReport();
        assertEquals(null, bot.getSubCategory());
        assertEquals(String.format(Report.RESULT, "Доходы", date.minusMonths(1), date.plusDays(1), "0.00"), bot.getMessages().get(0).getText());
    }
}