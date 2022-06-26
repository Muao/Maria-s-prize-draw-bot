package prizedrowtelegrambot.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    private boolean checked;
}
