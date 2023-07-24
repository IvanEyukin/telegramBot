package bot.handler.message;

import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Global;
import bot.message.Report;
import bot.message.send.MessageBuilder;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerReport {

    Bot bot;
    List<SendMessage> messages = new ArrayList<>();
    MessageBuilder message = new MessageBuilder();

    public HandlerReport(Bot bot) {
        this.bot = bot;
    }

    public Bot getReport() {
        switch (bot.getState()) {
            case ReportMenu -> {
                if (Keyboard.replyKeyboard.REPORT.contains(bot.getUserMessageText()) && !bot.getUserMessageText().equals(Keyboard.replyKeyboard.REPORT.get(2))) {
                    bot.setSubCategory(bot.getUserMessageText());
                    bot.setMessageHasInLineKeyboaard(true);
                    bot.updateBotState(State.PeriodSelection);
                    messages.add(message.sendMessageAndInline(Report.PERIOD, Keyboard.report));
                } else if (Keyboard.replyKeyboard.REPORT.contains(bot.getUserMessageText()) && bot.getUserMessageText().equals(Keyboard.replyKeyboard.REPORT.get(2))) {
                    bot.updateBotState(State.ReportMenu);
                    bot.setSubCategory(null);
                    messages.add(message.sendMessageAndKeyboard(Global.DEVELOP, Keyboard.replyKeyboard.REPORT));
                } else {
                    messages.add(message.sendMessageAndKeyboard(Report.CATEGORY_ERROR, Keyboard.replyKeyboard.REPORT));
                }
            }
            default -> {
                bot.updateBotState(State.ReportMenu);
                messages.add(message.sendMessageAndKeyboard(Report.CATEGORY, Keyboard.replyKeyboard.REPORT));
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}