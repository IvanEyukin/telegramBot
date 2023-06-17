package processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoReport.BaseReport;
import Utils.BotSendMessage;

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

            String message = String.format(botMessage.reportResultMessage, botMessage.getFinanceSubCategory(), report.getDateFrom(), report.getDateTo(), sum); 
            messages.add(sendMessage.sendMessage(botMessage.getMessage(), message));
            if (sum.compareTo(new BigDecimal("0")) == 1) {
                messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.reportResultMessageDetail.concat(categoryMessage)));
            }

        } else {
            messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.reportResultMessageError));
            botMessage.setFinanceSubCategory(null);
        }
        
        botMessage.setMessages(messages);

        return botMessage;

    }
    
}