package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.repositories.DonateRepository;

import java.util.Date;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DonateService {
    DonateRepository donateRepository;
    public Donate saveEntity(DonateDto donateDto, long totalNeedsToPayment) {
        final Donate donate = new Donate();
        donate.setTotalNeedsToPay(totalNeedsToPayment);
        donate.setAmountOfTickets(Integer.parseInt(donateDto.getInputText()));
        donate.setUserName(donateDto.getUserName());
        donate.setLogin(donateDto.getLogin());
        donate.setDate(new Date());
        donate.setChatId(donateDto.getChatId());
        return donateRepository.save(donate);
    }

    public boolean isDonateFromUserWithSameAmountExist(long totalNeedsToPayment, String login) {
        final Set<Donate> entityByAmountAndLogin =
                donateRepository.getEntityByTotalNeedsToPayAndLogin(totalNeedsToPayment, login);
        return !entityByAmountAndLogin.isEmpty();
    }

    public long getCheckedTotalNeedsToPay(){
        return donateRepository.getTotalNeedsToPayConfirmedSum();
    }

    public int getCountOfApprovedDonations(){
        return donateRepository.getCountOfApprovedDonations();
    }

    public Donate findByTicketsId(long id){
        return donateRepository.findByTicketsId(id);
    }
}
