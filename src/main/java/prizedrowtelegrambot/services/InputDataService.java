package prizedrowtelegrambot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class InputDataService {
    public boolean isPositiveDigit(String inputText) {
        boolean result = false;
        final Pattern pattern = Pattern.compile("\\d+");
        final Matcher matcher = pattern.matcher(inputText);
        if (matcher.matches()) {
            int amount = Integer.parseInt(inputText);
            if(amount > 0) {
                result = true;
            }
        }
        return result;
    }
}
