package prizedrowtelegrambot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.services.DonateService;
import prizedrowtelegrambot.services.InputDataService;
import prizedrowtelegrambot.services.RandomDrawService;
import prizedrowtelegrambot.services.UserMessageService;
import prizedrowtelegrambot.telegram.Bot;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    final UserMessageService userMessageService;
    final DonateService donateService;
    final InputDataService inputDataService;

    final RandomDrawService randomDrawService;
    @Value("${bot.ticket-price}") String ticketPrice;

    public SendMessage answerMessage(DonateDto donateDto, Bot bot) {
        SendMessage result;
        final String chatId = donateDto.getChatId();
        final String inputText = donateDto.getInputText();
        final String login = donateDto.getLogin();
        if (inputText == null) {
            throw new IllegalStateException();
        }
        switch (inputText) {
            case "/start": {
                result = userMessageService.getStartMessage(chatId, login);
                break;
            }
            case "Зробити донат та зареєструватися у розіграші": {
                result = userMessageService.getTicketsAmountMessage(chatId);
                break;
            }

            case "Start draw": {
                result = randomDrawService.startDraw(chatId, bot);
                break;
            }
            default: {
                if (inputDataService.isPositiveDigit(inputText)) {
                    result = paymentProcessing(donateDto, chatId);
                } else {
                    result = new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
                }
            }
        }
        return result;
    }

    private SendMessage paymentProcessing(DonateDto donateDto, String chatId) {
        final int ticketsAmount = Integer.parseInt(donateDto.getInputText());
        final long totalNeedsToPayment = (long) ticketsAmount * Integer.parseInt(ticketPrice);
        if (!donateService.isDonateFromUserWithSameAmountExist(totalNeedsToPayment, donateDto.getLogin())) {
            final Donate donate = donateService.saveEntity(donateDto, totalNeedsToPayment);
            return userMessageService.sendRequestToConfirmPaymentMessage(chatId, donate.getId(), totalNeedsToPayment);
        } else {
            return new SendMessage(chatId, BotMessageEnum.SAME_TICKETS_AMOUNT_ALREADY_EXIST.getMessage());
        }
    }
}
