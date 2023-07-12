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
import bot.message.BotMessage;
import bot.message.Global;
import bot.setting.Setting;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class RouteMessage {

    public BotMessage routeMessageProcessor(BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        ExpensesProcessor expenses = new ExpensesProcessor();
        IncomeProcessor income = new IncomeProcessor();
        ReportProcessor report = new ReportProcessor();
        AdminProcessor admin = new AdminProcessor();
        HelpProcessor help = new HelpProcessor();
        SettingProcessor setting = new SettingProcessor();
        List<SendMessage> messages = new ArrayList<>();

        if (botMessage.getUserMessageText().equals(UserCommand.commandStart) || botMessage.getUserMessageText().equals(UserCommand.start)) {

            botMessage.setFinanceSum(null);
            botMessage.setFinanceCategory(null);
            botMessage.setFinanceSubCategory(null);
            botMessage.updateBotState(State.Start);

            messages.add(sendMessage.sendMessage(String.format(Global.GREETING, botMessage.getUserInfo().getUser())));
            messages.add(sendMessage.sendMessage(Global.MENU));
        } else if (botMessage.getUserInfo().getId() == Setting.creatorId && botMessage.getUserMessageText().equals(AdminCommand.start) || botMessage.getSession() == State.AdminMenu || botMessage.getPreviousBotState() == State.AdminMenu) {
            botMessage = admin.adminMenu(botMessage);
            messages = botMessage.getMessages();
        } else if (UserCommand.UserComand.containsKey(botMessage.getUserMessageText()) || botMessage.getFinanceCategory() != null) {

            if (botMessage.getFinanceCategory() == null || UserCommand.UserComand.containsKey(botMessage.getUserMessageText())) { 
                botMessage.setFinanceCategory(UserCommand.UserComand.get(botMessage.getUserMessageText()));
                botMessage.setSession(State.Start);
            }

            // fix ситуации, когда бот ждет от пользователя нажатие ктопок в меню сохранения суммы, а пользователь вводит еще цифры.
            if (botMessage.getSession() == State.WaitCallbackSaveOrDelete) {
                botMessage.updateBotState(botMessage.getPreviousBotState());
            }
            
            switch (botMessage.getFinanceCategory()) {
                case (UserCommand.expenses) -> {
                    botMessage = expenses.getExpenses(botMessage);
                    messages = botMessage.getMessages();
                }
                case (UserCommand.income) -> {
                    botMessage = income.getIncome(botMessage);
                    messages = botMessage.getMessages();
                }
                case (UserCommand.report) -> {
                    botMessage = report.getReport(botMessage);
                    messages = botMessage.getMessages();
                }
                case (UserCommand.help) -> {
                    botMessage = help.getHelp(botMessage);
                    messages = botMessage.getMessages();
                }
                case (UserCommand.setting) -> {
                    botMessage = setting.getSetting(botMessage);
                    messages = botMessage.getMessages();
                }
            }
        } else {
            messages.add(sendMessage.sendMessage(Global.ERROR));
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}