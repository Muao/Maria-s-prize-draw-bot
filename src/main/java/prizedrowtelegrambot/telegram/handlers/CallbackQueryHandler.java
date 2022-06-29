package prizedrowtelegrambot.telegram.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import prizedrowtelegrambot.dtos.ButtonActionDto;
import prizedrowtelegrambot.enums.ButtonAction;
import prizedrowtelegrambot.services.AdminMessageService;
import prizedrowtelegrambot.services.ButtonActionService;
import prizedrowtelegrambot.telegram.Bot;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {
    ButtonActionService buttonActionService;
    AdminMessageService adminMessageService;

    public SendMessage processCallbackQuery(CallbackQuery buttonQuery, Bot bot) {
        String actionAnswer = "";
        final String chatId = buttonQuery.getMessage().getChatId().toString();
        final String data = buttonQuery.getData();
        final User user = buttonQuery.getFrom();
        final ButtonActionDto buttonActionDto;
        try {
            buttonActionDto = new ObjectMapper().readValue(data, ButtonActionDto.class);
            switch (buttonActionDto.getAction()) {
                case ACCEPT: {
                    actionAnswer = buttonActionService.acceptAction(buttonActionDto.getDonateId(), user.getUserName(), bot);
                    break;
                }
                case DECLINE: {
                    actionAnswer = buttonActionService.declineAction(buttonActionDto.getDonateId(), user.getUserName(), bot);
                    break;
                }
                case USER_PAYMENT_CONFIRMATION: {
                    actionAnswer = adminMessageService.sendCheckPaymentMessageToAllAdmins(buttonActionDto.getDonateId(), bot);
                    break;
                }
            }

        } catch (JsonProcessingException e) {
            actionAnswer = "Can't parse button action object " + e.getMessage();
        }
        final SendMessage sendMessage = new SendMessage(chatId, actionAnswer);
        sendMessage.enableHtml(true);
        return sendMessage;
    }
}
