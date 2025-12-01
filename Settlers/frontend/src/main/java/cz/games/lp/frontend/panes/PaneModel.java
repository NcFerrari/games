package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.actions.ActionManager;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.frontend.components.Card;
import cz.games.lp.frontend.components.Deals;
import cz.games.lp.frontend.components.FactionBoard;
import cz.games.lp.frontend.components.RoundPhases;
import cz.games.lp.frontend.components.ScoreBoard;
import cz.games.lp.frontend.components.SourceStatusBlock;
//import cz.games.lp.backend.frontend.enums.CardType;
//import cz.games.lp.backend.frontend.enums.Sources;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PaneModel {

    //    private final Map<Sources, SourceStatusBlock> sources = new LinkedHashMap<>();
//    private final Map<CardType, HBox> builtFactionCards = new LinkedHashMap<>();
//    private final Map<CardType, HBox> builtCommonCards = new LinkedHashMap<>();
//    private final Map<CardType, Integer> cardSizeMap = new EnumMap<>(CardType.class);
    private final StackPane factionCardsStack = new StackPane();
    private final StackPane commonCardsStack = new StackPane();
    private final HBox cardsInHand = new HBox();
    private IManager manager;
    private Pane frontPane;
    private Duration cardSpeed;
    private String style;
    private String headerStyle;
    private List<Card> factionCards;
    private List<Card> commonCards;
    private Card factionCard;
    private Card commonCard;
    private FactionBoard factionBoard;
    private ScoreBoard scoreBoard;
    private RoundPhases roundPhases;
    private ActionManager actionManager;
    private Deals deals;
    private boolean cardInProcess;
}
