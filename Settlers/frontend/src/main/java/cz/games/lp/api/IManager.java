package cz.games.lp.api;

import cz.games.lp.components.Card;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Sex;
import cz.games.lp.enums.Sources;

import java.util.List;

public interface IManager {

    String getTitle();

    Sources[] getSources();

    CardType[] getCardTypes();

    List<Card> prepareCards(double cardWidth, double cardHeight);

    List<Card> prepareCards(double cardWidth, double cardHeight, String fraction, int cardCount);

    String getFraction();

    String getFractionBoard();

    void setFractionAndSex(String fraction, Sex sex);
}
