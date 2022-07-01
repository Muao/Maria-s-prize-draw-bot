package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import prizedrowtelegrambot.entities.ChatAdmin;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.repositories.ChatAdminRepository;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.keyboards.InlineKeyboardMaker;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AdminMessageService {
    ChatAdminRepository chatAdminRepository;
    InlineKeyboardMaker inlineKeyboardMaker;
    DonateService donateService;
    TicketService ticketService;

    public String sendCheckPaymentMessageToAllAdmins(long totalNeedsToPayment, User user, String chatId, Bot bot) {
        final Donate donate = donateService.saveEntity(totalNeedsToPayment, user, chatId);
            final Iterable<ChatAdmin> admins = chatAdminRepository.findAll();
            final String messageForAdmin = getMessageForAdmin(donate);
            admins.forEach(ad -> {
                try {
                    bot.execute(createPaymentValidationMessage(
                            ad.getChatId(), messageForAdmin, String.valueOf(donate.getId())));
                } catch (TelegramApiException e) {
                    log.error(e.getMessage(), e);
                }
            });
        return BotMessageEnum.AFTER_PAYMENT_MESSAGE.getMessage();
    }

    private SendMessage createPaymentValidationMessage(String chatId, String messageForAdmin, String donateId) {
        final SendMessage sendMessage = new SendMessage(chatId, messageForAdmin);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getPaymentActionInlineButtons(donateId));
        return sendMessage;
    }

    private String getMessageForAdmin(Donate donate) {
        return String.format("Please check the new payment: user: @%s; name: %s; amount: %d uah; data: %s",
                donate.getLogin(), donate.getUserName(), donate.getTotalNeedsToPay(), donate.getDate());
    }

    public void sendWinningMessage(int iteration, String chatId, String login, Long ticketId, Bot bot) {
        final String message = String.format(BotMessageEnum.WINNING_ADMIN_MESSAGE.getMessage(),
                iteration, login, ticketId);
        try {
            bot.execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendStartDrawMessage(String chatId, Bot bot, List<String> tickets) {
        final String allTicketIds = String.join("\n", tickets);
        final String message = String.format(BotMessageEnum.START_DRAW_MESSAGE.getMessage(), allTicketIds);
        try {
            bot.execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public SendMessage getConfirmedUserList(String chatId) {
        final Set<String> usersWithTickets = ticketService.getUserWithTickets();
        final String usersWithTicketsMessageString = String.join("\n", usersWithTickets);
        return new SendMessage(chatId, usersWithTicketsMessageString);
    }

    public SendMessage startDrawConfirmation(String chatId) {
        final SendMessage sendMessage = new SendMessage(
                chatId, BotMessageEnum.START_DRAW_CONFIRMATION_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getStartDrawValidationMessage());
        return sendMessage;
    }
}
