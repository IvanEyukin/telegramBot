package Processors;

import TelegramBot.BotSendMessage;
import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Help;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HelpProcessor {

    public Bot getHelp(Bot bot) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (bot.getState()) {
            case HelpMenu -> {
                if (Keyboard.replyKeyboar.HELP.contains(bot.getUserMessageText())) {
                    if (bot.getUserMessageText().contains("О боте")) {
                        messages.add(sendMessage.sendMessageAndKeyboard(Help.INFO_BOT, Keyboard.replyKeyboar.HELP));
                    } else if (bot.getUserMessageText().contains("Как бот работает с информацией")) {
                        messages.add(sendMessage.sendMessageAndInline(Help.INFO_QUESTION, Keyboard.help));
                        bot.setMessageHasInLineKeyboaard(true);
                        bot.updateBotState(State.InformationRetentionQuestionsSelection);
                    }
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(Help.ERROR, Keyboard.replyKeyboar.HELP));
                }
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(String.format(Help.START, bot.getUser().getUser()), Keyboard.replyKeyboar.HELP));
                bot.updateBotState(State.HelpMenu);
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}