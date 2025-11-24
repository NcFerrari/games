package cz.games.lp.panes;

import cz.games.lp.Actions;
import cz.games.lp.api.IManager;
import cz.games.lp.components.Card;
import cz.games.lp.components.Deals;
import cz.games.lp.components.FactionBoard;
import cz.games.lp.components.RoundPhases;
import cz.games.lp.components.ScoreBoard;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Sources;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PaneModel {

    private IManager manager;
    private Pane frontPane;
    private double width;
    private double height;
    private double cardWidth;
    private double cardHeight;
    private double scoreXMove;
    private double scoreYMove;
    private Duration cardSpeed;
    private String style;
    private String headerStyle;
    private Map<Sources, SourceStatusBlock> sources;
    private Map<CardType, HBox> builtFactionCards;
    private Map<CardType, HBox> builtCommonCards;
    private List<Card> factionCards;
    private List<Card> commonCards;
    private Card factionCard;
    private Card commonCard;
    private FactionBoard factionBoard;
    private ScoreBoard scoreBoard;
    private RoundPhases roundPhases;
    private Deals deals;
    private HBox cardsInHand;
    private StackPane factionCardsStack;
    private StackPane commonCardsStack;
    private Actions actions;
    private boolean sequentialTransitionRunning;
}
