package bot.handler.callback;

import bot.database.ReportDatabase;
import bot.database.sqlite.dto.BaseReport;
import bot.entitie.Bot;
import bot.entitie.User;
import bot.message.Report;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;


public class HandlerReportMessageTest {

    private Bot bot = new Bot();
    private User user = new User();
    private BaseReport report = new BaseReport();
    private ReportDatabase database = new ReportDatabase();
    LocalDate date = LocalDate.of(2023, 8, 7);

    @Before
    public void setUser() {
        user.setId((long) 100200300);
        user.setName("Test");
        bot.setUser(user);
    }

    @Before
    public void setReport() {
        report.setUserId(user.getId());
        report.setDateFrom(date.minusDays(1));
        report.setDateTo(date.plusDays(1));
    }

    @Test
    public void getMessageTest_reportListIncome() {
        HandlerReportMessage handlerReportMessage = new HandlerReportMessage();
        report.setTableName(database.tableIncome);
        bot.setSubCategory("Доходы");
        bot = handlerReportMessage.getMessage(database.selectFinance(report), bot);
        assertEquals(String.format(Report.RESULT, "Доходы", date.minusDays(1), date.plusDays(1), "100.00"), bot.getMessages().get(0).getText());
        assertEquals(Report.RESULT_DETAIL.concat(String.format(Report.RESULT_CATEGORY, "Зарплата", "100.00")), bot.getMessages().get(1).getText());
    }

    @Test
    public void getMessageTest_reportListExpenses() {
        HandlerReportMessage handlerReportMessage = new HandlerReportMessage();
        report.setTableName(database.tableExpenses);
        bot.setSubCategory("Расходы");
        bot = handlerReportMessage.getMessage(database.selectFinance(report), bot);
        assertEquals(String.format(Report.RESULT, "Расходы", date.minusDays(1), date.plusDays(1), "100.00"), bot.getMessages().get(0).getText());
        assertEquals(Report.RESULT_DETAIL.concat(String.format(Report.RESULT_CATEGORY, "Алкоголь", "100.00")), bot.getMessages().get(1).getText());
    }

    @Test
    public void getMessageTest_error() {
        HandlerReportMessage handlerReportMessage = new HandlerReportMessage();
        bot = handlerReportMessage.getMessage(report, bot);
        assertEquals(null, bot.getSubCategory());
        assertEquals(Report.RESULT_ERROR, bot.getMessages().get(0).getText());
    }
}