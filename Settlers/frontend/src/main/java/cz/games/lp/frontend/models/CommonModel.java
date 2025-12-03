package cz.games.lp.frontend.models;

import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.common.game.GameData;
import cz.games.lp.frontend.actions.ActionManager;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.frontend.components.ChoiceDialog;
import cz.games.lp.frontend.components.FactionBoard;
import cz.games.lp.frontend.components.RoundPhases;
import cz.games.lp.frontend.components.ScoreAndRoundBoard;
import cz.games.lp.frontend.components.Supply;
import cz.games.lp.frontend.components.transition_components.Transitionable;
import cz.games.lp.frontend.enums.TransitionKeys;
import cz.games.lp.frontend.panes.CardDeckPane;
import cz.games.lp.frontend.panes.DealPane;
import cz.games.lp.frontend.panes.SourcePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class CommonModel {

    private final Map<Sources, Supply> ownSupplies = new LinkedHashMap<>();
    private final Map<TransitionKeys, Transitionable> transitionableMap = new EnumMap<>(TransitionKeys.class);
    private final Map<CardTypes, HBox> factionCards = new EnumMap<>(CardTypes.class);
    private final Map<CardTypes, HBox> commonCards = new EnumMap<>(CardTypes.class);
    private final UIConfig uIConfig = new UIConfig();
    private final ActionManager actionManager = new ActionManager(this);
    private final ChoiceDialog choiceDialog = new ChoiceDialog(this);
    private final SourcePane sourcePane = new SourcePane(this);
    private final CardDeckPane factionDeck = new CardDeckPane(this);
    private final CardDeckPane commonDeck = new CardDeckPane(this);
    private final ScoreAndRoundBoard scoreBoard = new ScoreAndRoundBoard(this);
    private final RoundPhases roundPhases = new RoundPhases(this);
    private final DealPane deals = new DealPane(this);
    private final FactionBoard factionBoard = new FactionBoard(this);
    private final HBox cardsInHand = new HBox();
    private IManager manager;
    private Pane frontPane;
    private GameData gameData;
    private Transitionable currentTransition;
}
