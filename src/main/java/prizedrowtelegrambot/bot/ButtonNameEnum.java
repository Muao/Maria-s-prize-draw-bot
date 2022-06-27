package prizedrowtelegrambot.bot;

public enum ButtonNameEnum {
    GET_PAYMENT1_BUTTON("Payment type 1"),
    GET_PAYMENT2_BUTTON("Payment type 2"),
    GET_APPROVED_BUTTON("Approve Payment"),
    GET_DECLINE_BUTTON("Decline Payment");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
