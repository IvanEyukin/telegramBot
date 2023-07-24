package bot.handler.callback;

import bot.database.sqlite.dto.BaseReport;
import bot.entitie.Bot;
import bot.message.Report;
import bot.message.send.MessageBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class HandlerReportMessage {

    public Bot getMessage(BaseReport report, Bot bot) {

        MessageBuilder message = new MessageBuilder();
        BigDecimal sum = new BigDecimal("0");
        List<SendMessage> messages = new ArrayList<>();
        String categoryMessage = "";

        if (report.getBaseReportsList() != null) {  
            for (BaseReport result : report.getBaseReportsList()) {
                sum = sum.add(result.getSum());
                categoryMessage = categoryMessage.concat(String.format(Report.RESULT_CATEGORY, result.getCategory(), result.getSum()));
            }
            String messageReport = String.format(Report.RESULT, bot.getSubCategory(), report.getDateFrom(), report.getDateTo(), sum.setScale(2, RoundingMode.HALF_DOWN)); 
            messages.add(message.sendMessage(messageReport));
            if (sum.compareTo(new BigDecimal("0")) == 1) {
                messages.add(message.sendMessage(Report.RESULT_DETAIL.concat(categoryMessage)));
            }
        } else {
            messages.add(message.sendMessage(Report.RESULT_ERROR));
            bot.setSubCategory(null);
        }
        bot.setMessages(messages);

        return bot;
    }
}