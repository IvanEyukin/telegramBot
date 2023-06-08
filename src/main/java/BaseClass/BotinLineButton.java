package BaseClass;

public class BotinLineButton {

    String name;
    String callBack;

    private void settName(String name) {
        this.name = name;
    }

    private void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public static BotinLineButton setInLineButton(String name, String callBack) {

        BotinLineButton inLineButton = new BotinLineButton();

        inLineButton.settName(name);
        inLineButton.setCallBack(callBack);

        return inLineButton;
    }

}
