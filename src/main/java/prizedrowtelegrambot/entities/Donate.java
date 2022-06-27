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
    private Long id;
    private int amount;
    private String login;
    private String userName;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String charId;
    private boolean checked;
    @Temporal(TemporalType.DATE)
    private Date checkingDate;
    private String checkerLogin;
    @OneToMany
    private Set<Ticket> tickets;
}
