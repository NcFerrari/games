package cz.games.lp.panes;

import cz.games.lp.api.IManager;
import cz.games.lp.components.Deals;
import cz.games.lp.components.FactionBoard;
import cz.games.lp.components.RoundPhases;
import cz.games.lp.components.ScoreBoard;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Sources;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

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
    private String style;
    private String headerStyle;
    private Map<Sources, SourceStatusBlock> sources;
    private Map<CardType, HBox> factionCards;
    private Map<CardType, HBox> commonCards;
    private FactionBoard factionBoard;
    private ScoreBoard scoreBoard;
    private RoundPhases roundPhases;
    private Deals deals;
    private HBox cardsInHand;
}
