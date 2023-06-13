package route;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoBaseUser.UserCommand;
import LibBaseDto.DtoBaseUser.UserInfo;
import LibBaseDto.DtoReport.BaseReport;
import Utils.BotSendMessage;
import processor.ReportMessageProcessor;
import Database.BotDatabase;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RouteCallback {

    KeyboardMessage keyboardMessage = new KeyboardMessage();
    BotSendMessage sendMessage = new BotSendMessage();
    BotDatabase database = new BotDatabase();
    UserInfo userInfo = new UserInfo();
    BaseReport report = new BaseReport();
    ReportMessageProcessor reportMessage = new ReportMessageProcessor();

    public BotMessage routeCallbacProcessor(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        String callBackData = botMessage.getPreviousMessageCallback().getData();
        Message message = botMessage.getPreviousMessageCallback().getMessage();
        LocalDate date = LocalDate.now();

        if (callBackData.equals(keyboardMessage.getDeleteButton().getCallBack())) {
            messages.add(sendMessage.sendMessage(message, botMessage.delete.concat(botMessage.getFinanceSum().toEngineeringString())));
        } else if (callBackData.equals(keyboardMessage.getSaveButton().getCallBack())) {

            List<String> keyboard;
            userInfo.setId(botMessage.getMessage().getChat().getId());
            userInfo.setName(botMessage.getMessage().getChat().getUserName());
            userInfo.setFirstName(botMessage.getMessage().getChat().getFirstName());
            userInfo.setLastName(botMessage.getMessage().getChat().getLastName());

            if (database.searchUser(userInfo) == false) {
                database.insertUser(userInfo);
            }

            if (botMessage.getFinanceCategory().equals(UserCommand.expenses)) {
                keyboard = keyboardMessage.getExpensesMenuButton();
                database.insertFinance(botMessage, database.tableExpenses);
            } else {
                keyboard = keyboardMessage.getIncomeMenuButton();
                database.insertFinance(botMessage, database.tableIncome);
            }

            messages.add(sendMessage.sendMessageAndKeyboard(message, String.format(botMessage.save, botMessage.getFinanceSum()), keyboard));
            botMessage.setFinanceSubCategory(null);

        } else if (callBackData.equals(keyboardMessage.getLastDayButton().getCallBack())) {

            report.setUserId(botMessage.getMessage().getChat().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusDays(1));
            report.setDateTo(date);

            botMessage = reportMessage.getReportMessage(report, database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);
            
        } else if (callBackData.equals(keyboardMessage.getLastWeekButton().getCallBack())) {

            report.setUserId(botMessage.getMessage().getChat().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusWeeks(1));
            report.setDateTo(date);

            botMessage = reportMessage.getReportMessage(report, database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        } else if (callBackData.equals(keyboardMessage.getLastTwoWeekButton().getCallBack())) {

            report.setUserId(botMessage.getMessage().getChat().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusWeeks(2));
            report.setDateTo(date);

            botMessage = reportMessage.getReportMessage(report, database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        } else if (callBackData.equals(keyboardMessage.getLastMonthButton().getCallBack())) {

            report.setUserId(botMessage.getMessage().getChat().getId());
            report.setTableName(database.tableMap.get(botMessage.getFinanceSubCategory()));
            report.setDateFrom(date.minusMonths(1));
            report.setDateTo(date);

            botMessage = reportMessage.getReportMessage(report, database.selectFinance(report), botMessage);
            messages = botMessage.getMessages();
            botMessage.setFinanceSubCategory(null);

        }
        
        botMessage.setFinanceSum(null);
        botMessage.setComment(null);
        botMessage.setMessages(messages);

        return botMessage;

    }

}