package bot.message;

import LibBaseDto.DtoBaseUser.UserInfo;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public final class BotMessage {
    UserInfo userInfo;
    State botState;
    State previousBotState;
    String userMessageText;
    String callbackData;
    BigDecimal financeSum;
    String financeCategory;
    String financeSubCategory;
    String comment;
    Integer previousBotMessageId;
    Boolean messageHasInLineKeyboaard = false;
    List<SendMessage> messages;
    Map<Long, String> adminNotificationMessages;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public State getSession() {
        return botState;
    }

    public void setSession(State botState) {
        this.botState = botState;
    }

    public State getPreviousBotState() {
        return previousBotState;
    }

    public void setPreviousBotState(State previousBotState) {
        this.previousBotState = previousBotState;
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

    public BigDecimal getFinanceSum() {
        return financeSum;
    }

    public void setFinanceSum(BigDecimal financeSum) {
        this.financeSum = financeSum;
    }

    public String getFinanceCategory() {
        return financeCategory;
    }

    public void setFinanceCategory(String financeCategory) {
        this.financeCategory = financeCategory;
    }

    public String getFinanceSubCategory() {
        return financeSubCategory;
    }

    public void setFinanceSubCategory(String financeSubCategory) {
        this.financeSubCategory = financeSubCategory;
    }

    public Integer getPreviousBotMessageId() {
        return previousBotMessageId;
    }

    public void setPreviousBotMessageId(Integer previousBotMessageId) {
        this.previousBotMessageId = previousBotMessageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getMessageHasInLineKeyboaard() {
        return messageHasInLineKeyboaard;
    }

    public void setMessageHasInLineKeyboaard(Boolean messageHasInLineKeyboaard) {
        this.messageHasInLineKeyboaard = messageHasInLineKeyboaard;
    }

    public void updateBotState(State botState) {
        this.previousBotState = getSession();
        this.botState = botState;
    }

    public Map<Long, String> getAdminNotificationMessages() {
        return adminNotificationMessages;
    }

    public void setAdminNotificationMessages(Map<Long, String> adminNotificationMessages) {
        this.adminNotificationMessages = adminNotificationMessages;
    }
}