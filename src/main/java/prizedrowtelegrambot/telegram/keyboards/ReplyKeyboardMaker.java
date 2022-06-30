package prizedrowtelegrambot.telegram.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import prizedrowtelegrambot.enums.ButtonNameEnum;


import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {

    public ReplyKeyboardMarkup getUserMenuKeyboard() {
        final KeyboardRow row1 = new KeyboardRow();
        final List<KeyboardRow> keyboard = new ArrayList<>();
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        row1.add(new KeyboardButton(ButtonNameEnum.REGISTER.getButtonName()));
        keyboard.add(row1);
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getAdminKeyboardMarkup() {
        final KeyboardRow row1 = new KeyboardRow();
        final KeyboardRow row2 = new KeyboardRow();
        final List<KeyboardRow> keyboard = new ArrayList<>();
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        row1.add(new KeyboardButton(ButtonNameEnum.SEND_15_MIN_REMINDER.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.GET_CONFIRMED_USERS_LIST.getButtonName()));
        row2.add(new KeyboardButton(ButtonNameEnum.START_DRAW.getButtonName()));
        keyboard.add(row1);
        keyboard.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }
}
