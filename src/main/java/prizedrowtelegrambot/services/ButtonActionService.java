package prizedrowtelegrambot.services;

import org.springframework.stereotype.Service;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.entities.Ticket;
import prizedrowtelegrambot.repositories.DonateRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public record ButtonActionService(DonateRepository donateRepository, TicketService ticketService) {

    public String acceptAction(String donateId, String checkerLogin){
        String result = "";
        final long id = Long.parseLong(donateId);
        final Optional<Donate> optionalDonate = donateRepository.findById(id);
        if(optionalDonate.isPresent()) {
            final Donate donate = optionalDonate.get();
            final int amount = donate.getAmount();
            final int ticketsCount = amount/250;
            final Set<Ticket> tickets = new HashSet<>();
            final Set<String> ticketsIds = new HashSet<>();

            for (int i = 0; i < ticketsCount; i++) {
                final Ticket ticket = ticketService.createTicket(donate.getLogin());
                tickets.add(ticket);
                ticketsIds.add(ticket.getTicketId().toString());
            }

            donate.setChecked(true);
            donate.setCheckingDate(new Date());
            donate.setCheckerLogin(checkerLogin);
            donate.setTickets(tickets);
            donateRepository.save(donate);

            result = String.format("Was created %d ticket/s for user %s with numbers: %s",
                    ticketsIds.size(),
                    donate.getLogin(),
                    String.join(",", ticketsIds));

            //todo send success validation message to user here
        }
        return result;
    }
}
