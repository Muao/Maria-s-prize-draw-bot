package prizedrowtelegrambot.repositories;

import org.springframework.data.repository.CrudRepository;
import prizedrowtelegrambot.entities.Donate;

public interface DonateRepository extends CrudRepository<Donate, Long> {
}
