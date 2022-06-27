package prizedrowtelegrambot.telegram.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import prizedrowtelegrambot.dtos.ButtonActionDto;

import static prizedrowtelegrambot.enums.ButtonAction.ACCEPT;
import static prizedrowtelegrambot.enums.ButtonAction.DECLINE;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {
    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final String chatId = buttonQuery.getMessage().getChatId().toString();
        final String data = buttonQuery.getData();
        try {
            final ButtonActionDto buttonActionDto = new ObjectMapper().readValue(data, ButtonActionDto.class);
            switch (buttonActionDto.action()){
                case ACCEPT -> System.out.println(ACCEPT);
                case DECLINE -> System.out.println(DECLINE);
                default -> throw new IllegalStateException();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //todo here is needs to accept or decline donate
        return new SendMessage(chatId, data);
    }
}
