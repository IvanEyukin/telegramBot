package bot.handler.message.finance;

import bot.entitie.Bot;
import bot.message.Finance;
import bot.message.send.MessageBuilder;
import bot.state.State;
import bot.utils.Parser;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerFinance {

    List<SendMessage> messages = new ArrayList<SendMessage>();
    MessageBuilder message = new MessageBuilder();

    public Bot getMenu(Bot bot, List<String> keyboard) {
        if (keyboard.contains(bot.getUserMessageText())) {
            bot.setSubCategory(bot.getUserMessageText());
            if (bot.getSubCategory().equals(keyboard.get(keyboard.size() - 1))) {
                bot.updateBotState(State.WaitingComment);
                messages.add(message.sendMessage(Finance.SAVE_OTHER));
            } else {
                bot.updateBotState(State.WaitingSum);
                messages.add(message.sendMessage(Finance.NUMBER.concat(bot.getUserMessageText())));
            }
        } else {
            messages.add(message.sendMessageAndKeyboard(Finance.CATEGORY_ERROR, keyboard));
        }
        bot.setMessages(messages);

        return bot;
    }

    public Bot getSum(Bot bot) {
        HandlerCalculationSum calculation = new HandlerCalculationSum(bot);
        if (bot.getUserMessageText().matches(Parser.regNumber)) {
            bot = calculation.getCalculatio();
            bot.updateBotState(State.WaitCallbackSaveOrDelete);
        } else {
            messages.add(message.sendMessage(Finance.NUMBER_ERROR));
            bot.setMessages(messages);
        }
        return bot;
    }
}