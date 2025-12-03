package cz.games.lp.main;

import cz.games.lp.backend.BackendManager;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.frontend.MainApp;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.common.game.GameData;
import lombok.Getter;

public class Manager implements IManager {

    private final BackendManager backendManager = new BackendManager();
    @Getter
    private final GameData gameData = new GameData();

    public static void main(String[] args) {
        new Manager().start();
    }

    private void start() {
        backendManager.log(getClass()).info("starting Application...");
        backendManager.prepareCardData();
        backendManager.log(getClass()).info("creating new game");
        MainApp.run(this);
    }

    @Override
    public CardDTO getCard(String cardId) {
        return backendManager.getCards().get(cardId);
    }
}