package route;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoBaseUser.UserMassage;
import LibBaseDto.DtoBaseUser.UserCommand;
import processor.ExpensesProcessor;
import processor.IncomeProcessor;
import Utils.BotSendMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class RouteMessage {

    KeyboardMessage keyboardMessage = new KeyboardMessage();

    public BotMessage routeMessageProcessor(BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        ExpensesProcessor expenses = new ExpensesProcessor();
        IncomeProcessor income = new IncomeProcessor();
        UserMassage userMassage = new UserMassage();
        UserCommand userCommand = new UserCommand();

        List<SendMessage> messages = new ArrayList<>();
        String messageText = botMessage.getMessage().getText();

        if (messageText.equals(userMassage.start) || messageText.equals(userCommand.start)) {

            messages.add(sendMessage.sendMessage(botMessage.getMessage(), String.format(botMessage.greeting, botMessage.getMessage().getChat().getFirstName())));
            messages.add(sendMessage.sendMessageAndKeyboard(botMessage.getMessage(), botMessage.mainMenuQuestion, keyboardMessage.getKeyboardType().classic, keyboardMessage.getMainMenuButton()));
            botMessage.setFinanceCategory(null);

        } else if (keyboardMessage.getMainMenuButton().contains(messageText) || botMessage.getFinanceCategory() != null) {

            if (botMessage.getFinanceCategory() == null || (!botMessage.getFinanceCategory().equals(botMessage.getPreviousFinanceCategory()) && keyboardMessage.getMainMenuButton().contains(messageText))) {
                botMessage.setFinanceCategory(messageText);
            }

            switch (botMessage.getFinanceCategory()) {
                case ("Расходы") :
            
                    botMessage = expenses.getExpenses(botMessage);
                    messages = botMessage.getMessages();
                    break;

                case ("Доходы") :

                    botMessage = income.getIncome(botMessage);
                    messages = botMessage.getMessages();
                    break;

                case ("Отчеты") :

                    messages.add(sendMessage.sendMessageAndKeyboard(botMessage.getMessage(), botMessage.develop, keyboardMessage.getKeyboardType().classic, keyboardMessage.getMainMenuButton()));
                    botMessage.setFinanceCategory(null);
                    break;

            }

            botMessage.setPreviousFinanceCategory(botMessage.getPreviousFinanceCategory());

        } else {
            messages.add(sendMessage.sendMessageAndKeyboard(botMessage.getMessage(), botMessage.error, keyboardMessage.getKeyboardType().classic, keyboardMessage.getMainMenuButton()));
        }

        botMessage.setMessages(messages);

        return botMessage;

    }

}