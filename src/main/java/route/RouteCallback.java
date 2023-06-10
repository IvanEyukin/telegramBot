package route;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.ArrayList;
import java.util.List;


public class RouteCallback {

    KeyboardMessage keyboardMessage = new KeyboardMessage();
    RouteMessage routeMessage = new RouteMessage();

    public BotMessage routeCallbacProcessor(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        String callBackData = botMessage.getPreviousMessageCallback().getData();
        Message message = botMessage.getPreviousMessageCallback().getMessage();

        if (callBackData.equals(keyboardMessage.getDeleteButton().getCallBack())) {
            messages.add(routeMessage.sendMessage(message, botMessage.delete.concat(botMessage.getFinanceSum().toEngineeringString())));
        } else if (callBackData.equals(keyboardMessage.getSaveButton().getCallBack())) {

            messages.add(routeMessage.sendMessageAndKeyboard(message, String.format(botMessage.save, botMessage.getFinanceSum()), keyboardMessage.getKeyboardType().classic));
            botMessage.setFinanceCategory(null);

        }
        
        botMessage.setFinanceSum(null);
        botMessage.setMessages(messages);

        return botMessage;

    }

}