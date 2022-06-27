package prizedrowtelegrambot.telegram.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import prizedrowtelegrambot.enums.ButtonAction;
import prizedrowtelegrambot.enums.ButtonNameEnum;
import prizedrowtelegrambot.dtos.ButtonActionDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {
    public InlineKeyboardMarkup getInlineMessageButtons(String donateId) {
        final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        final ButtonActionDto accept = new ButtonActionDto(donateId, ButtonAction.ACCEPT);
        final ButtonActionDto decline = new ButtonActionDto(donateId, ButtonAction.DECLINE);
        keyboard.add(getButton(ButtonNameEnum.GET_APPROVED_BUTTON.getButtonName(), accept.toString()));
        keyboard.add(getButton(ButtonNameEnum.GET_DECLINE_BUTTON.getButtonName(), decline.toString()));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
