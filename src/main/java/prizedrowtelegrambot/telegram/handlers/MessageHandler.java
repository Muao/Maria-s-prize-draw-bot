package prizedrowtelegrambot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import prizedrowtelegrambot.bot.BotMessageEnum;
import prizedrowtelegrambot.bot.ButtonNameEnum;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.services.DonateService;
import prizedrowtelegrambot.telegram.keyboards.ReplyKeyboardMaker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    ReplyKeyboardMaker replyKeyboardMaker;
    DonateService donateService;

    public BotApiMethod<?> answerMessage(Message message) {
        final DonateDto donateDto = new DonateDto(message);
        final String chatId = donateDto.getChatId();
        final String inputText = donateDto.getInputText();

        return switch (inputText) {
            case null -> throw new IllegalStateException();
            case "/start" -> getStartMessage(chatId);
            case "Payment type 1" -> getPayment1Message(chatId);
            case "Payment type 2" -> getPayment2Message(chatId);
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
        if (!donateService.isDonateFromUserWithSameAmountExist(donateDto)) {
            final Donate donate = donateService.saveEntity(donateDto);
            checkPaymentService.startCheckPayment(donate, chatId);
            return afterPayment1Message(chatId, donate);
        } else {
            return new SendMessage(chatId, BotMessageEnum.SAME_PAYMENT_EXIST.getMessage());
        }
    }

    private SendMessage afterPayment1Message(String chatId, Donate save) {
        return new SendMessage(chatId, BotMessageEnum.AFTER_PAYMENT_MESSAGE.getMessage() + save.toString());
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
        final SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getPayment1Message(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.PAYMENT1_MESSAGE.getMessage());
    }

    private SendMessage getPayment2Message(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.PAYMENT1_MESSAGE.getMessage());
    }
}
