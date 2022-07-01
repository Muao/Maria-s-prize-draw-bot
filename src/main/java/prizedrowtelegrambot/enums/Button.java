package prizedrowtelegrambot.enums;

import java.util.stream.Stream;

public enum Button {
    RESTART("/restart"),
    START("/start"),
    REGISTER("Зробити донат та зареєструватися у розіграші"),
    ACCEPT_PAYMENT("Approve Payment"),
    DECLINE_PAYMENT("Decline Payment"),
    USER_PAYMENT_CONFIRMATION("Я підтверджую що зробив донат! Піревірте будьласка!"),

    SEND_15_MIN_REMINDER("Send 15 min reminder"),

    START_DRAW("Start draw"),

    GET_CONFIRMED_USERS_LIST("Get confirmed users list"),

    START_ADMIN_CONFIRMATION("----------------I'm sure! Start action!----------------"),

    SEND_TODAY_REMINDER("Send today reminder (and stop take donates)");

    private final String name;

    Button(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Stream<Button> stream(){
        return Stream.of(Button.values());
    }
}
