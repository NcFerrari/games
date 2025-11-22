package cz.games.lp;

import cz.games.lp.api.IManager;
import cz.games.lp.components.Card;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.Sources;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Map;

public record ActionMessenger(IManager manager,
                              HBox cardsInHand,
                              Map<Sources, SourceStatusBlock> sourceStatusMap,
                              double cardWidth,
                              double cardHeight,
                              Pane frontPane,
                              List<Card> factionCards,
                              List<Card> commonCards) {

}
