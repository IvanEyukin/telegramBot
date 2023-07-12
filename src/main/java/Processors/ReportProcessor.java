package Processors;

import TelegramBot.BotSendMessage;
import bot.keyboard.Keyboard;
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
        BotSendMessage sendMessage = new BotSendMessage();

        switch (botMessage.getSession()) {
            case ReportMenu -> {
                if (Keyboard.replyKeyboar.REPORT.contains(botMessage.getUserMessageText()) && !botMessage.getUserMessageText().equals("Бюджет")) {
                    botMessage.setFinanceSubCategory(botMessage.getUserMessageText());
                    botMessage.setMessageHasInLineKeyboaard(true);
                    botMessage.updateBotState(State.PeriodSelection);

                    messages.add(sendMessage.sendMessageAndInline(Report.PERIOD, Keyboard.report));
                } else if (Keyboard.replyKeyboar.REPORT.contains(botMessage.getUserMessageText()) && botMessage.getUserMessageText().equals("Бюджет")) {
                    botMessage.setFinanceSubCategory(botMessage.getUserMessageText());
                    botMessage.updateBotState(State.ReportMenu);
                    botMessage.setFinanceSubCategory(null);
                    
                    messages.add(sendMessage.sendMessageAndKeyboard(Global.DEVELOP, Keyboard.replyKeyboar.REPORT));
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(Report.CATEGORY_ERROR, Keyboard.replyKeyboar.REPORT));
                }
            }
            default -> {
                botMessage.updateBotState(State.ReportMenu);
                messages.add(sendMessage.sendMessageAndKeyboard(Report.CATEGORY, Keyboard.replyKeyboar.REPORT));
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}