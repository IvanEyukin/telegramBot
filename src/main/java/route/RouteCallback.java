package Route;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoBaseUser.UserCommand;
import Processors.HelpProcessorRequest;
import Processors.ReportProcessorRequest;
import TelegramBot.BotSendMessage;
import Database.ReportDatabase;
import BotFSM.BotState;

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
        List<SendMessage> messages = new ArrayList<SendMessage>();

        switch (botMessage.getBotState()) {
            case WaitCallbackFinance -> {
                if (botMessage.getCallbackData().equals(keyboardMessage.getDeleteButton().getCallBack())) {
                    messages.add(sendMessage.sendMessage(String.format(botMessage.delete, botMessage.getFinanceSum().toEngineeringString())));
                    botMessage.setFinanceSum(null);
                    botMessage.updateBotState(botMessage.getPreviousBotState());
                } else if (botMessage.getCallbackData().equals(keyboardMessage.getSaveButton().getCallBack())) {
                    List<String> keyboard;

                    if (database.searchUser(botMessage.getUserInfo()) == false) {
                        database.insertUser(botMessage.getUserInfo());
                    }

                    if (botMessage.getFinanceCategory().equals(UserCommand.expenses)) {
                        botMessage.updateBotState(BotState.ExpensesMenu);
                        keyboard = keyboardMessage.getExpensesMenuButton();
                        database.insertFinance(botMessage, database.tableExpenses);
                    } else {
                        botMessage.updateBotState(BotState.IncomeMenu);
                        keyboard = keyboardMessage.getIncomeMenuButton();
                        database.insertFinance(botMessage, database.tableIncome);
                    }

                    messages.add(sendMessage.sendMessageAndKeyboard(String.format(botMessage.save, botMessage.getFinanceSum()), keyboard));
                    botMessage.setFinanceSubCategory(null);
                }
                botMessage.setMessageHasInLineKeyboaard(false);
            }
            case WaitCallbackReport -> {
                botMessage = requestReport.getReportRequest(botMessage);
                messages = botMessage.getMessages();
                botMessage.setMessageHasInLineKeyboaard(false);
            }
            case WaitCallbackHelp -> {
                botMessage = requestHelp.getHelpInfo(botMessage);
                messages = botMessage.getMessages();
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