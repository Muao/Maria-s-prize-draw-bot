package prizedrowtelegrambot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.enums.ButtonNameEnum;
import prizedrowtelegrambot.services.AdminMessageService;
import prizedrowtelegrambot.services.DonateService;
import prizedrowtelegrambot.services.InputDataService;
import prizedrowtelegrambot.services.UserMessageService;

import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    final UserMessageService userMessageService;
    final DonateService donateService;
    final InputDataService inputDataService;
    final AdminMessageService adminMessageService;
    @Value("${bot.ticket-price}") String ticketPrice;

    public SendMessage answerMessage(DonateDto donateDto) {
        SendMessage result;
        final String chatId = donateDto.getChatId();
        final String inputText = donateDto.getInputText();
        final String login = donateDto.getLogin();
        if (inputText == null) {
            throw new IllegalStateException();
        }
        final Optional<ButtonNameEnum> inputAsButton = convertToButton(inputText);
        if (inputAsButton.isPresent()) {
            final ButtonNameEnum buttonName = inputAsButton.get();
            result = buttonAction(chatId, login, buttonName);
        } else {
            result = noButtonAction(donateDto, chatId, inputText);
        }
        return result;
    }

    private SendMessage noButtonAction(DonateDto donateDto, String chatId, String inputText) {
        SendMessage result;
        if (inputDataService.isPositiveDigit(inputText)) {
            result = paymentProcessing(donateDto, chatId);
        } else {
            result = new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
        return result;
    }

    private SendMessage buttonAction(String chatId, String login, ButtonNameEnum buttonName) {
        SendMessage result;
        switch (buttonName) {
            case START: {
                result = userMessageService.getStartMessage(chatId, login);
                break;
            }
            case REGISTER: {
                result = userMessageService.getTicketsAmountMessage(chatId);
                break;
            }
            case START_DRAW: {
                result = adminMessageService.startDrawConfirmation(chatId);
                break;
            }
            case GET_CONFIRMED_USERS_LIST: {
                result = adminMessageService.getConfirmedUserList(chatId);
                break;
            }
            default: {
                result = new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
            }
        }
        return result;
    }

    private Optional<ButtonNameEnum> convertToButton(String inputText) {
        return ButtonNameEnum.stream()
                .filter(button -> button.getButtonName().equals(inputText)).findFirst();
    }

    private SendMessage paymentProcessing(DonateDto donateDto, String chatId) {
        SendMessage result;
        final int ticketsAmount = Integer.parseInt(donateDto.getInputText());
        final long totalNeedsToPayment = (long) ticketsAmount * Integer.parseInt(ticketPrice);
        if (donateService.isUserHaveUncheckedDonate(donateDto.getLogin())) {
            result = new SendMessage(chatId, BotMessageEnum.NOT_CHECKED_DONATE_ALREADY_EXIST.getMessage());
        } else {
            result = userMessageService.sendRequestToConfirmPaymentMessage(chatId, totalNeedsToPayment);
        }
        return result;
    }
}
