package Processors;

import TelegramBot.BotSendMessage;
import bot.database.sqlite.dto.BaseReport;
import bot.message.BotMessage;
import bot.message.Report;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class ReportMessageProcessor {

    public BotMessage getReportMessage(BaseReport report, BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        BigDecimal sum = new BigDecimal("0");
        List<SendMessage> messages = new ArrayList<>();
        String categoryMessage = "";

        if (report.getBaseReportsList() != null) {  
            for (BaseReport result : report.getBaseReportsList()) {
                sum = sum.add(result.getSum());
                categoryMessage = categoryMessage.concat(String.format(Report.RESULT_CATEGORY, result.getCategory(), result.getSum()));
            }

            String message = String.format(Report.RESULT, botMessage.getFinanceSubCategory(), report.getDateFrom(), report.getDateTo(), sum.setScale(2, RoundingMode.HALF_DOWN)); 
            messages.add(sendMessage.sendMessage(message));
            if (sum.compareTo(new BigDecimal("0")) == 1) {
                messages.add(sendMessage.sendMessage(Report.RESULT_DETAIL.concat(categoryMessage)));
            }
        } else {
            messages.add(sendMessage.sendMessage(Report.RESULT_ERROR));
            botMessage.setFinanceSubCategory(null);
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}