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

    public EditMessageReplyMarkup updateMessage(Long chatId, Integer messageId) {

        EditMessageReplyMarkup updateMesasge = new EditMessageReplyMarkup();
        updateMesasge.setChatId(chatId);
        updateMesasge.setMessageId(messageId);
        updateMesasge.setReplyMarkup(null);

        return updateMesasge;

    }

    public BotMessage routeMessageProcessor(BotMessage botMessage) {

        UserMassage userMassage = new UserMassage();
        UserCommand userCommand = new UserCommand();

        List<SendMessage> messages = new ArrayList<>();
        String messageText = botMessage.getMessage().getText();
        BigDecimal number;

        if (messageText.equals(userMassage.start) || messageText.equals(userCommand.start)) {

            messages.add(sendMessage(botMessage.getMessage(), String.format(botMessage.greeting, botMessage.getMessage().getChat().getFirstName())));
            messages.add(sendMessageAndKeyboard(botMessage.getMessage(), botMessage.category, keyboardMessage.getKeyboardType().classic));

        } else if (keyboardMessage.getClassicButton().contains(messageText)) {

            messages.add(sendMessage(botMessage.getMessage(), botMessage.finance.concat(messageText)));
            botMessage.setFinanceCategory(messageText);

        } else if (messageText.matches(Parser.regNumberValid) || messageText.matches(Parser.regNumberNoValid)) {
                    
            if (botMessage.getFinanceCategory() != null) {       

                number = Parser.parseIntToString(messageText);
                switch (number.compareTo(new BigDecimal("0"))) {
                    case (-1):
                        messages.add(sendMessage(botMessage.getMessage(), botMessage.negativeNumber));
                        break;
                    case (0):
                        messages.add(sendMessage(botMessage.getMessage(), botMessage.zeroNumber));
                        break;
                    case (1):

                        if (botMessage.getFinanceSum() == null || !botMessage.getFinanceCategory().equals(botMessage.getLastFinanceCategory())) {

                            botMessage.setFinanceSum(number);
                            botMessage.setLastFinanceCategory(botMessage.getFinanceCategory());
                            
                        } else {
                            botMessage.setFinanceSum(number.add(botMessage.getFinanceSum()));
                        }

                        botMessage.setLastMessage(botMessage.getMessage());
                        messages.add(sendMessageAndKeyboard(botMessage.getMessage(), String.format(botMessage.saveQuestion, botMessage.getFinanceSum()), keyboardMessage.getKeyboardType().inLine));

                        System.out.print("\n" + botMessage.getFinanceCategory() + "\n");
                        System.out.print(botMessage.getLastFinanceCategory() + "\n");
                        System.out.print(botMessage.getFinanceSum() + "\n");
                        System.out.print(botMessage.getLastMessageCallback() + "\n");

                        break;

                }

            } else {
                messages.add(sendMessageAndKeyboard(botMessage.getMessage(), botMessage.categoryError, keyboardMessage.getKeyboardType().classic));
            }

        } else {
            messages.add(sendMessageAndKeyboard(botMessage.getMessage(), botMessage.error, keyboardMessage.getKeyboardType().classic));
        }

        botMessage.setMessages(messages);

        return botMessage;

    }

}