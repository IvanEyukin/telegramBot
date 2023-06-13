import LibBaseDto.DtoBaseBot.Bot;
import Utils.Parser;

import org.json.JSONObject;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class App {
    public static void main(String[] args) throws Exception {

        JSONObject setting = Parser.parseJsonFile("BotSetting.json");
        Bot bot = Bot.setBotInJson(setting);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Telegram(bot));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        
    }

}