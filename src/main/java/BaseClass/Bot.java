package BaseClass;

import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Bot {

    String name;
    String token;
    int creatorId;
    Message lastMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public static Bot setBotInJson (JSONObject setting) throws Exception {

        Bot bot = new Bot();

        if (setting.get("botName") != null) {
            bot.setName(setting.get("botName").toString());
        }

        if (setting.get("botCreatorId") != null) {
            bot.setCreatorId(Integer.parseInt(setting.get("botCreatorId").toString()));
        } else {
            throw new Exception("Bot creatorId not specified");
        }

        if (setting.get("botToken") != null) {
            bot.setToken(setting.get("botToken").toString());
        } else {
            throw new Exception("Bot token not specified");
        }

        return bot;

    }
    
}