package LibBaseDto.DtoBaseBot;

import org.json.JSONObject;


public class Bot {

    String name;
    String token;
    int creatorId;
    

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