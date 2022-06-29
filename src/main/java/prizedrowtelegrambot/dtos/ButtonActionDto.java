package prizedrowtelegrambot.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import prizedrowtelegrambot.enums.ButtonAction;

import java.util.HashMap;
import java.util.Map;
@Getter
@AllArgsConstructor
public class ButtonActionDto {
    String donateId;
    ButtonAction action;

    public Map<String, Object> toMap() {
        Map<String, Object> raw = new HashMap<>();
        raw.put("donateId", this.donateId);
        raw.put("action", this.action);
        return raw;
    }

    @SneakyThrows
    @Override
    public String toString() {
        return new ObjectMapper().writeValueAsString(this.toMap());
    }
}
