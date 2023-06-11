package route;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import Utils.BotSendMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.ArrayList;
import java.util.List;


public class RouteCallback {

    KeyboardMessage keyboardMessage = new KeyboardMessage();
    BotSendMessage sendMessage = new BotSendMessage();

    public BotMessage routeCallbacProcessor(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        String callBackData = botMessage.getPreviousMessageCallback().getData();
        Message message = botMessage.getPreviousMessageCallback().getMessage();

        if (callBackData.equals(keyboardMessage.getDeleteButton().getCallBack())) {
            messages.add(sendMessage.sendMessage(message, botMessage.delete.concat(botMessage.getFinanceSum().toEngineeringString())));
        } else if (callBackData.equals(keyboardMessage.getSaveButton().getCallBack())) {

            List<String> keyboard;
            if (botMessage.getFinanceCategory().equals("Расходы")) {
                keyboard = keyboardMessage.getExpensesMenuButton();
            } else {
                keyboard = keyboardMessage.getIncomeMenuButton();
            }

            messages.add(sendMessage.sendMessageAndKeyboard(message, String.format(botMessage.save, botMessage.getFinanceSum()), keyboardMessage.getKeyboardType().classic, keyboard));
            botMessage.setFinanceSubCategory(null);

        }
        
        botMessage.setFinanceSum(null);
        botMessage.setMessages(messages);

        return botMessage;

    }

}