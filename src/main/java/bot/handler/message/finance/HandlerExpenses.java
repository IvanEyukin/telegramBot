package bot.handler.message.finance;

import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Finance;
import bot.message.send.MessageBuilder;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerExpenses extends HandlerFinance {

    Bot bot;
    List<SendMessage> messages = new ArrayList<SendMessage>();
    MessageBuilder message = new MessageBuilder();

    public HandlerExpenses(Bot bot) {
        this.bot = bot;
    }

    public Bot financeProcess() {
        switch (bot.getState()) {
            case ExpensesMenu -> {
                bot = getMenu(bot, Keyboard.replyKeyboard.EXPENSES);
            }
            case WaitingSum -> {
                bot = getSum(bot);
            }
            case WaitingComment -> {
                bot.updateBotState(State.WaitingSum);
                bot.setComment(bot.getUserMessageText());
                messages.add(message.sendMessage(Finance.NUMBER.concat(bot.getSubCategory())));
                bot.setMessages(messages);
            }
            default -> {
                bot.updateBotState(State.ExpensesMenu);
                messages.add(message.sendMessageAndKeyboard(Finance.EXPENSES, Keyboard.replyKeyboard.EXPENSES));
                bot.setMessages(messages);
            }
        }
        return bot;
    }
}