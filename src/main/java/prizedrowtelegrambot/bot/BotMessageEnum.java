package prizedrowtelegrambot.bot;

public enum BotMessageEnum {

    HELP_MESSAGE("\uD83D\uDC4B Привіт,  це Maria's prize-draw bot " +
            "Оберіть як вам зручно зробити донат\n\n" +
            "Needs default hell text"
          ),
    PAYMENT1_MESSAGE("Зробіть донат на карту 111111111111111111, и після цого введіть сумму у полі нижче"),

    AFTER_PAYMENT_MESSAGE("Дякуємо. Після перевірки вам прийде повідомлення про нарахування квітків"),
    NON_COMMAND_MESSAGE("Невідома команда або невірний формат ввода суми: повиинні бути лише цифри"),
    EXCEPTION_WHAT_THE_FUCK("Something went wrong");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
