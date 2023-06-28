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
            messages.add(sendMessage.sendMessage(botMessage.helpInfoSave));
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getSaveInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessage(botMessage.helpSaveQuestion));
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getDeleteInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessage(botMessage.helpDeleteInfo));
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getWritInfoButton().getCallBack())) {
            messages.add(sendMessage.sendMessage(botMessage.helpWitingQuestion));
        }

        botMessage.setMessages(messages);

        return botMessage;

    }
    
}
