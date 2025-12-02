package cz.games.lp.backend.file_workers;

import cz.games.lp.backend.json_object.CardJSON;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class JsonCreator {

    public List<CardJSON> loadCardData() {
        InputStream jsonPathWithCardData = getClass().getClassLoader().getResourceAsStream("data/cards.json");
        return new ObjectMapper().readValue(jsonPathWithCardData, new TypeReference<>() {
        });
    }
}
