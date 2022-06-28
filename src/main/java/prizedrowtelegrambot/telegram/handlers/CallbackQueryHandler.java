package prizedrowtelegrambot.telegram.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import prizedrowtelegrambot.dtos.ButtonActionDto;
import prizedrowtelegrambot.services.AdminMessageService;
import prizedrowtelegrambot.services.ButtonActionService;
import prizedrowtelegrambot.telegram.Bot;

@Component
public record CallbackQueryHandler(ButtonActionService buttonActionService, AdminMessageService adminMessageService) {
    public SendMessage processCallbackQuery(CallbackQuery buttonQuery, Bot bot) {
        String actionAnswer;
        final String chatId = buttonQuery.getMessage().getChatId().toString();
        final String data = buttonQuery.getData();
        final User user = buttonQuery.getFrom();
        final ButtonActionDto buttonActionDto;
        try {
            buttonActionDto = new ObjectMapper().readValue(data, ButtonActionDto.class);
            actionAnswer = switch (buttonActionDto.action()) {
                case ACCEPT -> buttonActionService.acceptAction(buttonActionDto.donateId(), user.getUserName(), bot);
                case DECLINE -> buttonActionService.declineAction(buttonActionDto.donateId(), user.getUserName(), bot);
                case USER_PAYMENT_CONFIRMATION ->
                        adminMessageService.sendCheckPaymentMessageToAllAdmins(buttonActionDto.donateId(), bot);
            };
        } catch (JsonProcessingException e) {
            actionAnswer = "Can't parse button action object " + e.getMessage();
        }
        final SendMessage sendMessage = new SendMessage(chatId, actionAnswer);
        sendMessage.enableHtml(true);
        return sendMessage;
    }
}
