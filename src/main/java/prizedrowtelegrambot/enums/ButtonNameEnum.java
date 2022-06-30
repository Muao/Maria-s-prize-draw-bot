package prizedrowtelegrambot.enums;

public enum ButtonNameEnum {
    REGISTER("Зробити донат та зареєструватися у розіграші"),
    ACCEPT_PAYMENT("Approve Payment"),
    DECLINE_PAYMENT("Decline Payment"),
    USER_PAYMENT_CONFIRMATION("Я підтверджую що зробив донат! Піревірте будьласка!"),

    SEND_15_MIN_REMINDER("Send 15 min reminder"),

    START_DRAW("Start draw"),

    GET_CONFIRMED_USERS_LIST("Get confirmed users"),

    START_DRAW_CONFIRMATION("----------------I'm sure. Start the draw!----------------");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
