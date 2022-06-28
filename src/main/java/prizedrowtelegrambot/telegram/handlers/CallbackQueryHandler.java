package prizedrowtelegrambot.telegram.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import prizedrowtelegrambot.dtos.ButtonActionDto;
import prizedrowtelegrambot.services.AdminMessageService;
import prizedrowtelegrambot.services.ButtonActionService;
import prizedrowtelegrambot.telegram.Bot;

@Component
public record CallbackQueryHandler(ButtonActionService buttonActionService, AdminMessageService adminMessageService) {
    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery, Bot bot) {
        String actionAnswer;
        final String chatId = buttonQuery.getMessage().getChatId().toString();
        final String data = buttonQuery.getData();
        final User user = buttonQuery.getFrom();
        try {
            final ButtonActionDto buttonActionDto = new ObjectMapper().readValue(data, ButtonActionDto.class);
           actionAnswer = switch (buttonActionDto.action()){
                case ACCEPT -> buttonActionService.acceptAction(buttonActionDto.donateId(), user.getUserName(), bot);
                case DECLINE -> "decline";
                case USER_PAYMENT_CONFIRMATION -> adminMessageService.sendCheckPaymentMessageToAllAdmins(buttonActionDto.donateId(), bot);
                default -> throw new IllegalStateException();
            };
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new SendMessage(chatId, actionAnswer);
    }
}
