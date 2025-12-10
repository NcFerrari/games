package cz.games.lp.main;

import cz.games.lp.backend.BackendManager;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.game.GameData;
import cz.games.lp.frontend.MainApp;
import cz.games.lp.frontend.api.IManager;
import lombok.Getter;

import java.util.List;

public class Manager implements IManager {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;
    private static final int ANIMATION_SPEED = 300;
    private final BackendManager backendManager = new BackendManager();
    @Getter
    private final GameData gameData;

    public static void main(String[] args) {
        new Manager().start();
    }

    private Manager() {
        gameData = new GameData(FACTION_CARD_COUNT, COMMON_CARD_COUNT);
    }

    private void start() {
        backendManager.log(getClass()).info("starting Application...");
        backendManager.prepareCardAndFactionData();
        backendManager.log(getClass()).info("creating new game");
        MainApp.run(this);
    }

    @Override
    public CardDTO getCardData(String cardId) {
        return backendManager.getCards().get(cardId);
    }

    @Override
    public FactionDTO getFaction(String faction) {
        return backendManager.getFactions().get(faction);
    }

    @Override
    public int getAnimationSpeed() {
        return ANIMATION_SPEED;
    }

    @Override
    public List<CardDTO> getMockList() {
        return backendManager.getCards().values().stream().filter(card -> card.getCondition() != null).toList();
    }
}