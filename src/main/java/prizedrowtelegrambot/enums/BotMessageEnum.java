package prizedrowtelegrambot.enums;

public enum BotMessageEnum {

    INTRO_MESSAGE(
            "Вітаємо тебе, друже, на розіграші баски від Марії Кулієвої!\n" +
                    "\n" +
                    "Декілька простих кроків і ти матимеш свої квитки, а тобто шанси\n" +
                    "на виграш. Нагадаємо, що 1 квиток коштує 250 грн, але ти\n" +
                    "можеш придбати скільки забажаєш. Усі отримані гроші,\n" +
                    "окрім 8000 грн на зарплатню та матеріали, ми відправляємо\n" +
                    "Волонтерському угрупуванню від бару “Світч”, яке збирає\n" +
                    "на дрон для захисників. Тому навіть якщо тобі не пощастить\n" +
                    "виграти сексі баску, ти все одно допоможеш країні та\n" +
                    "підтримаєш молоде відчизняне виробництво.\n"
    ),

    INTRO_ADMIN_MESSAGE(
            "Hello, %s!\n" +
                    "Now amount of approved donates is: %d"
    ),

    TICKETS_AMOUNT_MESSAGE("Напиши кількість квитків, які плануєш придбати (лише цифру)"),

    PAYMENT_MESSAGE(
            "Дякую за твою участь!\n" +
                    "Перекажи будь-ласка суму %d грн на картку %s\n"
    ),

    AFTER_PAYMENT_MESSAGE("Дякуємо за твою оплату! Адмін підтвердить твій запит протягом 24 годин максимум."),

    SUCCESS_CONFIRMATION_MESSAGE(
            "Дякуємо тобі за участь!\n" +
                    "Донат на суму %d грн було підтверджено!\n" +
                    "Кількість квітків, що було нараховано: %d\n" +
                    "Номер/и твоїх квітків:\n" +
                    "<strong><ins>%s</ins></strong>\n" +
                    "                \n" +
                    "Якщо маєш бажання, можеш поділитися у своїй сторіз нашим\n" +
                    "постом і тим, що приймаєш участь у такому форматі\n" +
                    "допомоги нашим бійцям.\n" +
                    "%s відбудеться онлайн стрім в Інстаграмі, де ми\n" +
                    "обиратимемо переможців. Посилання на стрім з’явиться у боті\n" +
                    "за декілька хвилин до початку.\n" +
                    "                \n" +
                    "Твої персональні результати зможеш побачити також у цьому\n" +
                    "боті вже після стріму.\n"
    ),
    DECLINE_PAYMENT_MESSAGE(
            "Щось трапилось і адмін не побачив, що платіж на суму %d було виконано.\n" +
                    "Зв'яжіться будьласка з адміном %s.\n"
    ),

    DECLINE_PAYMENT_MESSAGE_ADMIN("Payment for %d uah by @%s was declined by @%s"),

    NON_COMMAND_MESSAGE("Невідома команда або невірний формат ввода суми: повиинні бути лише цифри"),

    EXCEPTION_WHAT_THE_FUCK("Something went wrong"),

    SAME_TICKETS_AMOUNT_ALREADY_EXIST("Ви вже купували таку ж кількість квитків"),

    CAN_NOT_GET_DONATE_ENTITY("Can't get Donate entity with id %s"),

    SUCCESS_CONFIRMATION_ADMIN(
            "@%s Confirmed payment.\n" +
                    "Was created %d ticket/s for user @%s with numbers:\n" +
                    "<strong><ins>%s</ins></strong>\n"
    ),

    USER_PAYMENT_VALIDATION_DONE_BEFORE(
            "Validation of this payment was held\n" +
                    "by @%s\n" +
                    "Data: %s\n" +
                    "Payment confirm: %b\n"
    ),

    STOP_TAKING_DONATES_MESSAGE(
            "Вітаємо тебе, %s!\n" +
                    "Нажаль прийом донатів для участі в розігращі\n" +
                    "баски вже завершився. Але ти можеш\n" +
                    "задонатити на картку %s\n" +
                    "Будь впевнений, гроші дійдуть до адресата!"
    ),

    STOP_DRAW_MESSAGE(
            "Вітаємо тебе, %s!\n" +
                    "Нажаль розігращ вже завершено.\n" +
                    "Нам вдалося зібрати %s грн. " +
                    "Усі отримані гроші,\n" +
                    "окрім 8000 грн на зарплатню та матеріали, ми відправили\n" +
                    "Волонтерському угрупуванню від бару “Світч” \n" +
                    "на дрон для захисників.\n" +
                    "Але ти можеш долучитися - кидай гроші  на картку\n" +
                    "%s\n" +
                    "Будь впевнений, гроші дійдуть до адресата!"
    ),

    START_DRAW_MESSAGE(
            "Всі квитки, що беруть участь у розігращі\n" +
                    "\n" +
                    "%s\n" +
                    "\n" +
                    "Розіграш почнеться за 3 секунди..."

    ),

    WINNING_MESSAGE(
            "Вітаємо %s з перемогою у розігращі!\n" +
                    "Виграв твій квиток %s \n" +
                    "Наші майстрині зв’яжуться з тобою особисто задля уточнення " +
                    "твоїх параметрів та домовленостей про доставку.\n" +
                    "\n" +
                    "Доставка Україною: безкоштовна\n" +
                    "Доставка за кордон: за домовленістю, бо багато нюансів"
    ),

    WINNING_ADMIN_MESSAGE(
            "Winner# %d\n" +
                    "User: %s\n" +
                    "Ticket: %s"
    ),

    START_DRAW_CONFIRMATION_MESSAGE("Start the draw?");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
