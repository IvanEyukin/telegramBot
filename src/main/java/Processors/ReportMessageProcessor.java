package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoReport.BaseReport;
import TelegramBot.BotSendMessage;

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
                categoryMessage = categoryMessage.concat(String.format(botMessage.reportResultMessageCategory, result.getCategory(), result.getSum()));
            }

            String message = String.format(botMessage.reportResultMessage, botMessage.getFinanceSubCategory(), report.getDateFrom(), report.getDateTo(), sum.setScale(2, RoundingMode.HALF_DOWN)); 
            messages.add(sendMessage.sendMessage(message));
            if (sum.compareTo(new BigDecimal("0")) == 1) {
                messages.add(sendMessage.sendMessage(botMessage.reportResultMessageDetail.concat(categoryMessage)));
            }
        } else {
            messages.add(sendMessage.sendMessage(botMessage.reportResultMessageError));
            botMessage.setFinanceSubCategory(null);
        }
        
        botMessage.setMessages(messages);

        return botMessage;

    }
    
}