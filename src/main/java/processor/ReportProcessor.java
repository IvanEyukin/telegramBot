package processor;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import Utils.BotSendMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class ReportProcessor {

    public BotMessage getReport(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();
        String messageText = botMessage.getMessage().getText();

        if (keyboardMessage.getReportMenuButton().contains(messageText) && !messageText.equals("Бюджет")) {
            botMessage.setFinanceSubCategory(messageText);
            messages.add(sendMessage.sendMessageAndInline(botMessage.getMessage(), botMessage.reportPeriodQuestion, keyboardMessage.getReportButtons()));
        } else if (keyboardMessage.getReportMenuButton().contains(messageText) && messageText.equals("Бюджет")) {
            botMessage.setFinanceSubCategory(messageText);
            messages.add(sendMessage.sendMessageAndKeyboard(botMessage.getMessage(), botMessage.develop, keyboardMessage.getReportMenuButton()));
            botMessage.setFinanceSubCategory(null);
        } else {
            messages.add(sendMessage.sendMessageAndKeyboard(botMessage.getMessage(), botMessage.reportCategoryQuestion, keyboardMessage.getReportMenuButton()));
        }

        botMessage.setMessages(messages);

        return botMessage;

    }
    
}