package prizedrowtelegrambot.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class DonateDto {
    String chatId;
    String inputText;
    String login;
    String userName;

    public DonateDto(Message message) {
        final User user = message.getFrom();
        this.login = user.getUserName();
        this.userName = user.getFirstName() + " " + user.getLastName();
        this.chatId = message.getChatId().toString();
        this.inputText = message.getText();
    }
}
