package prizedrowtelegrambot.telegram.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import prizedrowtelegrambot.enums.ButtonAction;
import prizedrowtelegrambot.enums.Button;
import prizedrowtelegrambot.dtos.ButtonActionDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {
    public InlineKeyboardMarkup getPaymentActionInlineButtons(String donateId) {
        final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        final ButtonActionDto accept = new ButtonActionDto(donateId, ButtonAction.ACCEPT);
        final ButtonActionDto decline = new ButtonActionDto(donateId, ButtonAction.DECLINE);
        keyboard.add(getButton(Button.ACCEPT_PAYMENT.getName(), accept.toString()));
        keyboard.add(getButton(Button.DECLINE_PAYMENT.getName(), decline.toString()));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getStartDrawValidationMessage(){
        final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        final ButtonActionDto accept = new ButtonActionDto(null, ButtonAction.START_DRAW_CONFIRMATION);
        keyboard.add(getButton(Button.START_DRAW_CONFIRMATION.getName(), accept.toString()));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getUserPaymentConfirmationInlineButtons(long totalNeedsToPayment) {
        final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        final ButtonActionDto accept = new ButtonActionDto(totalNeedsToPayment, ButtonAction.USER_PAYMENT_CONFIRMATION);
        keyboard.add(getButton(Button.USER_PAYMENT_CONFIRMATION.getName(), accept.toString()));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName, String buttonCallBackData) {
        final InlineKeyboardButton button = new InlineKeyboardButton();
        final List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
