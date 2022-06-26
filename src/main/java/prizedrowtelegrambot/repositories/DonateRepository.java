package prizedrowtelegrambot.repositories;

import org.springframework.data.repository.CrudRepository;
import prizedrowtelegrambot.entities.Donate;

import java.util.Set;

public interface DonateRepository extends CrudRepository<Donate, Long> {

    Set<Donate> getEntityByAmountAndLogin(int amount, String login);
}
