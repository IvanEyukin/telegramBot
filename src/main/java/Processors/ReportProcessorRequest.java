package Processors;

import bot.database.ReportDatabase;
import bot.database.sqlite.dto.BaseReport;
import bot.keyboard.Keyboard;
import bot.message.BotMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReportProcessorRequest {

    public BotMessage getReportRequest (BotMessage botMessage) {

        BaseReport report = new BaseReport();
        ReportDatabase database = new ReportDatabase();
        ReportMessageProcessor reportMessage = new ReportMessageProcessor();
        LocalDate date = LocalDate.now();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        if (botMessage.getCallbackData().equals(Keyboard.report.get(0).getCallbackData())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date);
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        } else if (botMessage.getCallbackData().equals(Keyboard.report.get(1).getCallbackData())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusDays(1));
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);
            
        } else if (botMessage.getCallbackData().equals(Keyboard.report.get(2).getCallbackData())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusWeeks(1));
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        } else if (botMessage.getCallbackData().equals(Keyboard.report.get(3).getCallbackData())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusWeeks(2));
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        } else if (botMessage.getCallbackData().equals(Keyboard.report.get(4).getCallbackData())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusMonths(1));
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}