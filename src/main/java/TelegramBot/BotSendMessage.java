package TelegramBot;

import LibBaseDto.DtoBaseKeyboard.Keyboard;
import LibBaseDto.DtoBaseKeyboard.KeyboardInLineButton;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.List;


public class BotSendMessage {

    KeyboardMessage keyboardMessage = new KeyboardMessage();

    public SendMessage sendMessage(String textToSend) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(textToSend);

        return sendMessage;

    }

    public SendMessage sendMessageAndKeyboard(String textToSend, List<String> keyboardButton) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(Keyboard.getKeyboardMarkup(keyboardButton));

        return sendMessage;

    }

    public SendMessage sendMessageAndInline(String textToSend, List<KeyboardInLineButton> keyboardButton) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(Keyboard.getInlineMessageButtons(keyboardButton));

        return sendMessage;

    }

    public EditMessageReplyMarkup updateMessage(Message message) {

        EditMessageReplyMarkup updateMesasge = new EditMessageReplyMarkup();
        updateMesasge.setMessageId(message.getMessageId());
        updateMesasge.setReplyMarkup(null);

        return updateMesasge;

    }

    public EditMessageReplyMarkup updateMessage(Long chatId, Integer messageId) {

        EditMessageReplyMarkup updateMesasge = new EditMessageReplyMarkup();
        updateMesasge.setMessageId(messageId);
        updateMesasge.setReplyMarkup(null);

        return updateMesasge;

    }
    
}