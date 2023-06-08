package LibBaseDto.DtoBaseKeyboard;

public class KeyboardInLineButton {

    String name;
    String callBack;

    private void settName(String name) {
        this.name = name;
    }

    private void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public String gettName() {
        return name;
    }

    public String getCallBack() {
        return callBack;
    }

    public static KeyboardInLineButton setInLineButton(String name, String callBack) {

        KeyboardInLineButton inLineButton = new KeyboardInLineButton();

        inLineButton.settName(name);
        inLineButton.setCallBack(callBack);

        return inLineButton;

    }

}
