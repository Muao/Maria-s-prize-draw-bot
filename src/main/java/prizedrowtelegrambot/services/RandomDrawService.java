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

import java.util.List;
import java.util.Optional;
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

    public String startDraw(String chatId, Bot bot) {
        final List<Ticket> allTickets = ticketService.findAll();
        final List<String> tickets = ticketService.getAllTicketsAsStrings(allTickets);
        adminMessageService.sendStartDrawMessage(chatId, bot, tickets);
        processDraw(chatId, allTickets, bot);
        scheduleAppService.setStopDraw();
        return BotMessage.END_DRAW_ADMIN_MESSAGE.getMessage();
    }

    public void processDraw(String chatId, List<Ticket> allTickets, Bot bot) {
        final List<Long> ticketIds = allTickets.parallelStream()
                .map(Ticket::getId).collect(Collectors.toList());
        for (int i = 1; i <= 4; i++) {
            final Long winnerTicketId = doDraw(3000L, ticketIds);
            final Optional<Ticket> optionalTicket = ticketService.findById(winnerTicketId);
            if (optionalTicket.isPresent()) {
                final Ticket ticket = optionalTicket.get();
                final Donate winnerDonate = donationService.findByTicketsId(winnerTicketId);
                adminMessageService.sendWinningMessage(i, chatId, winnerDonate.getLogin(), ticket.getTicketId(), bot);
                userMessageService.sendWinningMessage(winnerDonate.getChatId(), winnerDonate.getLogin(), ticket.getTicketId(), bot);
            }
        }
    }

    public Long doDraw(long timeOut, List<Long> ticketsIds) {
        try {
            Thread.sleep(timeOut);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return doDraw(ticketsIds);
    }

    public Long doDraw(List<Long> ticketsIds) {
        final long generatorFrom = 1;
        final long generatorTo = ticketsIds.size();
        final long winnerId = new RandomDataGenerator().nextLong(generatorFrom, generatorTo);
        ticketsIds.remove(winnerId);
        return winnerId;
    }
}
