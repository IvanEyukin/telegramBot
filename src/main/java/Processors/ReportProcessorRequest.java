package Processors;

import bot.database.ReportDatabase;
import bot.database.sqlite.dto.BaseReport;
import bot.entitie.Bot;
import bot.keyboard.Keyboard;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReportProcessorRequest {

    public Bot getReportRequest (Bot bot) {

        BaseReport report = new BaseReport();
        ReportDatabase database = new ReportDatabase();
        ReportMessageProcessor reportMessage = new ReportMessageProcessor();
        LocalDate date = LocalDate.now();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        if (bot.getCallbackData().equals(Keyboard.report.get(0).getCallbackData())) {

            report.setUserId(bot.getUser().getId());
            report.setTableName(database.tableMap.get(bot.getSubCategory()));
            report.setDateFrom(date);
            report.setDateTo(date.plusDays(1));

            bot = reportMessage.getReportMessage(database.selectFinance(report), bot);
            messages = bot.getMessages();
            bot.setSubCategory(null);

        } else if (bot.getCallbackData().equals(Keyboard.report.get(1).getCallbackData())) {

            report.setUserId(bot.getUser().getId());
            report.setTableName(database.tableMap.get(bot.getSubCategory()));
            report.setDateFrom(date.minusDays(1));
            report.setDateTo(date.plusDays(1));

            bot = reportMessage.getReportMessage(database.selectFinance(report), bot);
            messages = bot.getMessages();
            bot.setSubCategory(null);
            
        } else if (bot.getCallbackData().equals(Keyboard.report.get(2).getCallbackData())) {

            report.setUserId(bot.getUser().getId());
            report.setTableName(database.tableMap.get(bot.getSubCategory()));
            report.setDateFrom(date.minusWeeks(1));
            report.setDateTo(date.plusDays(1));

            bot = reportMessage.getReportMessage(database.selectFinance(report), bot);
            messages = bot.getMessages();
            bot.setSubCategory(null);

        } else if (bot.getCallbackData().equals(Keyboard.report.get(3).getCallbackData())) {

            report.setUserId(bot.getUser().getId());
            report.setTableName(database.tableMap.get(bot.getSubCategory()));
            report.setDateFrom(date.minusWeeks(2));
            report.setDateTo(date.plusDays(1));

            bot = reportMessage.getReportMessage(database.selectFinance(report), bot);
            messages = bot.getMessages();
            bot.setSubCategory(null);

        } else if (bot.getCallbackData().equals(Keyboard.report.get(4).getCallbackData())) {

            report.setUserId(bot.getUser().getId());
            report.setTableName(database.tableMap.get(bot.getSubCategory()));
            report.setDateFrom(date.minusMonths(1));
            report.setDateTo(date.plusDays(1));

            bot = reportMessage.getReportMessage(database.selectFinance(report), bot);
            messages = bot.getMessages();
            bot.setSubCategory(null);

        }
        bot.setMessages(messages);

        return bot;
    }
}