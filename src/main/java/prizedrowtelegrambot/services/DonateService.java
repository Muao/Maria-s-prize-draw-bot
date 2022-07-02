package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.repositories.DonateRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DonateService {
    final DonateRepository donateRepository;
    @Value("${bot.ticket-price}") String ticketPrice;

    public Donate saveEntity(long totalNeedsToPayment, User user, String chatId) {
        final Donate donate = new Donate();
        donate.setTotalNeedsToPay(totalNeedsToPayment);
        donate.setAmountOfTickets((int) (totalNeedsToPayment / Integer.parseInt(ticketPrice)));
        donate.setUserName(user.getFirstName() + " " + user.getLastName());
        donate.setLogin(user.getUserName());
        donate.setDate(new Date());
        donate.setChatId(chatId);
        return donateRepository.save(donate);
    }

    public boolean isUserHaveUncheckedDonate(String login) {
        final Set<Donate> entityByAmountAndLogin =
                donateRepository.getEntityByLoginAndCheckerLoginIsNull(login);
        return !entityByAmountAndLogin.isEmpty();
    }

    public long getCheckedTotalNeedsToPay() {
        return donateRepository.getTotalNeedsToPayConfirmedSum();
    }

    public int getCountOfApprovedDonations() {
        return donateRepository.getCountOfApprovedDonations();
    }

    public Donate findByTicketsId(long id) {
        return donateRepository.findByTicketsId(id);
    }

    public List<String> getAllChatIdsWithConfirmedDonates(){
        return donateRepository.getChatIdsWithConfirmedDonates();
    }
}
