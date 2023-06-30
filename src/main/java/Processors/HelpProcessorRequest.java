package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HelpProcessorRequest {

    public BotMessage getHelpInfo(BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        if (botMessage.getCallbackData().equals(keyboardMessage.getPrivatInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessageAndInline(botMessage.helpInfoSave, keyboardMessage.getHelpButtons()));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getSaveInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessageAndInline(botMessage.helpSaveQuestion, keyboardMessage.getHelpButtons()));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getDeleteInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessageAndInline(botMessage.helpDeleteInfo, keyboardMessage.getHelpButtons()));
            botMessage.setMessageHasInLineKeyboaard(true);
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getWritInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessageAndInline(botMessage.helpWitingQuestion, keyboardMessage.getHelpButtons()));
            botMessage.setMessageHasInLineKeyboaard(true);
        }

        botMessage.setMessages(messages);

        return botMessage;

    }
    
}
