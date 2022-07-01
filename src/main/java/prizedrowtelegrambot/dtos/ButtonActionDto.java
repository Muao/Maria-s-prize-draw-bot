package prizedrowtelegrambot.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import prizedrowtelegrambot.enums.ButtonAction;

import java.util.HashMap;
import java.util.Map;
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ButtonActionDto {
    Object value;
    ButtonAction action;

    public Map<String, Object> toMap() {
        Map<String, Object> raw = new HashMap<>();
        raw.put("value", this.value);
        raw.put("action", this.action);
        return raw;
    }

    @SneakyThrows
    @Override
    public String toString() {
        return new ObjectMapper().writeValueAsString(this.toMap());
    }
}
