package prizedrowtelegrambot.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.services.AdminMessageService;
import prizedrowtelegrambot.services.DonateService;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.keyboards.ReplyKeyboardMaker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public record MessageHandler(
        AdminMessageService adminMessageService,
        ReplyKeyboardMaker replyKeyboardMaker,
        DonateService donateService
) {
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
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
                    yield paymentProcessing(donateDto, chatId, bot);
                } else {
                    yield new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
                }
            }
        };
    }

    private SendMessage paymentProcessing(DonateDto donateDto, String chatId, Bot bot) {
        if (!donateService.isDonateFromUserWithSameAmountExist(donateDto)) {
            final Donate donate = donateService.saveEntity(donateDto);
            adminMessageService.sendCheckPaymentMessageToAllAdmins(donate, bot);
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
        final SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.INTRO_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getPayment1Message(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.TICKETS_AMOUNT_MESSAGE.getMessage());
    }

    private SendMessage getPayment2Message(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.TICKETS_AMOUNT_MESSAGE.getMessage());
    }
}
