package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoReport.BaseReport;
import bot.database.ReportDatabase;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReportProcessorRequest {

    public BotMessage getReportRequest (BotMessage botMessage) {

        BaseReport report = new BaseReport();
        ReportDatabase database = new ReportDatabase();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        ReportMessageProcessor reportMessage = new ReportMessageProcessor();
        LocalDate date = LocalDate.now();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        if (botMessage.getCallbackData().equals(keyboardMessage.getToDayButton().getCallBack())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date);
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        } else if (botMessage.getCallbackData().equals(keyboardMessage.getLastDayButton().getCallBack())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusDays(1));
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);
            
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getLastWeekButton().getCallBack())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusWeeks(1));
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        } else if (botMessage.getCallbackData().equals(keyboardMessage.getLastTwoWeekButton().getCallBack())) {

            report.setUserId(botMessage.getUserInfo().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusWeeks(2));
            report.setDateTo(date.plusDays(1));

            botMessage = reportMessage.getReportMessage(database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        } else if (botMessage.getCallbackData().equals(keyboardMessage.getLastMonthButton().getCallBack())) {

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