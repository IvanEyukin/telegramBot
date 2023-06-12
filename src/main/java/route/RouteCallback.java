package route;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoBaseUser.UserCommand;
import LibBaseDto.DtoBaseUser.UserInfo;
import Utils.BotSendMessage;
import Database.BotDatabase;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.ArrayList;
import java.util.List;


public class RouteCallback {

    KeyboardMessage keyboardMessage = new KeyboardMessage();
    BotSendMessage sendMessage = new BotSendMessage();
    BotDatabase database = new BotDatabase();
    UserInfo userInfo = new UserInfo();

    public BotMessage routeCallbacProcessor(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        String callBackData = botMessage.getPreviousMessageCallback().getData();
        Message message = botMessage.getPreviousMessageCallback().getMessage();

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
                database.insertExpenses(botMessage);
            } else {
                keyboard = keyboardMessage.getIncomeMenuButton();
                database.insertIncome(botMessage);
            }

            messages.add(sendMessage.sendMessageAndKeyboard(message, String.format(botMessage.save, botMessage.getFinanceSum()), keyboardMessage.getKeyboardType().classic, keyboard));
            botMessage.setFinanceSubCategory(null);

        }
        
        botMessage.setFinanceSum(null);
        botMessage.setComment(null);
        botMessage.setMessages(messages);

        return botMessage;

    }

}