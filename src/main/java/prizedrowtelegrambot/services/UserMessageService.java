package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.keyboards.InlineKeyboardMaker;
import prizedrowtelegrambot.telegram.keyboards.ReplyKeyboardMaker;

import java.util.Set;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserMessageService {
    final InlineKeyboardMaker inlineKeyboardMaker;
    final ReplyKeyboardMaker replyKeyboardMaker;
    final DonateService donateService;
    final ChatAdminService chatAdminService;
    @Value("${bot.draw-data}") String drawData;
    @Value("${bot.admin}") String adminLogin;
    @Value("${bot.card-number}") String cardNumber;

    public SendMessage getStartMessage(String chatId, String login) {
        SendMessage sendMessage;
        ReplyKeyboardMarkup replyKeyboardMarkup;
        if (chatAdminService.isAdminUser(login)) {
            replyKeyboardMarkup = replyKeyboardMaker.getAdminKeyboardMarkup();
            final int countOfApprovedDonations = donateService.getCountOfApprovedDonations();
            final String adminMessage = String.format(
                    BotMessageEnum.INTRO_ADMIN_MESSAGE.getMessage(),
                    login, countOfApprovedDonations);
            sendMessage = new SendMessage(chatId, adminMessage);
        } else {
            replyKeyboardMarkup = replyKeyboardMaker.getUserMenuKeyboard();
            sendMessage = new SendMessage(chatId, BotMessageEnum.INTRO_MESSAGE.getMessage());
        }
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    public SendMessage getTicketsAmountMessage(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.TICKETS_AMOUNT_MESSAGE.getMessage());
    }

    public void sendSuccessConfirmationMessage(Bot bot, long totalNeedsToPay, Set<String> ticketsIds, String chatId) {
        final String message = getSuccessConfirmationMessage(totalNeedsToPay, ticketsIds);
        final SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableHtml(true);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendDeclinePaymentMessage(Bot bot, long totalNeedsToPay, String chatId) {
        final String message = getDeclinePaymentMessage(totalNeedsToPay);
        try {
            bot.execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public SendMessage sendRequestToConfirmPaymentMessage(String chatId, Long donateId, long totalNeedsToPayment) {
        final String message = String.format(BotMessageEnum.PAYMENT_MESSAGE.getMessage(), totalNeedsToPayment, cardNumber);
        final SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getUserPaymentConfirmationInlineButtons(donateId.toString()));
        return sendMessage;
    }

    private String getDeclinePaymentMessage(long totalNeedsToPay) {
        return String.format(BotMessageEnum.DECLINE_PAYMENT_MESSAGE.getMessage(),
                totalNeedsToPay, adminLogin);
    }

    private String getSuccessConfirmationMessage(long totalNeedsToPay, Set<String> ticketsIds) {
        return String.format(BotMessageEnum.SUCCESS_CONFIRMATION_MESSAGE.getMessage(),
                totalNeedsToPay, ticketsIds.size(), String.join("\n", ticketsIds), drawData);
    }

    public SendMessage getStopTakingDonatesMessage(DonateDto donateDto) {
        return new SendMessage(donateDto.getChatId(), String.format(
                BotMessageEnum.STOP_TAKING_DONATES_MESSAGE.getMessage(),
                donateDto.getLogin(), cardNumber));
    }

    public SendMessage getStopDrawMessage(DonateDto donateDto) {
        return new SendMessage(donateDto.getChatId(), String.format(
                BotMessageEnum.STOP_DRAW_MESSAGE.getMessage(),
                donateDto.getLogin(), donateService.getCheckedTotalNeedsToPay(), cardNumber));
    }
}
