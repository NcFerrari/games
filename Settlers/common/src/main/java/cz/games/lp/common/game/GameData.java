package cz.games.lp.common.game;

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
public class GameData {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;
    private List<Integer> commonCards;
    private List<Integer> factionCards;
    private Factions selectedFaction;
    private int round;
    private Phases currentPhase;

    public void newGame() {
        setRound(1);
        currentPhase = Phases.LOOKOUT;
        commonCards = fillCards(COMMON_CARD_COUNT);
        factionCards = fillCards(FACTION_CARD_COUNT);
    }

    private List<Integer> fillCards(int cardCount) {
        List<Integer> listOfCards = IntStream.range(1, cardCount + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(listOfCards);
        return listOfCards;
    }

    public void nextRound() {
        if (getRound() < 5) {
            setRound(getRound() + 1);
        }
    }
}
