package Processors;

import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
import bot.message.BotMessage;
import bot.message.Global;
import bot.message.Report;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class ReportProcessor {

    public BotMessage getReport(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (botMessage.getSession()) {
            case ReportMenu -> {
                if (keyboardMessage.getReportMenuButton().contains(botMessage.getUserMessageText()) && !botMessage.getUserMessageText().equals("Бюджет")) {
                    botMessage.setFinanceSubCategory(botMessage.getUserMessageText());
                    botMessage.setMessageHasInLineKeyboaard(true);
                    botMessage.updateBotState(State.PeriodSelection);

                    messages.add(sendMessage.sendMessageAndInline(Report.PERIOD, keyboardMessage.getReportButtons()));
                } else if (keyboardMessage.getReportMenuButton().contains(botMessage.getUserMessageText()) && botMessage.getUserMessageText().equals("Бюджет")) {
                    botMessage.setFinanceSubCategory(botMessage.getUserMessageText());
                    botMessage.updateBotState(State.ReportMenu);
                    botMessage.setFinanceSubCategory(null);
                    
                    messages.add(sendMessage.sendMessageAndKeyboard(Global.DEVELOP, keyboardMessage.getReportMenuButton()));
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(Report.CATEGORY_ERROR, keyboardMessage.getReportMenuButton()));
                }
            }
            default -> {
                botMessage.updateBotState(State.ReportMenu);
                messages.add(sendMessage.sendMessageAndKeyboard(Report.CATEGORY, keyboardMessage.getReportMenuButton()));
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}