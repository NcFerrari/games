package cz.games.lp.files;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import cz.games.lp.dto.CardDTO;

import java.util.List;

public class JsonCreator {

    public List<CardDTO> loadCardData() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getClass().getClassLoader().getResourceAsStream("cards.json"), new TypeReference<>() {
        });
    }
}
