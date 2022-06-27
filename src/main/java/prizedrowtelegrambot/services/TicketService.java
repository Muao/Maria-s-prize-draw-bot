package prizedrowtelegrambot.services;

import org.springframework.stereotype.Service;
import prizedrowtelegrambot.entities.Ticket;
import prizedrowtelegrambot.repositories.TicketRepository;

import java.util.Date;

@Service
public record TicketService(TicketRepository ticketRepository) {

    public Ticket createTicket(String login) {
        final Date date = new Date();
        final Ticket ticket = new Ticket();
        ticket.setTicketId(date.getTime());
        ticket.setDate(date);
        ticket.setLogin(login);

        return ticketRepository.save(ticket);
    }
}
