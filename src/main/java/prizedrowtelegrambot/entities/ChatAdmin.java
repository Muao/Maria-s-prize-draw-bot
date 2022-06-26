package prizedrowtelegrambot.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "chat_admin")
@Data
@ToString
public class ChatAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String userName;
    private String chartId;
}
