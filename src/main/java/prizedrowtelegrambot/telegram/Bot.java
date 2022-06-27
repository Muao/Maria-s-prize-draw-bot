package prizedrowtelegrambot.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.telegram.handlers.CallbackQueryHandler;
import prizedrowtelegrambot.telegram.handlers.MessageHandler;

import javax.annotation.Nullable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bot extends SpringWebhookBot {
    String botPath;
    String botUsername;
    String botToken;

    MessageHandler messageHandler;
    CallbackQueryHandler callbackQueryHandler;

    public Bot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (Exception e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_WHAT_THE_FUCK.getMessage());
        }
    }

    @Nullable
    private BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            final Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(update.getMessage(), this);
            }
        }
        return null;
    }
}
