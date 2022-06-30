package prizedrowtelegrambot.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import prizedrowtelegrambot.entities.Donate;

import java.util.Set;

public interface DonateRepository extends CrudRepository<Donate, Long> {

    Set<Donate> getEntityByTotalNeedsToPayAndLogin(long totalNeedsToPay, String login);

    @Query("SELECT SUM(d.totalNeedsToPay) FROM Donate d WHERE d.checked is true")
    long getTotalNeedsToPayConfirmedSum();

    @Query("SELECT COUNT(d.id) FROM Donate d WHERE d.checked is true")
    int getCountOfApprovedDonations();
}
