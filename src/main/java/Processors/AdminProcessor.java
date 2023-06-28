package Processors;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import BotFSM.BotState;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;

public class AdminProcessor {

    public BotMessage adminMenu(BotMessage botMessage) {

        NotificationProcessor notification = new NotificationProcessor();
        List<SendMessage> messages = new ArrayList<SendMessage>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (botMessage.getBotState()) {
            case AdminMenu -> {
                messages.add(sendMessage.sendMessage(botMessage.adminNotification));
                botMessage.updateBotState(BotState.AdminCommandNotification);
            }
            case AdminCommandNotification -> {
                messages.add(sendMessage.sendMessage(botMessage.adminNotificationStart));
                botMessage.setAdminNotificationMessages(notification.startNotification(botMessage.getUserMessageText()));
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(botMessage.adminQuestion, keyboardMessage.getAdminMenuButton()));
                botMessage.updateBotState(BotState.AdminMenu);
            }
        }


        botMessage.setMessages(messages);

        return botMessage;

    }
    
}
