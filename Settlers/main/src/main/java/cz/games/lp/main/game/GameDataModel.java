package cz.games.lp.main.game;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.enums.Phases;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Setter
@Getter
public class GameDataModel {

    private List<Integer> commonCards;
    private List<Integer> factionCards;
    private Factions selectedFaction;
    private int round;
    private Phases currentPhase;

    /**
     * 1 (set 1.st round)
     * 2 (prepare common cards)
     * 3 (faction choice)
     * 4 (first 4 cards)
     */
    public void newGame(int commonCardCount, int factionCardCount) {
        // 1 (set 1.st round)
        setRound(1);
        currentPhase = Phases.PRODUCTION;
        // 2 (prepare common cards)
        commonCards = fillCards(commonCardCount);
        // 3 (faction choice)
        selectedFaction = Factions.BARBARIAN_M;
        factionCards = fillCards(factionCardCount);
        // 4 (first 4 cards) (in frontend)
    }

    private List<Integer> fillCards(int cardCount) {
        List<Integer> listOfCards = IntStream.range(1, cardCount + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(listOfCards);
        return listOfCards;
    }
}
