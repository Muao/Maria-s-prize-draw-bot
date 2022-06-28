package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.keyboards.InlineKeyboardMaker;

import java.util.Set;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserMessageService{
    final InlineKeyboardMaker inlineKeyboardMaker;
    @Value("${bot.draw-data}") String drawData;


    public void sendSuccessConfirmationMessage(Bot bot, int amount, Set<String> ticketsIds, String chatId) {
        final String message = getMessage(amount, ticketsIds);
        try {
            bot.execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public SendMessage sendRequestToConfirmPaymentMessage(String chatId, Long donateId, int totalNeedsToPayment) {
        final String message = String.format(BotMessageEnum.PAYMENT_MESSAGE.getMessage(), totalNeedsToPayment);
        final SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getUserPaymentConfirmationInlineButtons(donateId.toString()));
        return sendMessage;
    }

    private String getMessage(int amount, Set<String> ticketsIds) {
        return String.format(BotMessageEnum.SUCCESS_CONFIRMATION_MESSAGE.getMessage(),
                amount, ticketsIds.size(), String.join(",", ticketsIds), drawData);
    }
}
