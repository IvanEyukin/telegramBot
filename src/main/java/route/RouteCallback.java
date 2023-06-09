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
        String callBackData = botMessage.getLastMessageCallback().getData();
        Message message = botMessage.getLastMessageCallback().getMessage();

        if (callBackData.equals(keyboardMessage.getDeleteButton().getCallBack())) {
            messages.add(routeMessage.sendMessage(message, botMessage.delete.concat(botMessage.getLastMessage().getText())));
        } else if (callBackData.equals(keyboardMessage.getAddButton().getCallBack())) {
            messages.add(routeMessage.sendMessage(message, botMessage.add));
        }

        botMessage.setMessages(messages);

        return botMessage;

    }

}