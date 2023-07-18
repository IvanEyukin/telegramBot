import TelegramBot.FinanceBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class TelegramBotApp {
    public static void main(String[] args) throws Exception {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FinanceBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}