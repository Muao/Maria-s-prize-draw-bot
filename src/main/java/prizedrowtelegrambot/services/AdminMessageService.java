package prizedrowtelegrambot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import prizedrowtelegrambot.entities.ChatAdmin;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.enums.BotMessageEnum;
import prizedrowtelegrambot.repositories.ChatAdminRepository;
import prizedrowtelegrambot.repositories.DonateRepository;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.keyboards.InlineKeyboardMaker;

import java.util.Optional;

@Service
@Slf4j
public record AdminMessageService(
        ChatAdminRepository chatAdminRepository,
        InlineKeyboardMaker inlineKeyboardMaker,
        DonateRepository donateRepository) {
    public String sendCheckPaymentMessageToAllAdmins(String donateId, Bot bot) {
        final Optional<Donate> optionalDonate = donateRepository.findById(Long.parseLong(donateId));
        if (optionalDonate.isPresent()) {
            final Donate donate = optionalDonate.get();
            final Iterable<ChatAdmin> admins = chatAdminRepository.findAll();
            final String messageForAdmin = getMessageForAdmin(donate);
            admins.forEach(ad -> {
                try {
                    bot.execute(createAdminMessage(ad.getChatId(), messageForAdmin, String.valueOf(donate.getId())));
                } catch (TelegramApiException e) {
                    log.error(e.getMessage(), e);
                }
            });
        }
        return BotMessageEnum.AFTER_PAYMENT_MESSAGE.getMessage();
    }

    private SendMessage createAdminMessage(String chatId, String messageForAdmin, String donateId) {
        final SendMessage sendMessage = new SendMessage(chatId, messageForAdmin);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getPaymentActionInlineButtons(donateId));
        return sendMessage;
    }

    private String getMessageForAdmin(Donate donate) {
        return String.format("Please check the new payment: user: @%s; name: %s; amount: %d uah; data: %s",
                donate.getLogin(), donate.getUserName(), donate.getTotalNeedsToPay(), donate.getDate());
    }
}
