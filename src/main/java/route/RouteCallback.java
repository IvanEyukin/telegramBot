package Route;

import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoBaseUser.UserCommand;
import Processors.HelpProcessorRequest;
import Processors.ReportProcessorRequest;
import Processors.SettingProcessorRequest;
import TelegramBot.BotSendMessage;
import bot.database.ReportDatabase;
import bot.message.BotMessage;
import bot.message.Finance;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class RouteCallback {
    public BotMessage routeCallbacProcessor(BotMessage botMessage) {
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();
        ReportDatabase database = new ReportDatabase();
        ReportProcessorRequest requestReport = new ReportProcessorRequest();
        HelpProcessorRequest requestHelp = new HelpProcessorRequest();
        SettingProcessorRequest requestSetting = new SettingProcessorRequest();
        List<SendMessage> messages = new ArrayList<SendMessage>();

        switch (botMessage.getSession()) {
            case WaitCallbackSaveOrDelete -> {
                if (botMessage.getCallbackData().equals(keyboardMessage.getDeleteButton().getCallBack())) {
                    messages.add(sendMessage.sendMessage(String.format(Finance.DELETE, botMessage.getFinanceSum().toEngineeringString())));
                    botMessage.setFinanceSum(null);
                    botMessage.updateBotState(botMessage.getPreviousBotState());
                } else if (botMessage.getCallbackData().equals(keyboardMessage.getSaveButton().getCallBack())) {
                    List<String> keyboard;

                    if (database.searchUser(botMessage.getUserInfo()) == false) {
                        database.insertUser(botMessage.getUserInfo());
                    }

                    if (botMessage.getFinanceCategory().equals(UserCommand.expenses)) {
                        botMessage.updateBotState(State.ExpensesMenu);
                        keyboard = keyboardMessage.getExpensesMenuButton();
                        database.insertFinance(botMessage, database.tableExpenses);
                    } else {
                        botMessage.updateBotState(State.IncomeMenu);
                        keyboard = keyboardMessage.getIncomeMenuButton();
                        database.insertFinance(botMessage, database.tableIncome);
                    }

                    messages.add(sendMessage.sendMessageAndKeyboard(String.format(Finance.SAVE, botMessage.getFinanceSum()), keyboard));
                    botMessage.setFinanceSubCategory(null);
                }
                botMessage.setMessageHasInLineKeyboaard(false);
            }
            case PeriodSelection -> {
                botMessage = requestReport.getReportRequest(botMessage);
                messages = botMessage.getMessages();
                botMessage.updateBotState(botMessage.getPreviousBotState());
                botMessage.setMessageHasInLineKeyboaard(false);
            }
            case InformationRetentionQuestionsSelection -> {
                botMessage = requestHelp.getHelpInfo(botMessage);
                messages = botMessage.getMessages();
            }
            case ReminderOptionsSelection -> {
                botMessage = requestSetting.setSettingRequest(botMessage);
                messages = botMessage.getMessages();
                botMessage.setMessageHasInLineKeyboaard(false);
                botMessage.updateBotState(State.Start);
            }
            default -> {
            }
        }
        botMessage.setFinanceSum(null);
        botMessage.setComment(null);
        botMessage.setMessages(messages);

        return botMessage;
    }
}