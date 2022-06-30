package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RandomDrawService {
    TicketService ticketService;

    public Set<Long>doDraw() {
        final Set<Long> result = new HashSet<>();
        final Set<Long> allTicketsIds = ticketService.getAllTicketsIds();
        for (int i = 1; i <= 4; i++) {
            final long generatorFrom = 1;
            final long generatorTo = allTicketsIds.size();
            final long winnerId = new RandomDataGenerator().nextLong(generatorFrom, generatorTo);
            result.add(winnerId);
            allTicketsIds.remove(winnerId);
        }
        return result;
    }
}
