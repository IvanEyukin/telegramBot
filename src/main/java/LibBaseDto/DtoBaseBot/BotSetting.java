package LibBaseDto.DtoBaseBot;

public class BotSetting {

    String name;
    int creatorId;
    String token;
    String dbPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name =  name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token =  token;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId =  creatorId;
    }

    public String getDbPath() {
        return dbPath;
    }
        
    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

}