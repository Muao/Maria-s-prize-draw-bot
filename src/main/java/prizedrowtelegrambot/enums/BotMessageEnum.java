package prizedrowtelegrambot.enums;

public enum BotMessageEnum {

    INTRO_MESSAGE("""
            Вітаємо тебе, друже, на розіграші баски від Марії Кулієвої!
                                   
            Декілька простих кроків і ти матимеш свої квитки, а тобто шанси
            на виграш. Нагадаємо, що 1 квиток коштує 250 грн, але ти
            можеш придбати скільки забажаєш. Усі отримані гроші,
            окрім 8000 грн на зарплатню та матеріали, ми відправляємо
            Волонтерському угрупуванню від бару “Світч”, яке збирає
            на дрон для захисників. Тому навіть якщо тобі не пощастить
            виграти сексі баску, ти все одно допоможеш країні та
            підтримаєш молоде відчизняне виробництво.
            """),
    TICKETS_AMOUNT_MESSAGE("Напиши кількість квитків, які плануєш придбати (лише цифру)"),

    PAYMENT_MESSAGE("""
            Дякую за твою участь!
            Перекажи будь-ласка суму %d грн на картку 4441114457745725
            """),
    AFTER_PAYMENT_MESSAGE("Дякуємо за твою оплату! Адмін підтвердить твій запит протягом 24 годин максимум."),

    SUCCESS_CONFIRMATION_MESSAGE("""
            Дякуємо тобі за участь!
            Донат на суму %d грн було підтверджено!
            Кількість квітків, що було нараховано: %d
            Номер/и твоїх квітків: %s
                            
            Якщо маєш бажання, можеш поділитися у своїй сторіз нашим
            постом і тим, що приймаєш участь у такому форматі
            допомоги нашим бійцям.
            %s відбудеться онлайн стрім в Інстаграмі, де ми
            обиратимемо переможців. Посилання на стрім з’явиться у боті
            за декілька хвилин до початку.
                            
            Твої персональні результати зможеш побачити також у цьому
            боті вже після стріму.
            """),
    DECLINE_PAYMENT_MESSAGE("""
            Щось трапилось і адмін не побачив, що платіж на суму %d було виконано.
            Зв'яжіться будьласка з адміном %s.
            """),

    DECLINE_PAYMENT_MESSAGE_ADMIN("Payment for %d uah by @%s was declined by @%s"),
    NON_COMMAND_MESSAGE("Невідома команда або невірний формат ввода суми: повиинні бути лише цифри"),
    EXCEPTION_WHAT_THE_FUCK("Something went wrong"),
    SAME_TICKETS_AMOUNT_ALREADY_EXIST("Ви вже купували таку ж кількість квитків"),

    CAN_NOT_GET_DONATE_ENTITY("Can't get Donate entity with id %s"),

    SUCCESS_CONFIRMATION_ADMIN("""
            @%s Confirmed payment.
            Was created %d ticket/s for user @%s with numbers: %s
            """),

    USER_PAYMENT_VALIDATION_DONE_BEFORE("""
            Validation of this payment was held
            by @%s
            Data: %s
            Payment confirm: %b
            """);

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
