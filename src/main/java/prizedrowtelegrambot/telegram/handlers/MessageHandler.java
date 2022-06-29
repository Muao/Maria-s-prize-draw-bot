package prizedrowtelegrambot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.services.DonateService;
import prizedrowtelegrambot.services.InputDataService;
import prizedrowtelegrambot.services.UserMessageService;
import prizedrowtelegrambot.telegram.keyboards.ReplyKeyboardMaker;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    final UserMessageService userMessageService;
    final ReplyKeyboardMaker replyKeyboardMaker;
    final DonateService donateService;
    final InputDataService inputDataService;
    @Value("${bot.ticket-price}") String ticketPrice;

    public SendMessage answerMessage(Message message) {
        final DonateDto donateDto = new DonateDto(message);
        final String chatId = donateDto.getChatId();
        final String inputText = donateDto.getInputText();
        if (inputText == null) {
            throw new IllegalStateException();
        }
        SendMessage result;
        switch (inputText) {
            case "/start": {
                result = getStartMessage(chatId);
                break;
            }
            case "Зробити донат та зареєструватися у розіграші": {
                result = getTicketsAmountMessage(chatId);
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

    private SendMessage getStartMessage(String chatId) {
        final SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.INTRO_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getTicketsAmountMessage(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.TICKETS_AMOUNT_MESSAGE.getMessage());
    }
}
