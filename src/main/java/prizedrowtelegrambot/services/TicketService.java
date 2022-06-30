package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import prizedrowtelegrambot.entities.Ticket;
import prizedrowtelegrambot.repositories.TicketRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<Ticket> findAll(){
        return StreamSupport.stream(ticketRepository.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }

    public List<String> getAllTicketsAsStrings(Iterable<Ticket> tickets) {
        return StreamSupport.stream(tickets.spliterator(), true)
                .map(Ticket::toString).collect(Collectors.toList());
    }

    public Optional<Ticket> findById(long id){
        return ticketRepository.findById(id);
    }
}
