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

        List<SendMessage> messages = new ArrayList<>();
        String messageText = botMessage.getMessage().getText();

        if (messageText.equals(UserMassage.start) || messageText.equals(UserCommand.start)) {

            messages.add(sendMessage.sendMessage(botMessage.getMessage(), String.format(botMessage.greeting, botMessage.getMessage().getChat().getFirstName())));
            messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.mainMenuQuestion));
            botMessage.setFinanceCategory(null);

        } else if (UserCommand.UserComand.containsKey(messageText) || botMessage.getFinanceCategory() != null) {

            if (botMessage.getFinanceCategory() == null || (!botMessage.getFinanceCategory().equals(botMessage.getPreviousFinanceCategory()) && UserCommand.UserComand.containsKey(messageText))) {
                botMessage.setFinanceCategory(UserCommand.UserComand.get(messageText));
            }
            
            switch (botMessage.getFinanceCategory()) {
                case (UserCommand.expenses) :
            
                    botMessage = expenses.getExpenses(botMessage);
                    messages = botMessage.getMessages();
                    break;

                case (UserCommand.income) :

                    botMessage = income.getIncome(botMessage);
                    messages = botMessage.getMessages();
                    break;

                case (UserCommand.report) :

                    messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.develop));
                    botMessage.setFinanceCategory(null);
                    break;

                case (UserCommand.help) :

                    messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.develop));
                    botMessage.setFinanceCategory(null);
                    break;

            }

            botMessage.setPreviousFinanceCategory(botMessage.getPreviousFinanceCategory());

        } else {
            messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.error));
        }

        botMessage.setMessages(messages);

        return botMessage;

    }

}