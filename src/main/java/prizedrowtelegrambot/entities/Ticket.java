package prizedrowtelegrambot.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ticketId;
    private String login;
    private Date date;

    @Override
    public String toString() {
        return login + " " + ticketId;
    }
}
