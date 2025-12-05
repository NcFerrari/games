package cz.games.lp.frontend.api;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.game.GameData;

public interface IManager {

    GameData getGameData();

    CardDTO getCard(String cardId);

    int getAnimationSpeed();
}
