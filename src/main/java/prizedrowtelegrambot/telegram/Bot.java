package prizedrowtelegrambot.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.enums.BotMessage;
import prizedrowtelegrambot.services.ChatAdminService;
import prizedrowtelegrambot.services.ScheduleAppService;
import prizedrowtelegrambot.services.UserMessageService;
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

    final MessageHandler messageHandler;
    final CallbackQueryHandler callbackQueryHandler;
    final ScheduleAppService scheduleAppService;
    final UserMessageService userMessageService;

    final ChatAdminService chatAdminService;

    public Bot(SetWebhook setWebhook,
               MessageHandler messageHandler,
               CallbackQueryHandler callbackQueryHandler,
               ScheduleAppService scheduleAppService,
               UserMessageService userMessageService,
               ChatAdminService chatAdminService) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.scheduleAppService = scheduleAppService;
        this.userMessageService = userMessageService;
        this.chatAdminService = chatAdminService;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (Exception e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessage.EXCEPTION_WHAT_THE_FUCK.getMessage());
        }
    }

    @Nullable
    private BotApiMethod<?> handleUpdate(Update update) {
        SendMessage result;
        if (update.hasCallbackQuery()) {
            result = runReplyKeyboardAction(update);
        } else {
            result = runMessageAction(update);
        }
        return result;
    }

    private SendMessage runReplyKeyboardAction(Update update) {
        return callbackQueryHandler.processCallbackQuery(update.getCallbackQuery(), this);
    }
    private SendMessage runMessageAction(Update update) {
        SendMessage result = null;
        final Message message = update.getMessage();
        if (message != null) {
            final DonateDto donateDto = new DonateDto(message);
            if (chatAdminService.isAdminUser(donateDto.getLogin())) {
                result = runAdminAction(donateDto);
            } else {
                result = runUserAction(donateDto);
            }
        }
        return result;
    }

    private SendMessage runAdminAction(DonateDto donateDto) {
        return messageHandler.answerMessage(donateDto);
    }
    private SendMessage runUserAction(DonateDto donateDto) {
        SendMessage result;
        switch (scheduleAppService.getScheduledStopAction()) {
            case STOP_GETTING_DONATES: {
                result = userMessageService.getStopTakingDonatesMessage(donateDto);
                break;
            }
            case STOP_DRAW: {
                result = userMessageService.getStopDrawMessage(donateDto);
                break;
            }
            default: {
                result = messageHandler.answerMessage(donateDto);
            }
        }
        return result;
    }
}
