package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import prizedrowtelegrambot.entities.Ticket;
import prizedrowtelegrambot.repositories.TicketRepository;

import java.util.Date;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TicketService {
    TicketRepository ticketRepository;

    public Ticket createTicket(String login) {
        final Date date = new Date();
        final Ticket ticket = new Ticket();
        ticket.setTicketId(date.getTime());
        ticket.setDate(date);
        ticket.setLogin(login);

        return ticketRepository.save(ticket);
    }

    public Set<Long> getAllTicketsIds(){
        return ticketRepository.getTicketsIds();
    }
}
