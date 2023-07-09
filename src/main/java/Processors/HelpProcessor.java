package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
import bot.state.State;

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
                        messages.add(sendMessage.sendMessageAndKeyboard(botMessage.helpBotInfo, keyboardMessage.getHelpMenuButton()));
                    } else if (botMessage.getUserMessageText().contains("Как бот работает с информацией")) {
                        messages.add(sendMessage.sendMessageAndInline(botMessage.helpInfoQuestion, keyboardMessage.getHelpButtons()));
                        botMessage.setMessageHasInLineKeyboaard(true);
                        botMessage.updateBotState(State.InformationRetentionQuestionsSelection);
                    }
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(botMessage.helpError, keyboardMessage.getHelpMenuButton()));
                }
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(String.format(botMessage.helpStart, botMessage.getUserInfo().getUser()), keyboardMessage.getHelpMenuButton()));
                botMessage.updateBotState(State.HelpMenu);
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}