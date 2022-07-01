package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Service;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.entities.Ticket;
import prizedrowtelegrambot.enums.BotMessage;
import prizedrowtelegrambot.telegram.Bot;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RandomDrawService {
    TicketService ticketService;
    DonateService donationService;
    UserMessageService userMessageService;
    AdminMessageService adminMessageService;
    ScheduleAppService scheduleAppService;

    public String startDraw(String chatId, Bot bot) throws InterruptedException {
        final List<Ticket> allTickets = ticketService.findAll();
        final List<String> tickets = ticketService.getAllTicketsAsStrings(allTickets);
        adminMessageService.sendStartDrawMessage(chatId, bot, tickets);
        processDraw(chatId, allTickets, bot);
        scheduleAppService.setStopDraw();
        return BotMessage.END_DRAW_ADMIN_MESSAGE.getMessage();
    }

    public void processDraw(String chatId, List<Ticket> allTickets, Bot bot) throws InterruptedException {
        for (int i = 1; i <= 4; i++) {
            final Ticket winnerTicket = doDraw(3000L, allTickets);
            final Long winnerTicketId = winnerTicket.getTicketId();
            final Donate winnerDonate = donationService.findByTicketsId(winnerTicket.getId());
            final String winnerLogin = winnerDonate.getLogin();
            adminMessageService.sendWinningMessage(i, chatId, winnerLogin, winnerTicketId, bot);
            userMessageService.sendWinningMessage(winnerDonate.getChatId(), winnerLogin, winnerTicketId, bot);
        }
    }

    public Ticket doDraw(long timeOut, List<Ticket> allTickets) throws InterruptedException {
        Thread.sleep(timeOut);
        return doDraw(allTickets);
    }

    @Nullable
    public Ticket doDraw(List<Ticket> allTickets) {
        final int generatorFrom = 0;
        final int generatorTo = allTickets.size() - 1;
        final int winnerId = new RandomDataGenerator().nextInt(generatorFrom, generatorTo);
        final Ticket winnerTicket = allTickets.get(winnerId);
        final String winnerLogin = winnerTicket.getLogin();
        final List<Ticket> allWinnerUserTickets = allTickets.stream()
                .filter(t -> t.getLogin().equals(winnerLogin)).collect(Collectors.toList());
        allTickets.removeAll(allWinnerUserTickets);
        return winnerTicket;
    }
}
