package cz.games.lp.backend;

import cz.games.lp.backend.json_object.CardJSON;
import cz.games.lp.backend.file_workers.JsonCreator;
import cz.games.lp.backend.mapping.CardMapper;
import cz.games.lp.backend.service.LoggerService;
import cz.games.lp.backend.service_impl.LoggerServiceImpl;
import cz.games.lp.common.dto.CardDTO;
import lombok.Getter;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BackendManager {

    private final LoggerService logger = LoggerServiceImpl.getInstance();
    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);
    @Getter
    private final Map<String, CardDTO> cards = new HashMap<>();
    private Map<String, CardJSON> rawCardMap;

    public void loadAllCardData() {
        log(getClass()).info("loadAllCardData");
        long timeStart = System.currentTimeMillis();
        log(getClass()).info("starting reading card data from json");
        List<CardJSON> jsonData = new JsonCreator().loadCardData();
        rawCardMap = jsonData.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
        log(getClass()).info("Data loaded in {} ms", System.currentTimeMillis() - timeStart);
    }

    public void prepareCardData() {
        log(getClass()).info("loadAndMapCardData");
        Thread loadingDataThread = new Thread(this::loadAllCardData);
        loadingDataThread.start();
        try {
            loadingDataThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        new Thread(() -> {
            log(getClass()).info("getCardMap");
            cardMapper.mapToCardDTO(rawCardMap, cards);
        }).start();
    }

    public <T> Logger log(Class<T> clazz) {
        return logger.log(clazz);
    }
}
