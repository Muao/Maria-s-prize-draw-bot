package prizedrowtelegrambot.enums;

public enum ButtonNameEnum {
    REGISTER("Зробити донат та зареєструватися у розіграші"),
    ACCEPT_PAYMENT("Approve Payment"),
    DECLINE_PAYMENT("Decline Payment"),
    USER_PAYMENT_CONFIRMATION("Я підтверджую що зробив донат! Піревірте будьласка!");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
