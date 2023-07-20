package bot.handler.callback;

import TelegramBot.BotSendMessage;
import bot.command.UserCommand;
import bot.database.ReportDatabase;
import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.message.Finance;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerFinance {

    Bot bot;
    BotSendMessage sendMessage = new BotSendMessage();
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

    private Boolean checkNeedUpdate(String oldData, String newData) {
        if (oldData != null && !oldData.equals(newData)) {
            return true;
        } else {
            return false;
        }
    }

    private void checkUser(User user) {
        User userDb = database.selectUser(user);
        if (!userDb.getIsInDatabase()) {
            database.insertUser(user);
        } else if (checkNeedUpdate(userDb.getName(), user.getName()) || checkNeedUpdate(userDb.getFirstName(), user.getFirstName()) || checkNeedUpdate(userDb.getLastName(), user.getLastName())) {
            database.updateUser(user);
        }
    }

    private Bot processSave(Bot bot) {
        List<String> keyboard;
        
        checkUser(bot.getUser());
        if (bot.getCategory().equals(UserCommand.expenses)) {
            bot.updateBotState(State.ExpensesMenu);
            keyboard = Keyboard.replyKeyboar.EXPENSES;
            database.insertFinance(bot, database.tableExpenses);
        } else {
            bot.updateBotState(State.IncomeMenu);
            keyboard = Keyboard.replyKeyboar.INCOME;
            database.insertFinance(bot, database.tableIncome);
        }
        messages.add(sendMessage.sendMessageAndKeyboard(String.format(Finance.SAVE, bot.getSum()), keyboard));
        bot.setMessages(messages);
        bot.setSubCategory(null);

        return bot;
    }

    private Bot processDelete(Bot bot) {
        messages.add(sendMessage.sendMessage(String.format(Finance.DELETE, bot.getSum().toEngineeringString())));
        bot.setMessages(messages);
        bot.setSum(null);
        bot.updateBotState(bot.getPreviousState());

        return bot;
    } 
}