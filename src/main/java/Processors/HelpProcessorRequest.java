package Processors;

import TelegramBot.BotSendMessage;
import bot.keyboard.Keyboard;
import bot.message.BotMessage;
import bot.message.Help;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HelpProcessorRequest {

    public BotMessage getHelpInfo(BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        if (botMessage.getCallbackData().equals(Keyboard.help.get(0).getCallbackData())) {
            messages.add(sendMessage.sendMessageAndInline(Help.INFO_SAVE, Keyboard.help));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(Keyboard.help.get(1).getCallbackData())) {
            messages.add(sendMessage.sendMessageAndInline(Help.SAVE, Keyboard.help));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(Keyboard.help.get(2).getCallbackData())) {
            messages.add(sendMessage.sendMessageAndInline(Help.DELETE, Keyboard.help));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(Keyboard.help.get(3).getCallbackData())) {
            messages.add(sendMessage.sendMessageAndInline(Help.WRITE, Keyboard.help));
            botMessage.setMessageHasInLineKeyboaard(true);
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}