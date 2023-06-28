package Route;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseBot.BotSetting;
import LibBaseDto.DtoBaseUser.UserMassage;
import LibBaseDto.DtoBaseUser.AdminCommand;
import LibBaseDto.DtoBaseUser.UserCommand;
import TelegramBot.BotSendMessage;
import Processors.AdminProcessor;
import Processors.ExpensesProcessor;
import Processors.HelpProcessor;
import Processors.IncomeProcessor;
import Processors.ReportProcessor;
import BotFSM.BotState;

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
        List<SendMessage> messages = new ArrayList<>();

        if (botMessage.getUserMessageText().equals(UserMassage.start) || botMessage.getUserMessageText().equals(UserCommand.start)) {

            botMessage.setFinanceSum(null);
            botMessage.setFinanceCategory(null);
            botMessage.setFinanceSubCategory(null);
            botMessage.updateBotState(BotState.mainMenu);

            messages.add(sendMessage.sendMessage(String.format(botMessage.greeting, botMessage.getUserInfo().getUser())));
            messages.add(sendMessage.sendMessage(botMessage.mainMenuQuestion));
        } else if (botMessage.getUserInfo().getId() == BotSetting.creatorId && botMessage.getUserMessageText().equals(AdminCommand.start) || botMessage.getBotState() == BotState.AdminMenu || botMessage.getPreviousBotState() == BotState.AdminMenu) {
            botMessage = admin.adminMenu(botMessage);
            messages = botMessage.getMessages();
        } else if (UserCommand.UserComand.containsKey(botMessage.getUserMessageText()) || botMessage.getFinanceCategory() != null) {

            if (botMessage.getFinanceCategory() == null || UserCommand.UserComand.containsKey(botMessage.getUserMessageText())) { 
                botMessage.setFinanceCategory(UserCommand.UserComand.get(botMessage.getUserMessageText()));
                botMessage.setBotState(BotState.mainMenu);
            }

            // fix ситуации, когда бот ждет от пользователя нажатие ктопок в меню сохранения суммы, а пользователь вводит еще цифры.
            if (botMessage.getBotState() == BotState.WaitCallbackFinance) {
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
            }
        } else {
            messages.add(sendMessage.sendMessage(botMessage.error));
        }



        botMessage.setMessages(messages);

        return botMessage;

    }

}