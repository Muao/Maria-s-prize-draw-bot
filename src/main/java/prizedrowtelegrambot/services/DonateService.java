package prizedrowtelegrambot.services;

import org.springframework.stereotype.Service;
import prizedrowtelegrambot.dtos.DonateDto;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.repositories.DonateRepository;

import java.util.Date;
import java.util.Set;

@Service
public record DonateService(DonateRepository donateRepository) {
    public Donate saveEntity(DonateDto donateDto) {
        final Donate donate = new Donate();
        donate.setAmount(Integer.parseInt(donateDto.getInputText()));
        donate.setUserName(donateDto.getUserName());
        donate.setLogin(donateDto.getLogin());
        donate.setDate(new Date());
        donate.setChatId(donateDto.getChatId());
        return donateRepository.save(donate);
    }

    public boolean isDonateFromUserWithSameAmountExist(DonateDto donateDto) {
        final int amount = Integer.parseInt(donateDto.getInputText());
        final Set<Donate> entityByAmountAndLogin =
                donateRepository.getEntityByAmountAndLogin(amount, donateDto.getLogin());
        return !entityByAmountAndLogin.isEmpty();
    }
}
