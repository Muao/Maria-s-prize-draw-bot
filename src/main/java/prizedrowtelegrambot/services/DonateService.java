package prizedrowtelegrambot.services;

import org.springframework.stereotype.Service;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.repositories.DonateRepository;

import java.util.Date;
import java.util.Set;

@Service
public record DonateService(DonateRepository donateRepository) {
    public Donate saveEntity(DonateDto donateDto, int totalNeedsToPayment) {
        final Donate donate = new Donate();
        donate.setTotalNeedsToPay(totalNeedsToPayment);
        donate.setAmountOfTickets(Integer.parseInt(donateDto.getInputText()));
        donate.setUserName(donateDto.getUserName());
        donate.setLogin(donateDto.getLogin());
        donate.setDate(new Date());
        donate.setChatId(donateDto.getChatId());
        return donateRepository.save(donate);
    }

    public boolean isDonateFromUserWithSameAmountExist(int totalNeedsToPayment, String login) {
        final Set<Donate> entityByAmountAndLogin =
                donateRepository.getEntityByTotalNeedsToPayAndLogin(totalNeedsToPayment, login);
        return !entityByAmountAndLogin.isEmpty();
    }
}
