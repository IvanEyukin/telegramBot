package route;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.Keyboard;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoBaseUser.UserMassage;
import LibBaseDto.DtoBaseUser.UserCommand;
import Utils.Parser;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RouteMessage {

    KeyboardMessage keyboardMessage = new KeyboardMessage();

    public SendMessage sendMessage(Message message, String textToSend) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(textToSend);

        return sendMessage;

    }

    public SendMessage sendMessageAndKeyboard(Message message, String textToSend, String keyboardType) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(textToSend);

        if (keyboardType.equals(keyboardMessage.getKeyboardType().classic)) {
            sendMessage.setReplyMarkup(Keyboard.getKeyboardMarkup());
        } else if (keyboardType.equals(keyboardMessage.getKeyboardType().inLine)) {
            sendMessage.setReplyMarkup(Keyboard.getInlineMessageButtons());
        }

        return sendMessage;

    }

    public EditMessageReplyMarkup updateMessage(Message message) {

        EditMessageReplyMarkup updateMesasge = new EditMessageReplyMarkup();
        updateMesasge.setChatId(message.getChatId());
        updateMesasge.setMessageId(message.getMessageId());
        updateMesasge.setReplyMarkup(null);

        return updateMesasge;

    }

    public BotMessage routeMessageProcessor(Message message) {

        UserMassage userMassage = new UserMassage();
        UserCommand userCommand = new UserCommand();
        BotMessage botMessage = new BotMessage();

        List<SendMessage> messages = new ArrayList<>();
        String messageText = message.getText();
        BigDecimal number;

        if (messageText.equals(userMassage.start) || messageText.equals(userCommand.start)) {

            messages.add(sendMessage(message, String.format(botMessage.greeting, message.getChat().getFirstName())));
            messages.add(sendMessageAndKeyboard(message, botMessage.category, keyboardMessage.getKeyboardType().classic));

        } else if (keyboardMessage.getClassicButton().contains(messageText)) {
            messages.add(sendMessage(message, botMessage.finance.concat(messageText)));
        } else if (messageText.matches("((-|\\+)?[0-9]+(\\,[0-9]+)?)+")
                || messageText.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {

            number = Parser.parseIntToString(messageText);
            switch (number.compareTo(new BigDecimal("0"))) {
                case (-1):
                    messages.add(sendMessage(message, botMessage.negativeNumber));
                    break;
                case (0):
                    messages.add(sendMessage(message, botMessage.zeroNumber));
                    break;
                case (1):
                    message.setText(number.toEngineeringString());
                    botMessage.setLastMessage(message);
                    messages.add(sendMessageAndKeyboard(message, String.format(botMessage.save, number), keyboardMessage.getKeyboardType().inLine));
                    break;
            }

        } else {
            messages.add(sendMessageAndKeyboard(message, botMessage.error, keyboardMessage.getKeyboardType().classic));
        }

        botMessage.setMessages(messages);

        return botMessage;

    }

}