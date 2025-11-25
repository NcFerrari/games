package cz.games.lp;

import cz.games.lp.files.JsonCreator;
import cz.games.lp.service.LoggerService;
import cz.games.lp.service_impl.LoggerServiceImpl;

public class BackendManager {

    private final LoggerService logger = LoggerServiceImpl.getInstance(BackendManager.class);

    public void loadCardData() {
        new Thread(() -> {
            new JsonCreator().loadCardData();
            logger.info("Data loading finished");
        }).start();
    }
}
