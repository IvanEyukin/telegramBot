package bot.handler.callback;

import bot.database.ReportDatabase;
import bot.database.sqlite.dto.BaseReport;
import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import Processors.ReportMessageProcessor;

import java.time.LocalDate;


public class HandlerReport {

    Bot bot;
    BaseReport report = new BaseReport();
    ReportDatabase database = new ReportDatabase();
    ReportMessageProcessor reportMessage = new ReportMessageProcessor();

    public HandlerReport(Bot bot) {
        this.bot = bot;
    }

    private BaseReport setDataReport(LocalDate from, LocalDate to) {
        report.setUserId(bot.getUser().getId());
        report.setTableName(database.tableMap.get(bot.getSubCategory()));
        report.setDateFrom(from);
        report.setDateTo(to);
        return report;
    }

    public Bot getReport() {
        LocalDate date = LocalDate.now();
        if (bot.getCallbackData().equals(Keyboard.report.get(0).getCallbackData())) {
            bot = reportMessage.getReportMessage(database.selectFinance(setDataReport(date, date.plusDays(1))), bot);
        } else if (bot.getCallbackData().equals(Keyboard.report.get(1).getCallbackData())) {
            bot = reportMessage.getReportMessage(database.selectFinance(setDataReport(date.minusDays(1), date.plusDays(1))), bot);
        } else if (bot.getCallbackData().equals(Keyboard.report.get(2).getCallbackData())) {
            bot = reportMessage.getReportMessage(database.selectFinance(setDataReport(date.minusWeeks(1), date.plusDays(1))), bot);
        } else if (bot.getCallbackData().equals(Keyboard.report.get(3).getCallbackData())) {
            bot = reportMessage.getReportMessage(database.selectFinance(setDataReport(date.minusWeeks(2), date.plusDays(1))), bot);
        } else if (bot.getCallbackData().equals(Keyboard.report.get(4).getCallbackData())) {
            bot = reportMessage.getReportMessage(database.selectFinance(setDataReport(date.minusMonths(1), date.plusDays(1))), bot);
        }
        bot.setSubCategory(null);
        return bot;
    }
}