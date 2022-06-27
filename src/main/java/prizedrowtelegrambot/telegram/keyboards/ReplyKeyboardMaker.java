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

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        final KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNameEnum.GET_PAYMENT1_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.GET_PAYMENT2_BUTTON.getButtonName()));

//        KeyboardRow row2 = new KeyboardRow();
//        row2.add(new KeyboardButton(ButtonNameEnum.UPLOAD_DICTIONARY_BUTTON.getButtonName()));
//        row2.add(new KeyboardButton(ButtonNameEnum.HELP_BUTTON.getButtonName()));

        final List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
//        keyboard.add(row2);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
