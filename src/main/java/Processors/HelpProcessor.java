package Processors;

import BotFSM.BotState;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HelpProcessor {

    public BotMessage getHelp(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (botMessage.getBotState()) {
            case HelpMenu -> {
                if (keyboardMessage.getHelpMenuButton().contains(botMessage.getUserMessageText())) {
                    if (botMessage.getUserMessageText().contains("О боте")) {
                        messages.add(sendMessage.sendMessage(botMessage.helpBotInfo));
                    } else if (botMessage.getUserMessageText().contains("Как бот работает с информацией")) {
                        messages.add(sendMessage.sendMessageAndInline(botMessage.helpInfoQuestion, keyboardMessage.getHelpButtons()));
                        botMessage.updateBotState(BotState.WaitCallbackHelp);
                    }
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(botMessage.helpError, keyboardMessage.getHelpMenuButton()));
                }
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(String.format(botMessage.helpStart, botMessage.getUserInfo().getUser()) , keyboardMessage.getHelpMenuButton()));
                botMessage.updateBotState(BotState.HelpMenu);
            }
        }

        botMessage.setMessages(messages);

        return botMessage;

    }
    
}