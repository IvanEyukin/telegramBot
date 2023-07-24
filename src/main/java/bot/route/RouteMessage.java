package bot.route;

import bot.command.AdminCommand;
import bot.command.UserCommand;
import bot.entitie.Bot;
import bot.handler.message.HandlerAdmin;
import bot.handler.message.HandlerReport;
import bot.handler.message.HandlerStart;
import bot.handler.message.finance.HandlerExpenses;
import bot.handler.message.finance.HandlerIncome;
import bot.handler.message.HandlerSetting;
import bot.handler.message.HandlerHelp;
import bot.message.Global;
import bot.message.send.MessageBuilder;
import bot.setting.Setting;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class RouteMessage {

    private Bot checkStateAndCategory(Bot bot) {
        if (bot.getCategory() == null || UserCommand.UserComand.containsKey(bot.getUserMessageText())) { 
            bot.setCategory(UserCommand.UserComand.get(bot.getUserMessageText()));
            bot.setState(State.Start);
        }
        if (bot.getState() == State.WaitCallbackSaveOrDelete) {
            bot.updateBotState(bot.getPreviousState());
        }
        return bot;
    }

    public Bot routeMessageProcessor(Bot bot) {
        Boolean isStartMessage = (bot.getUserMessageText().equals(UserCommand.commandStart) || bot.getUserMessageText().equals(UserCommand.start));
        Boolean isAdminMessage = (bot.getUser().getId() == Setting.creatorId && bot.getUserMessageText().equals(AdminCommand.start) || bot.getState() == State.AdminMenu || bot.getPreviousState() == State.AdminMenu);
        Boolean isMenuMessage = (UserCommand.UserComand.containsKey(bot.getUserMessageText()) || bot.getCategory() != null);

        if (isStartMessage) {
            HandlerStart start = new HandlerStart(bot);
            bot = start.getStart();
        } else if (isAdminMessage) {
            HandlerAdmin admin = new HandlerAdmin(bot);
            bot = admin.adminMenu();
        } else if (isMenuMessage) {
            bot = checkStateAndCategory(bot);
            switch (bot.getCategory()) {
                case (UserCommand.expenses) -> {
                    HandlerExpenses expenses = new HandlerExpenses(bot);
                    bot = expenses.financeProcess();
                }
                case (UserCommand.income) -> {
                    HandlerIncome income = new HandlerIncome(bot);
                    bot = income.financeProcess();
                }
                case (UserCommand.report) -> {
                    HandlerReport report = new HandlerReport(bot);
                    bot = report.getReport();
                }
                case (UserCommand.help) -> {
                    HandlerHelp help = new HandlerHelp(bot);
                    bot = help.getHelp();
                }
                case (UserCommand.setting) -> {
                    HandlerSetting setting = new HandlerSetting(bot);
                    bot = setting.getSetting();
                }
            }
        } else {
            MessageBuilder message = new MessageBuilder();
            List<SendMessage> messages = new ArrayList<>();
            messages.add(message.sendMessage(Global.ERROR));
            bot.setMessages(messages);
        }
        return bot;
    }
}