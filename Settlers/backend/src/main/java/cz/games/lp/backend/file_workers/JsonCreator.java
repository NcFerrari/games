package cz.games.lp.backend.file_workers;

import cz.games.lp.backend.BackendManager;
import cz.games.lp.backend.json_object.CardJSON;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class JsonCreator {

    private final BackendManager backendManager;

    public JsonCreator(BackendManager backendManager) {
        this.backendManager = backendManager;
    }

    public List<CardJSON> loadCardData(String filePath) {
        backendManager.log(getClass()).info("loading {} file", filePath);
        InputStream jsonPathWithCardData = getClass().getClassLoader().getResourceAsStream(filePath);
        return new ObjectMapper().readValue(jsonPathWithCardData, new TypeReference<>() {
        });
    }
}
