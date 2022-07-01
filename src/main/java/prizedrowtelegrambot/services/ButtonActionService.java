package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import prizedrowtelegrambot.entities.Donate;
import prizedrowtelegrambot.entities.Ticket;
import prizedrowtelegrambot.enums.BotMessage;
import prizedrowtelegrambot.repositories.DonateRepository;
import prizedrowtelegrambot.telegram.Bot;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ButtonActionService{
        DonateRepository donateRepository;
        TicketService ticketService;
        UserMessageService userMessageService;
public String acceptAction(String donateId,String checkerLogin,Bot bot){
        String result=String.format(BotMessage.CAN_NOT_GET_DONATE_ENTITY.getMessage(),donateId);
final long id=Long.parseLong(donateId);
final Optional<Donate> optionalDonate=donateRepository.findById(id);
        if(optionalDonate.isPresent()){
final Donate donate=optionalDonate.get();
        if(!donate.isChecked()&&donate.getCheckerLogin()==null){
final long totalNeedsToPay=donate.getTotalNeedsToPay();
final int ticketsCount=donate.getAmountOfTickets();
final Set<Ticket> tickets=new HashSet<>();
final Set<String> ticketsIds=new HashSet<>();
        for(int i=0;i<ticketsCount; i++){
final Ticket ticket=ticketService.createTicket(donate.getLogin());
        tickets.add(ticket);
        ticketsIds.add(ticket.getTicketId().toString());
        }
        donate.setChecked(true);
        donate.setCheckingDate(new Date());
        donate.setCheckerLogin(checkerLogin);
        donate.setTickets(tickets);
        donateRepository.save(donate);

        result=String.format(BotMessage.SUCCESS_CONFIRMATION_ADMIN.getMessage(),
        checkerLogin,ticketsIds.size(),donate.getLogin(),String.join("\n",ticketsIds));

        userMessageService.sendSuccessConfirmationMessage(bot,totalNeedsToPay,ticketsIds,donate.getChatId());
        }else{
        result=String.format(BotMessage.USER_PAYMENT_VALIDATION_DONE_BEFORE.getMessage(),
        donate.getCheckerLogin(),donate.getCheckingDate(),donate.isChecked());
        }
        }
        return result;
        }

public String declineAction(String donateId,String checkerLogin,Bot bot){
        String result=String.format(BotMessage.CAN_NOT_GET_DONATE_ENTITY.getMessage(),donateId);
final long id=Long.parseLong(donateId);
final Optional<Donate> optionalDonate=donateRepository.findById(id);
        if(optionalDonate.isPresent()){
final Donate donate=optionalDonate.get();
        if(!donate.isChecked()&&donate.getCheckerLogin()==null){
final long totalNeedsToPay=donate.getTotalNeedsToPay();
        donate.setChecked(false);
        donate.setCheckingDate(new Date());
        donate.setCheckerLogin(checkerLogin);
        donateRepository.save(donate);

        result=String.format(BotMessage.DECLINE_PAYMENT_MESSAGE_ADMIN.getMessage(),
        donate.getTotalNeedsToPay(),donate.getLogin(),checkerLogin);

        userMessageService.sendDeclinePaymentMessage(bot,totalNeedsToPay,donate.getChatId());
        }else{
        result=String.format(BotMessage.USER_PAYMENT_VALIDATION_DONE_BEFORE.getMessage(),
        donate.getCheckerLogin(),donate.getCheckingDate(),donate.isChecked());
        }
        }
        return result;
        }
        }
