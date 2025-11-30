package cz.games.lp.files;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import cz.games.lp.dto.CardDTO;

import java.io.File;
import java.util.List;

public class JsonCreator {

    ObjectMapper mapper = new ObjectMapper();

    public List<CardDTO> loadCardData() {
        return mapper.readValue(getClass().getClassLoader().getResourceAsStream("cards.json"), new TypeReference<>() {
        });
    }

    public void createData(List<CardDTO> cards) {
        mapper.writeValue(new File("cards.json"), cards);
    }
}
