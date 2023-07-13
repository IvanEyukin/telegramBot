package Route;

import Processors.HelpProcessorRequest;
import Processors.ReportProcessorRequest;
import Processors.SettingProcessorRequest;
import TelegramBot.BotSendMessage;
import bot.command.UserCommand;
import bot.database.ReportDatabase;
import bot.dto.Bot;
import bot.keyboard.Keyboard;
import bot.message.Finance;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class RouteCallback {
    public Bot routeCallbacProcessor(Bot bot) {
        BotSendMessage sendMessage = new BotSendMessage();
        ReportDatabase database = new ReportDatabase();
        ReportProcessorRequest requestReport = new ReportProcessorRequest();
        HelpProcessorRequest requestHelp = new HelpProcessorRequest();
        SettingProcessorRequest requestSetting = new SettingProcessorRequest();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        switch (bot.getState()) {
            case WaitCallbackSaveOrDelete -> {
                if (bot.getCallbackData().equals(Keyboard.finance.get(1).getCallbackData())) {
                    messages.add(sendMessage.sendMessage(String.format(Finance.DELETE, bot.getSum().toEngineeringString())));
                    bot.setSum(null);
                    bot.updateBotState(bot.getPreviousState());
                } else if (bot.getCallbackData().equals(Keyboard.finance.get(0).getCallbackData())) {
                    List<String> keyboard;

                    if (database.searchUser(bot.getUser()) == false) {
                        database.insertUser(bot.getUser());
                    }

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
                    bot.setSubCategory(null);
                }
                bot.setMessageHasInLineKeyboaard(false);
            }
            case PeriodSelection -> {
                bot = requestReport.getReportRequest(bot);
                messages = bot.getMessages();
                bot.updateBotState(bot.getPreviousState());
                bot.setMessageHasInLineKeyboaard(false);
            }
            case InformationRetentionQuestionsSelection -> {
                bot = requestHelp.getHelpInfo(bot);
                messages = bot.getMessages();
            }
            case ReminderOptionsSelection -> {
                bot = requestSetting.setSettingRequest(bot);
                messages = bot.getMessages();
                bot.setMessageHasInLineKeyboaard(false);
                bot.updateBotState(State.Start);
            }
            default -> {
            }
        }
        bot.setSum(null);
        bot.setComment(null);
        bot.setMessages(messages);

        return bot;
    }
}