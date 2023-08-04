package bot.handler.callback;

import bot.command.UserCommand;
import bot.database.ReportDatabase;
import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Finance;
import bot.message.send.MessageBuilder;
import bot.processors.CheckUserInDatabase;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerFinance {

    Bot bot;
    MessageBuilder message = new MessageBuilder();
    List<SendMessage> messages = new ArrayList<SendMessage>();
    ReportDatabase database = new ReportDatabase();

    private final Boolean save;
    private final Boolean delete;

    public HandlerFinance(Bot bot) {
        this.bot = bot;
        this.save = bot.getCallbackData().equals(Keyboard.finance.get(0).getCallbackData()) == true ? true : false;
        this.delete = bot.getCallbackData().equals(Keyboard.finance.get(1).getCallbackData()) == true ? true : false;
    }

    public Bot handlerStart() {
        if (save) {
            bot = processSave(bot);
        } else if (delete) {
            bot = processDelete(bot);
        }
        return bot;
    }

    private Bot processSave(Bot bot) {
        List<String> keyboard;
        CheckUserInDatabase checkUserInDatabase = new CheckUserInDatabase();
        checkUserInDatabase.checkUser(bot.getUser());
        if (bot.getCategory().equals(UserCommand.expenses)) {
            bot.updateBotState(State.ExpensesMenu);
            keyboard = Keyboard.replyKeyboard.EXPENSES;
            database.insertFinance(bot, database.tableExpenses);
        } else {
            bot.updateBotState(State.IncomeMenu);
            keyboard = Keyboard.replyKeyboard.INCOME;
            database.insertFinance(bot, database.tableIncome);
        }
        messages.add(message.sendMessageAndKeyboard(String.format(Finance.SAVE, bot.getSum()), keyboard));
        bot.setMessages(messages);
        bot.setSubCategory(null);

        return bot;
    }

    private Bot processDelete(Bot bot) {
        messages.add(message.sendMessage(String.format(Finance.DELETE, bot.getSum().toEngineeringString())));
        bot.setMessages(messages);
        bot.setSum(null);
        bot.updateBotState(bot.getPreviousState());

        return bot;
    } 
}