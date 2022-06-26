package prizedrowtelegrambot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import prizedrowtelegrambot.bot.BotMessageEnum;
import prizedrowtelegrambot.bot.ButtonNameEnum;
import prizedrowtelegrambot.telegram.keyboards.ReplyKeyboardMaker;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    ReplyKeyboardMaker replyKeyboardMaker;

    public BotApiMethod<?> answerMessage(Message message) {
        final String chatId = message.getChatId().toString();
        final String inputText = message.getText();

        if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_PAYMENT1_BUTTON.getButtonName())) {
            return getPayment1Message(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_PAYMENT2_BUTTON.getButtonName())) {
            return getPayment2Message(chatId);
        } else if (isInputTextNumber(inputText)) {
            //todo save to db all need data
            return afterPayment1Message(chatId);
        } else {
            return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
    }

    private BotApiMethod<?> afterPayment1Message(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.AFTER_PAYMENT_MESSAGE.getMessage());
    }

    //todo move to service
    private boolean isInputTextNumber(String inputText) {
        boolean result;
        try {
            Float.parseFloat(inputText);
            result = true;
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    private SendMessage getStartMessage(String chatId) {
        final SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getPayment1Message(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.PAYMENT1_MESSAGE.getMessage());
    }

    private SendMessage getPayment2Message(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.PAYMENT1_MESSAGE.getMessage());
    }
}
