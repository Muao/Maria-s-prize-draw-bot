package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import prizedrowtelegrambot.entities.ChatAdmin;
import prizedrowtelegrambot.repositories.ChatAdminRepository;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ChatAdminService {
    ChatAdminRepository chatAdminRepository;

    public boolean isAdminUser(String login){
        final ChatAdmin chatAdmin = chatAdminRepository.findByLogin(login);
        return chatAdmin != null;
    }
}
