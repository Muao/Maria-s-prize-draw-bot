package prizedrowtelegrambot.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import prizedrowtelegrambot.entities.Ticket;

import java.util.Set;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Query("SELECT t.id FROM Ticket t")
    Set<Long>getTicketsIds();

    @Query("SELECT concat(t.login, ' have ', count(t.ticketId)) as user_to_tickets FROM Ticket t group by t.login")
    Set<String> getUserWithTickets();
}
