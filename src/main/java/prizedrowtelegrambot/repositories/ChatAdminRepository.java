package prizedrowtelegrambot.repositories;

import org.springframework.data.repository.CrudRepository;
import prizedrowtelegrambot.entities.ChatAdmin;

public interface ChatAdminRepository extends CrudRepository<ChatAdmin, Long> {
}
