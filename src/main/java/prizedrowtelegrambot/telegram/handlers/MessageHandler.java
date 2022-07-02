package prizedrowtelegrambot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.enums.BotMessage;
import prizedrowtelegrambot.enums.Button;
import prizedrowtelegrambot.services.*;

import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    final UserMessageService userMessageService;
    final DonateService donateService;
    final InputDataService inputDataService;
    final AdminMessageService adminMessageService;
    final ScheduleAppService scheduleAppService;
    @Value("${bot.ticket-price}") String ticketPrice;

    public SendMessage answerMessage(DonateDto donateDto) {
        SendMessage result;
        final String chatId = donateDto.getChatId();
        final String inputText = donateDto.getInputText();
        final String login = donateDto.getLogin();
        if (inputText == null) {
            throw new IllegalStateException();
        }
        final Optional<Button> inputAsButton = convertToButton(inputText);
        if (inputAsButton.isPresent()) {
            final Button button = inputAsButton.get();
            result = buttonAction(chatId, login, button);
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
            result = new SendMessage(chatId, BotMessage.NON_COMMAND_MESSAGE.getMessage());
        }
        return result;
    }

    private SendMessage buttonAction(String chatId, String login, Button button) {
        SendMessage result;
        switch (button) {
            case RESTART: {
                result = adminMessageService.reStartDraw(chatId);
                scheduleAppService.startNewDraw();
                //clean db action
                break;
            }

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
            case SEND_15_MIN_REMINDER: {
                result = adminMessageService.get15MinReminderConfirmation(chatId);
                break;
            }
            case SEND_TODAY_REMINDER: {
                result = adminMessageService.getTodayReminderConfirmation(chatId);
                break;
            }
            default: {
                result = new SendMessage(chatId, BotMessage.NON_COMMAND_MESSAGE.getMessage());
            }
        }
        return result;
    }

    private Optional<Button> convertToButton(String inputText) {
        return Button.stream()
                .filter(button -> button.getName().equals(inputText)).findFirst();
    }

    private SendMessage paymentProcessing(DonateDto donateDto, String chatId) {
        SendMessage result;
        final int ticketsAmount = Integer.parseInt(donateDto.getInputText());
        final long totalNeedsToPayment = (long) ticketsAmount * Integer.parseInt(ticketPrice);
        if (!donateService.isUserHaveUncheckedDonate(donateDto.getLogin())) {
            result = userMessageService.sendRequestToConfirmPaymentMessage(chatId, totalNeedsToPayment);
        } else {
            result = new SendMessage(chatId, BotMessage.NOT_CHECKED_DONATE_ALREADY_EXIST.getMessage());
        }
        return result;
    }
}
