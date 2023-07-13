package Processors;

import TelegramBot.BotSendMessage;
import bot.dto.Bot;
import bot.keyboard.Keyboard;
import bot.message.Global;
import bot.message.Report;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class ReportProcessor {

    public Bot getReport(Bot bot) {

        List<SendMessage> messages = new ArrayList<>();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (bot.getState()) {
            case ReportMenu -> {
                if (Keyboard.replyKeyboar.REPORT.contains(bot.getUserMessageText()) && !bot.getUserMessageText().equals("Бюджет")) {
                    bot.setSubCategory(bot.getUserMessageText());
                    bot.setMessageHasInLineKeyboaard(true);
                    bot.updateBotState(State.PeriodSelection);

                    messages.add(sendMessage.sendMessageAndInline(Report.PERIOD, Keyboard.report));
                } else if (Keyboard.replyKeyboar.REPORT.contains(bot.getUserMessageText()) && bot.getUserMessageText().equals("Бюджет")) {
                    bot.updateBotState(State.ReportMenu);
                    bot.setSubCategory(null);
                    
                    messages.add(sendMessage.sendMessageAndKeyboard(Global.DEVELOP, Keyboard.replyKeyboar.REPORT));
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(Report.CATEGORY_ERROR, Keyboard.replyKeyboar.REPORT));
                }
            }
            default -> {
                bot.updateBotState(State.ReportMenu);
                messages.add(sendMessage.sendMessageAndKeyboard(Report.CATEGORY, Keyboard.replyKeyboar.REPORT));
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}