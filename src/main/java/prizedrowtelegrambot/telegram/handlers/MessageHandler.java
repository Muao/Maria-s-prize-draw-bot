package prizedrowtelegrambot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.services.AdminMessageService;
import prizedrowtelegrambot.services.DonateService;
import prizedrowtelegrambot.services.UserMessageService;
import prizedrowtelegrambot.telegram.keyboards.ReplyKeyboardMaker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    final UserMessageService userMessageService;
    final AdminMessageService adminMessageService;
    final ReplyKeyboardMaker replyKeyboardMaker;
    final DonateService donateService;
    @Value("${bot.ticket-price}") String ticketPrice;

    public BotApiMethod<?> answerMessage(Message message) {
        final DonateDto donateDto = new DonateDto(message);
        final String chatId = donateDto.getChatId();
        final String inputText = donateDto.getInputText();
        return switch (inputText) {
            case null -> throw new IllegalStateException();
            case "/start" -> getStartMessage(chatId);
            case "Зробити донат та зареєструватися у розіграші" -> getTicketsAmountMessage(chatId);
            default -> {
                if (isDigit(inputText)) {
                    yield paymentProcessing(donateDto, chatId);
                } else {
                    yield new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
                }
            }
        };
    }

    private SendMessage paymentProcessing(DonateDto donateDto, String chatId) {
        final int ticketsAmount = Integer.parseInt(donateDto.getInputText());
        final int totalNeedsToPayment = ticketsAmount * Integer.parseInt(ticketPrice);
        if (!donateService.isDonateFromUserWithSameAmountExist(totalNeedsToPayment, donateDto.getLogin())) {
            final Donate donate = donateService.saveEntity(donateDto, totalNeedsToPayment);
            return userMessageService.sendRequestToConfirmPaymentMessage(chatId, donate.getId(), totalNeedsToPayment);
        } else {
            return new SendMessage(chatId, BotMessageEnum.SAME_TICKETS_AMOUNT_ALREADY_EXIST.getMessage());
        }
    }

    //todo move to service
    private boolean isDigit(String inputText) {
        boolean result = false;
        final Pattern pattern = Pattern.compile("\\d+");
        final Matcher matcher = pattern.matcher(inputText);
        if (matcher.matches()) {
            result = true;
        }
        return result;
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
