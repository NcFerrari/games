package cz.games.lp.api;

import cz.games.lp.components.Card;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Phases;
import cz.games.lp.enums.Sex;
import cz.games.lp.enums.Sources;

import java.util.List;

public interface IManager {

    String getTitle();

    Sources[] getSources();

    CardType[] getCardTypes();

    List<Card> prepareCards(double cardWidth, double cardHeight);

    List<Card> prepareCards(double cardWidth, double cardHeight, String faction, int cardCount);

    String getFaction();

    String getFactionBoard();

    void setFactionAndSex(String faction, Sex sex);

    Phases getCurrentPhase();

    void setCurrentPhase(Phases currentPhase);

    String getChooserDialogTitle();

    String[] getFactionArray();

    String[] getSexArray();

    String getStartNewGameButtonTtitle();
}
