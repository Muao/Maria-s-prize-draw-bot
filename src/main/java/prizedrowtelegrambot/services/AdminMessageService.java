package prizedrowtelegrambot.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import prizedrowtelegrambot.entities.ChatAdmin;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.repositories.ChatAdminRepository;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.keyboards.InlineKeyboardMaker;

@Service
public record AdminMessageService(ChatAdminRepository chatAdminRepository, InlineKeyboardMaker inlineKeyboardMaker) {
    public void sendCheckPaymentMessageToAllAdmins(Donate donate, Bot bot) {
        final Iterable<ChatAdmin> admins = chatAdminRepository.findAll();
        final String messageForAdmin = getMessageForAdmin(donate);
        admins.forEach(ad -> {
            try {
                bot.execute(createAdminMessage(ad.getChartId(), messageForAdmin, donate.getId().toString()));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private SendMessage createAdminMessage(String chatId, String messageForAdmin, String donateId) {
        final SendMessage sendMessage = new SendMessage(chatId, messageForAdmin);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtons(donateId));
        return sendMessage;
    }

    private String getMessageForAdmin(Donate donate) {
        return String.format("Please check the new payment: user: %s; name: %s; amount: %d; data: %s",
                donate.getLogin(), donate.getUserName(), donate.getAmount(), donate.getDate());
    }
}
