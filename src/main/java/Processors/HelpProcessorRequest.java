package Processors;

import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
import bot.message.BotMessage;
import bot.message.Help;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HelpProcessorRequest {

    public BotMessage getHelpInfo(BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        if (botMessage.getCallbackData().equals(keyboardMessage.getPrivatInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessageAndInline(Help.INFO_SAVE, keyboardMessage.getHelpButtons()));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getSaveInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessageAndInline(Help.SAVE, keyboardMessage.getHelpButtons()));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getDeleteInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessageAndInline(Help.DELETE, keyboardMessage.getHelpButtons()));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getWritInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessageAndInline(Help.WRITE, keyboardMessage.getHelpButtons()));
            botMessage.setMessageHasInLineKeyboaard(true);
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}