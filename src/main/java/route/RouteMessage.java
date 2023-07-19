package Route;

import TelegramBot.BotSendMessage;
import Processors.AdminProcessor;
import Processors.ExpensesProcessor;
import Processors.HelpProcessor;
import Processors.IncomeProcessor;
import Processors.ReportProcessor;
import Processors.SettingProcessor;
import bot.command.AdminCommand;
import bot.command.UserCommand;
import bot.entitie.Bot;
import bot.message.Global;
import bot.setting.Setting;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class RouteMessage {

    public Bot routeMessageProcessor(Bot bot) {

        BotSendMessage sendMessage = new BotSendMessage();
        ExpensesProcessor expenses = new ExpensesProcessor();
        IncomeProcessor income = new IncomeProcessor();
        ReportProcessor report = new ReportProcessor();
        AdminProcessor admin = new AdminProcessor();
        HelpProcessor help = new HelpProcessor();
        SettingProcessor setting = new SettingProcessor();
        List<SendMessage> messages = new ArrayList<>();

        if (bot.getUserMessageText().equals(UserCommand.commandStart) || bot.getUserMessageText().equals(UserCommand.start)) {

            bot.setSum(null);
            bot.setCategory(null);
            bot.setSubCategory(null);
            bot.updateBotState(State.Start);

            messages.add(sendMessage.sendMessage(String.format(Global.GREETING, bot.getUser().getUser())));
            messages.add(sendMessage.sendMessage(Global.MENU));
        } else if (bot.getUser().getId() == Setting.creatorId && bot.getUserMessageText().equals(AdminCommand.start) || bot.getState() == State.AdminMenu || bot.getPreviousState() == State.AdminMenu) {
            bot = admin.adminMenu(bot);
            messages = bot.getMessages();
        } else if (UserCommand.UserComand.containsKey(bot.getUserMessageText()) || bot.getCategory() != null) {

            if (bot.getCategory() == null || UserCommand.UserComand.containsKey(bot.getUserMessageText())) { 
                bot.setCategory(UserCommand.UserComand.get(bot.getUserMessageText()));
                bot.setState(State.Start);
            }

            // fix ситуации, когда бот ждет от пользователя нажатие ктопок в меню сохранения суммы, а пользователь вводит еще цифры.
            if (bot.getState() == State.WaitCallbackSaveOrDelete) {
                bot.updateBotState(bot.getPreviousState());
            }
            
            switch (bot.getCategory()) {
                case (UserCommand.expenses) -> {
                    bot = expenses.getExpenses(bot);
                    messages = bot.getMessages();
                }
                case (UserCommand.income) -> {
                    bot = income.getIncome(bot);
                    messages = bot.getMessages();
                }
                case (UserCommand.report) -> {
                    bot = report.getReport(bot);
                    messages = bot.getMessages();
                }
                case (UserCommand.help) -> {
                    bot = help.getHelp(bot);
                    messages = bot.getMessages();
                }
                case (UserCommand.setting) -> {
                    bot = setting.getSetting(bot);
                    messages = bot.getMessages();
                }
            }
        } else {
            messages.add(sendMessage.sendMessage(Global.ERROR));
        }
        bot.setMessages(messages);

        return bot;
    }
}