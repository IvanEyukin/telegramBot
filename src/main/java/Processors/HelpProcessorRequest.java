package Processors;

import TelegramBot.BotSendMessage;
import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Help;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HelpProcessorRequest {

    public Bot getHelpInfo(Bot bot) {

        BotSendMessage sendMessage = new BotSendMessage();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        if (bot.getCallbackData().equals(Keyboard.help.get(0).getCallbackData())) {
            messages.add(sendMessage.sendMessageAndInline(Help.INFO_SAVE, Keyboard.help));
            bot.setMessageHasInLineKeyboaard(true);
        } else if (bot.getCallbackData().equals(Keyboard.help.get(1).getCallbackData())) {
            messages.add(sendMessage.sendMessageAndInline(Help.SAVE, Keyboard.help));
            bot.setMessageHasInLineKeyboaard(true);
        } else if (bot.getCallbackData().equals(Keyboard.help.get(2).getCallbackData())) {
            messages.add(sendMessage.sendMessageAndInline(Help.DELETE, Keyboard.help));
            bot.setMessageHasInLineKeyboaard(true);
        } else if (bot.getCallbackData().equals(Keyboard.help.get(3).getCallbackData())) {
            messages.add(sendMessage.sendMessageAndInline(Help.WRITE, Keyboard.help));
            bot.setMessageHasInLineKeyboaard(true);
        }
        bot.setMessages(messages);

        return bot;
    }
}