package cz.games.lp.backend;

import cz.games.lp.backend.json_object.CardJSON;
import cz.games.lp.backend.files.JsonCreator;
import cz.games.lp.backend.service.LoggerService;
import cz.games.lp.backend.service_impl.LoggerServiceImpl;
import lombok.Getter;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BackendManager {

    private final LoggerService logger = LoggerServiceImpl.getInstance();

    @Getter
    private Map<String, CardJSON> cardMap;

    public void loadAllCardData() {
        log(getClass()).info("loadAllCardData");
        long timeStart = System.currentTimeMillis();
        log(getClass()).info("starting reading card data from json");
        List<CardJSON> jsonData = new JsonCreator().loadCardData();
        cardMap = jsonData.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
        log(getClass()).info("Data loaded in {} ms", System.currentTimeMillis() - timeStart);
    }

    public <T> Logger log(Class<T> clazz) {
        return logger.log(clazz);
    }
}
