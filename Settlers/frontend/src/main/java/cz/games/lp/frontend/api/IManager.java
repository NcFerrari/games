package cz.games.lp.frontend.api;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.game.GameData;

import java.util.List;

public interface IManager {

    GameData getGameData();

    CardDTO getCardData(String cardId);

    FactionDTO getFaction(String faction);

    int getAnimationSpeed();

    List<CardDTO> getMockList();
}
