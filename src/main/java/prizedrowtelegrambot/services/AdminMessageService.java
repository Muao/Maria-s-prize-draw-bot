package prizedrowtelegrambot.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import prizedrowtelegrambot.entities.ChatAdmin;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.repositories.ChatAdminRepository;
import prizedrowtelegrambot.telegram.Bot;

@Service
public record AdminMessageService(ChatAdminRepository chatAdminRepository) {
    public void sendCheckPaymentMessageToAllAdmins(Donate donate, Bot bot) {
        final Iterable<ChatAdmin> admins = chatAdminRepository.findAll();
        admins.forEach(ad -> {
            try {
                bot.execute(new SendMessage(ad.getChartId(),
                        String.format("check the new payment: user: %s; name: %s; amount: %d; data: %s",
                                donate.getLogin(), donate.getUserName(), donate.getAmount(), donate.getDate())));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
