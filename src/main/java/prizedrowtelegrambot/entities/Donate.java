package prizedrowtelegrambot.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Donate")
@Data
@ToString
public class Donate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long totalNeedsToPay;
    private int amountOfTickets;
    private String login;
    private String userName;
    private Date date;
    private String chatId;
    private boolean checked;
    private Date checkingDate;
    private String checkerLogin;
    @OneToMany
    private Set<Ticket> tickets;
}
