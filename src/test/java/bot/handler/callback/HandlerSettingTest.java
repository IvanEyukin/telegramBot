package bot.handler.callback;

import bot.entitie.Bot;
import bot.entitie.User;
import bot.message.Setting;
import bot.message.callback.CallbackSetting;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HandlerSettingTest {

    private Bot bot = new Bot();
    private User user = new User();

    @Before
    public void setUser() {
        user.setId((long) 100200300);
        user.setName("Test");
        user.setNotification("all");
        bot.setUser(user);
    }

    @Test
    public void setSettingTest_notificationAll() {
        bot.setCallbackData("NotificationAll");
        HandlerSetting handlerSetting = new HandlerSetting(bot);
        bot = handlerSetting.setSetting();
        assertEquals(Setting.SAVE.concat(CallbackSetting.notification.get("all")), bot.getMessages().get(0).getText());
    }

    @Test
    public void setSettingTest_notificationActive() {
        bot.setCallbackData("NotificationActive");
        HandlerSetting handlerSetting = new HandlerSetting(bot);
        bot = handlerSetting.setSetting();
        assertEquals(Setting.SAVE.concat(CallbackSetting.notification.get("active")), bot.getMessages().get(0).getText());
    }

    @Test
    public void setSettingTest_notificationDisabled() {
        bot.setCallbackData("NotificationDisabled");
        HandlerSetting handlerSetting = new HandlerSetting(bot);
        bot = handlerSetting.setSetting();
        assertEquals(Setting.SAVE.concat(CallbackSetting.notification.get("disabled")), bot.getMessages().get(0).getText());
    }
}