package TelegramBot;

import bot.keyboard.inline.InLineKeyboard;
import bot.keyboard.reply.ReplyKeyboard;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;


public class BotSendMessage {

    public SendMessage sendMessage(String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(textToSend);

        return sendMessage;
    }

    public SendMessage sendMessageAndKeyboard(String textToSend, List<String> keyboardButton) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(ReplyKeyboard.getKeyboardMarkup(keyboardButton));

        return sendMessage;
    }

    public SendMessage sendMessageAndInline(String textToSend, List<InlineKeyboardButton> keyboardButton) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(InLineKeyboard.setKeyboard(keyboardButton));

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