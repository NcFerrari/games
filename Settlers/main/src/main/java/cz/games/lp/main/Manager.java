package cz.games.lp.main;

import cz.games.lp.backend.BackendManager;
import cz.games.lp.frontend.MainApp;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.main.game.GameDataModel;
import lombok.Getter;

public class Manager implements IManager {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;
    private static final int ANIMATION_SPEED = 400;

    private final BackendManager backendManager = new BackendManager();
    @Getter
    private final GameDataModel gameData = new GameDataModel();

    public static void main(String[] args) {
        new Manager().start();
    }

    private void start() {
        backendManager.log(getClass()).info("starting Application...");
        backendManager.prepareCardData();
        backendManager.log(getClass()).info("creating new game");
        gameData.newGame(COMMON_CARD_COUNT, FACTION_CARD_COUNT);
        MainApp.run(this);
    }

    public int getAnimationSpeed() {
        return ANIMATION_SPEED;
    }
}