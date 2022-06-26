package prizedrowtelegrambot.repositories;

import org.springframework.data.repository.CrudRepository;
import prizedrowtelegrambot.entities.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
