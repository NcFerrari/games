package cz.games.lp.api;

import cz.games.lp.components.Card;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Sex;
import cz.games.lp.enums.Sources;

import java.util.List;
import java.util.Map;

public interface IManager {

    String getTitle();

    Sources[] getSources();

    CardType[] getCardTypes();

    List<Card> prepareCards(double cardWidth, double cardHeight, String faction, int cardCount);

    String getFaction();

    String getFactionBoard();

    void setFactionAndSex(String faction, Sex sex);

    String getChooserDialogTitle();

    String[] getFactionArray();

    String[] getSexArray();

    String getStartNewGameButtonTtitle();

    double getWidth();

    double getHeight();

    double getCardWidth();

    double getCardHeight();

    double getScoreYMove();

    double getScoreXMove();

    Integer getCommonCardCount();

    Integer getFactionCardCount();

    double getCardSpeed();

    Card fillCardWithDataAndExecute(Card card, Map<Sources, SourceStatusBlock> sources);
}
