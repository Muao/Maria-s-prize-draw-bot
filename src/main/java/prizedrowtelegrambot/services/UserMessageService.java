package prizedrowtelegrambot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.telegram.Bot;

import java.util.Set;

@Service
@Slf4j
public record UserMessageService() {

    public void sendSuccessConfirmationMessage(Bot bot, int amount, Set<String> ticketsIds, String chatId) {
        final String message = getMessage(amount, ticketsIds);
        try {
            bot.execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getMessage(int amount, Set<String> ticketsIds) {
        return String.format(BotMessageEnum.SUCCESS_CONFIRMATION_MESSAGE.getMessage(),
                amount, ticketsIds.size(), String.join(",", ticketsIds));
    }
}
