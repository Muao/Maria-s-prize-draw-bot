package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import prizedrowtelegrambot.entities.ChatAdmin;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.enums.BotMessage;
import prizedrowtelegrambot.repositories.ChatAdminRepository;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.keyboards.InlineKeyboardMaker;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AdminMessageService {
    final ChatAdminRepository chatAdminRepository;
    final InlineKeyboardMaker inlineKeyboardMaker;
    final DonateService donateService;
    final TicketService ticketService;
    @Value("${bot.draw-link}") String drawLink;
    @Value("${bot.draw-time}") String drawTime;

    public String sendCheckPaymentMessageToAllAdmins(long totalNeedsToPayment, User user, String chatId, Bot bot) {
        String result;
        if (donateService.isUserHaveUncheckedDonate(user.getUserName())) {
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
            result = BotMessage.AFTER_PAYMENT_MESSAGE.getMessage();
        } else {
            result = BotMessage.NOT_CHECKED_DONATE_ALREADY_EXIST.getMessage();
        }
        return result;
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
        final String message = String.format(BotMessage.WINNING_ADMIN_MESSAGE.getMessage(),
                iteration, login, ticketId);
        try {
            bot.execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendStartDrawMessage(String chatId, Bot bot, List<String> tickets) {
        final String allTicketIds = String.join("\n", tickets);
        final String message = String.format(BotMessage.START_DRAW_MESSAGE.getMessage(), allTicketIds);
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
                chatId, BotMessage.START_DRAW_CONFIRMATION_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getStartDrawValidationMessage());
        return sendMessage;
    }

    public SendMessage get15MinReminderConfirmation(String chatId) {
        final List<String> chatIdsWithConfirmedDonates = donateService.getAllChatIdsWithConfirmedDonates();
        final SendMessage sendMessage = new SendMessage(
                chatId, String.format(BotMessage.SEND_REMINDER_CONFIRMATION_MESSAGE.getMessage(),
                chatIdsWithConfirmedDonates.size()));
        sendMessage.setReplyMarkup(inlineKeyboardMaker.get15MinReminderValidationMessage());
        return sendMessage;
    }

    public String send15MinReminderToAllUsers(Bot bot) {
        final String message = String.format(BotMessage.SEND_15_MIN_MESSAGE.getMessage(), drawLink);
        final int sendMessageCount = sendReminderToAllUsers(bot, message);
        return String.format(BotMessage.ALREADY_SENT.getMessage(), sendMessageCount);
    }

    public SendMessage getTodayReminderConfirmation(String chatId) {
        final List<String> chatIdsWithConfirmedDonates = donateService.getAllChatIdsWithConfirmedDonates();
        final SendMessage sendMessage = new SendMessage(
                chatId, String.format(BotMessage.SEND_REMINDER_CONFIRMATION_MESSAGE.getMessage(),
                chatIdsWithConfirmedDonates.size()));
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getTodayReminderValidationMessage());
        return sendMessage;
    }
    public String sendTodayReminderToAllUsers(Bot bot) {
        final String message = String.format(BotMessage.SEND_TODAY_REMINDER_MESSAGE.getMessage(), drawTime, drawLink);
        final int sendMessageCount = sendReminderToAllUsers(bot, message);
        return String.format(BotMessage.ALREADY_SENT.getMessage(), sendMessageCount);
    }

    private int sendReminderToAllUsers(Bot bot, String message) {
        final List<String> chatIdsWithConfirmedDonates = donateService.getAllChatIdsWithConfirmedDonates();
        chatIdsWithConfirmedDonates.forEach(chatId -> {
            try {
                bot.execute(new SendMessage(chatId, message));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
        return chatIdsWithConfirmedDonates.size();
    }

    public SendMessage reStartDraw(String chatId) {
        return new SendMessage(chatId, BotMessage.RESTART_DRAW.getMessage());
    }
}
