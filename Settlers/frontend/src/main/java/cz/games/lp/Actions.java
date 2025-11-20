package cz.games.lp;

import cz.games.lp.components.Card;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.Sources;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Map;

public class Actions {

    private final HBox cardsInHand;
    private final Map<Sources, SourceStatusBlock> sourceStatusMap;

    public Actions(HBox cardsInHand, Map<Sources, SourceStatusBlock> sourceStatusMap) {
        this.cardsInHand = cardsInHand;
        this.sourceStatusMap = sourceStatusMap;
    }

    public void addSettler(int count) {
        sourceStatusMap.get(Sources.SETTLER).add(count);
    }

    public void removeSettler(int count) {
        sourceStatusMap.get(Sources.SETTLER).remove(count);
    }

    public void addCardIntoHand(List<Card> cards, StackPane cardStack) {
        if (sourceStatusMap.get(Sources.SETTLER).getValue() < 2 || cards.isEmpty()) {
            return;
        }
        removeSettler(2);
        cardsInHand.getChildren().add(cards.getFirst());
        cards.removeFirst();
        if (cards.isEmpty() && cardStack != null) {
            cardStack.getChildren().clear();
        }
    }
}
