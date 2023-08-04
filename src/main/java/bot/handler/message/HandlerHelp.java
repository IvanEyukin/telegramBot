package bot.handler.message;

import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Help;
import bot.message.send.MessageBuilder;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerHelp {

    Bot bot;
    List<SendMessage> messages = new ArrayList<SendMessage>();
    MessageBuilder message = new MessageBuilder();

    public HandlerHelp(Bot bot) {
        this.bot = bot;
    }

    public Bot getHelp() {
        switch (bot.getState()) {
            case HelpMenu -> {
                if (Keyboard.replyKeyboard.HELP.contains(bot.getUserMessageText())) {
                    if (bot.getUserMessageText().contains(Keyboard.replyKeyboard.HELP.get(0))) {
                        messages.add(message.sendMessageAndKeyboard(Help.INFO_BOT, Keyboard.replyKeyboard.HELP));
                    } else if (bot.getUserMessageText().contains(Keyboard.replyKeyboard.HELP.get(1))) {
                        bot.setMessageHasInLineKeyboaard(true);
                        bot.updateBotState(State.InformationRetentionQuestionsSelection);
                        messages.add(message.sendMessageAndInline(Help.INFO_QUESTION, Keyboard.help));
                    }
                } else {
                    messages.add(message.sendMessageAndKeyboard(Help.ERROR, Keyboard.replyKeyboard.HELP));
                }
            }
            default -> {
                messages.add(message.sendMessageAndKeyboard(String.format(Help.START, bot.getUser().getUser()), Keyboard.replyKeyboard.HELP));
                bot.updateBotState(State.HelpMenu);
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}