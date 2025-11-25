package cz.games.lp;

import cz.games.lp.dto.CardDTO;
import cz.games.lp.files.JsonCreator;
import cz.games.lp.service.LoggerService;
import cz.games.lp.service_impl.LoggerServiceImpl;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BackendManager {

    private final LoggerService logger = LoggerServiceImpl.getInstance(BackendManager.class);

    @Getter
    private Map<String, CardDTO> cardDtoMap;

    public void loadCardData() {
        new Thread(() -> {
            List<CardDTO> jsonData = new JsonCreator().loadCardData();
            cardDtoMap = jsonData.stream().collect(Collectors.toMap(CardDTO::getId, card -> card));
            logger.getLogger().info("Data loading finished");
        }).start();
    }
}
