package processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoReport.BaseReport;
import LibBaseDto.DtoReport.BaseReportResult;
import Utils.BotSendMessage;

public class ReportMessageProcessor {

    public BotMessage getReportMessage(BaseReport report, List<BaseReportResult> reportResult, BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        BigDecimal sum = new BigDecimal("0");
        List<SendMessage> messages = new ArrayList<>();
        String categoryMessage = "";

        for (BaseReportResult result : reportResult) {
            sum = sum.add(result.getSum());
            categoryMessage = categoryMessage.concat(String.format(botMessage.reportResultMessageCategory, result.getCategory(), result.getSum()));
        }

        String message = String.format(botMessage.reportResultMessage, botMessage.getFinanceSubCategory(), report.getDateFrom(), report.getDateTo(), sum); 
        messages.add(sendMessage.sendMessage(botMessage.getMessage(), message));
        if (sum.compareTo(new BigDecimal("0")) == 1) {
            messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.reportResultMessageDetail.concat(categoryMessage)));
        }
        
        botMessage.setMessages(messages);

        return botMessage;

    }
    
}
