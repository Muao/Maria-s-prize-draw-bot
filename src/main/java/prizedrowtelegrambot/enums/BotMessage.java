package prizedrowtelegrambot.enums;

public enum BotMessage {

    INTRO_MESSAGE(
            "\uD83D\uDC4B Вітаємо тебе, друже, у боті для розіграшу баски від Марії Кулієвої! \n" +
                    "▶️ Щоб почати роботу бота напишіть команду /start\n" +
                    "Ви маєте можливість купити квиток на розіграш або просто задонатити кошти на пташечку для ЗСУ без участі ✈️\n" +
                    "\n" +
                    "Декілька простих кроків і ти матимеш свої квитки, а тобто шанси на виграш.\n" +
                    "Нагадаємо, що 1️⃣ квиток коштує 2️⃣5️⃣0️⃣ грн, але ти можеш придбати скільки забажаєш.\n" +
                    "\n" +
                    "\uD83D\uDCB8 Усі отримані гроші, окрім 8000 грн на зарплатню майстриням та матеріали, ми відправляємо волонтерському угрупуванню від бару “Світч”, яке збирає на дрон для захисників.\n" +
                    "\n" +
                    "Тому навіть якщо тобі не пощастить виграти сексі баску, ти все одно допоможеш країні та підтримаєш молоде вітчизняне виробництво \uD83E\uDD17\n"
    ),

    INTRO_ADMIN_MESSAGE(
            "Hello, %s!\n" +
                    "Now amount of approved donates is: %d"
    ),

    TICKETS_AMOUNT_MESSAGE("Напиши кількість квитків, які плануєш придбати (лише цифру)"),

    PAYMENT_MESSAGE(
            "Дякую за твою участь!\n" +
                    "\uD83D\uDCB8 Перекажи будь-ласка суму %d грн на картку %s\n"
    ),

    AFTER_PAYMENT_MESSAGE("Дякуємо за твою оплату! ✅ Адмін підтвердить твій запит протягом 24 годин максимум."),

    SUCCESS_CONFIRMATION_MESSAGE(
            "Дякуємо тобі за участь!\n" +
                    "\n" +
                    "\uD83D\uDCB0 Донат на суму %d грн було підтверджено!\n" +
                    "#️⃣ Кількість квитків, що було нараховано: %d\n" +
                    "\n" +
                    "Номер/и твоїх квитків:\n" +
                    "\n" +
                    "<strong><ins>%s</ins></strong>\n" +
                    "\n" +
                    "\uD83D\uDCE2 Якщо маєш бажання, можеш поділитися у своїй сторіз нашим постом і тим, що приймаєш участь у такому форматі допомоги нашим бійцям.\n" +
                    "\n" +
                    "\uD83D\uDCC5 %s відбудеться онлайн стрім в Інстаграмі, де ми обиратимемо переможців. \n" +
                    "\n" +
                    "\uD83D\uDD17 Посилання на стрім з’явиться у боті за 15 хвилин до початку.\n" +
                    "\n" +
                    "\uD83D\uDCCC Твої персональні результати зможеш побачити також у цьому боті вже після стріму.\n"
    ),
    DECLINE_PAYMENT_MESSAGE(
            "\uD83D\uDE31 Щось трапилось і адмін не побачив, що платіж на суму %d було виконано.\n" +
                    "Зв'яжіться будь-ласка з адміном %s.\n"
    ),

    DECLINE_PAYMENT_MESSAGE_ADMIN("Payment for %d uah by @%s was declined by @%s"),

    NON_COMMAND_MESSAGE("Невідома команда або невірний формат ввода суми: повиинні бути лише цифри"),

    EXCEPTION_WHAT_THE_FUCK("Something went wrong"),

    NOT_CHECKED_DONATE_ALREADY_EXIST(
            "Ти вже зробив запит на перевірку доната.\n" +
                    "Незабаром адмін його перевірить."
    ),

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
                    "☹️ Нажаль прийом донатів для участі в розіграші\n" +
                    "баски вже завершився. Але ти можеш\n" +
                    "задонатити на картку %s\n" +
                    "Будь впевнений, гроші дійдуть до адресата! \uD83D\uDCF2"
    ),

    STOP_DRAW_MESSAGE(
            "☹️ Нажаль розіграш вже завершено.\n" +
                    "\uD83D\uDCB0 Нам вдалося зібрати %s грн.\n" +
                    "Усі отримані гроші,\n" +
                    "окрім 8000 грн на зарплатню майстриням та матеріали, ми відправили\n" +
                    "Волонтерському угрупуванню від бару “Світч”\n" +
                    "на дрон для захисників. ✈️\n" +
                    "Але ти можеш долучитися - кидай гроші  на картку\n" +
                    "%s\n" +
                    "Будь впевнений, гроші дійдуть до адресата! \uD83D\uDCF2"
    ),

    START_DRAW_MESSAGE(
            "Всі квитки, що беруть участь у розігращі\n" +
                    "\n" +
                    "%s\n" +
                    "\n" +
                    "Розіграш почнеться за 3 секунди..."

    ),

    WINNING_MESSAGE(
            "\uD83E\uDD73 Вітаємо %s з перемогою у розіграші!\n" +
                    "Виграв твій квиток %s\n" +
                    "\uD83D\uDCE9 Наші майстрині зв’яжуться з тобою особисто задля уточнення\n" +
                    "твоїх параметрів та домовленостей про доставку.\n" +
                    "\n" +
                    "\uD83C\uDDFA\uD83C\uDDE6 Доставка Україною: безкоштовна\n" +
                    "\uD83C\uDF0D Доставка за кордон: за домовленістю, бо багато нюансів\n"
    ),

    WINNING_ADMIN_MESSAGE(
            "Winner# %d\n" +
                    "User: %s\n" +
                    "Ticket: %s"
    ),

    START_DRAW_CONFIRMATION_MESSAGE("Start the draw?"),

    SEND_15_MIN_MESSAGE(
            "\uD83D\uDC4B Привіт! Розіграш почнеться за 15 хвилин.\n" +
                    "\uD83D\uDD17 Чекаємо на тебе! Переходь за посиланням\n" +
                    "%s"
    ),

    SEND_TODAY_REMINDER_MESSAGE(
            "\uD83D\uDC4B Привіт! Розіграш відбудеться сьогодні о %s.\n" +
                    " Чекаємо на тебе! Переходь за посиланням\n" +
                    "%s\n"
    ),
    ALREADY_SENT("Message sent to %d user/s"),

    END_DRAW_ADMIN_MESSAGE(
            "Вітаю! Розіграш виконано. \n" +
                    "Не забудь скинути посилання на стрім"
    ),

    SEND_REMINDER_CONFIRMATION_MESSAGE(
            "You will send reminders for %d user/s. Confirm?"
    ),

    RESTART_DRAW("Draw was restarted");

    private final String message;

    BotMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
