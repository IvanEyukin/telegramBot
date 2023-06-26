package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
import BotFSM.BotState;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class ReportProcessor {

    public BotMessage getReport(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (botMessage.getBotState()) {
            case EnterTypeReport -> {
                if (keyboardMessage.getReportMenuButton().contains(botMessage.getUserMessageText()) && !botMessage.getUserMessageText().equals("Бюджет")) {
                    botMessage.setFinanceSubCategory(botMessage.getUserMessageText());
                    botMessage.setMessageHasInLineKeyboaard(true);
                    botMessage.updateBotState(BotState.WaitCallbackReport);

                    messages.add(sendMessage.sendMessageAndInline(botMessage.reportPeriodQuestion, keyboardMessage.getReportButtons()));
                } else if (keyboardMessage.getReportMenuButton().contains(botMessage.getUserMessageText()) && botMessage.getUserMessageText().equals("Бюджет")) {
                    botMessage.setFinanceSubCategory(botMessage.getUserMessageText());
                    botMessage.updateBotState(BotState.ReportMenu);
                    botMessage.setFinanceSubCategory(null);
                    
                    messages.add(sendMessage.sendMessageAndKeyboard(botMessage.develop, keyboardMessage.getReportMenuButton()));
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(botMessage.reportCategoryError, keyboardMessage.getReportMenuButton()));
                }
            }
            default -> {
                botMessage.updateBotState(BotState.EnterTypeReport);
                messages.add(sendMessage.sendMessageAndKeyboard(botMessage.reportCategoryQuestion, keyboardMessage.getReportMenuButton()));
            }
        }

        botMessage.setMessages(messages);

        return botMessage;

    }
    
}