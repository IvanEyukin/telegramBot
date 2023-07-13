package bot.dto;

import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class Bot {

    User user;
    State state;
    State previousState;
    String userMessageText;
    String callbackData;
    BigDecimal sum;
    String category;
    String subCategory;
    String comment;
    Integer botMessageId;
    Boolean messageHasInLineKeyboaard = false;
    List<SendMessage> messages;
    Map<Long, String> adminNotificationMessages;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public State getPreviousState() {
        return previousState;
    }
    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public void updateBotState(State state) {
        this.previousState = getState();
        this.state = state;
    }

    public String getUserMessageText() {
        return userMessageText;
    }
    public void setUserMessageText(String userMessageText) {
        this.userMessageText = userMessageText;
    }

    public String getCallbackData() {
        return callbackData;
    }
    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    public List<SendMessage> getMessages() {
        return messages;
    }
    public void setMessages(List<SendMessage> messages) {
        this.messages = messages;
    }

    public BigDecimal getSum() {
        return sum;
    }
    public void setSum(BigDecimal sum) {
        this.sum = sum;
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

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getBotMessageId() {
        return botMessageId;
    }
    public void setBotMessageId(Integer botMessageId) {
        this.botMessageId = botMessageId;
    }

    public Boolean getMessageHasInLineKeyboaard() {
        return messageHasInLineKeyboaard;
    }
    public void setMessageHasInLineKeyboaard(Boolean messageHasInLineKeyboaard) {
        this.messageHasInLineKeyboaard = messageHasInLineKeyboaard;
    }

    public Map<Long, String> getAdminNotificationMessages() {
        return adminNotificationMessages;
    }
    public void setAdminNotificationMessages(Map<Long, String> adminNotificationMessages) {
        this.adminNotificationMessages = adminNotificationMessages;
    }
}