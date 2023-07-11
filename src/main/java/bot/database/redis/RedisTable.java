package bot.database.redis;


public class RedisTable {

    String key;
    String userName;
    String userFirstName;
    String userLastName;
    String dataTimeMessage;
    String category;
    String subCategory;
    String sum;
    String comment;
    String messageHasKeyboard;
    String previousState;
    String state;
    String previousBotMessageId;
    Boolean sessionHasRedis = false;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getDataTimeMessage() {
        return dataTimeMessage;
    }

    public void setDataTimeMessage(String dataTimeMessage) {
        this.dataTimeMessage = dataTimeMessage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMessageHasKeyboard() {
        return messageHasKeyboard;
    }

    public void setMessageHasKeyboard(String messageHasKeyboard) {
        this.messageHasKeyboard = messageHasKeyboard;
    }

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPreviousBotMessageId() {
        return previousBotMessageId;
    }

    public void setPreviousBotMessageId(String previousBotMessageId) {
        this.previousBotMessageId = previousBotMessageId;
    }

    public Boolean getSessionHasRedis() {
        return sessionHasRedis;
    }

    public void setSessionHasRedis(Boolean sessionHasRedis) {
        this.sessionHasRedis = sessionHasRedis;
    }
}