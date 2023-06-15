import LibBaseDto.DtoBaseBot.BotSetting;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class TelegramBotApp {
    public static void main(String[] args) throws Exception {

        BotSetting botSetting = new BotSetting();
        botSetting.setName(System.getenv("name"));
        botSetting.setCreatorId(Integer.parseInt(System.getenv("creatorId")));
        botSetting.setToken(System.getenv("token"));
        botSetting.setDbPath(System.getenv("dbPath"));

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Telegram(botSetting));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}