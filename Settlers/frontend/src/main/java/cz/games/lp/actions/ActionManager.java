package cz.games.lp.actions;

import cz.games.lp.enums.Phases;
import cz.games.lp.panes.PaneModel;
import javafx.application.Platform;

public class ActionManager {

    private final CardMoveActions cardMoveActions;
    private final PaneModel model;

    public ActionManager(PaneModel model) {
        this.model = model;
        cardMoveActions = new CardMoveActions(model);
    }

    public void drawFactionCard() {
        cardMoveActions.drawFactionCard(true);
    }

    public void drawCommonCard() {
        cardMoveActions.drawCommonCard(true);
    }

    public void prepareFirstFourCards() {
        Platform.runLater(() -> cardMoveActions.drawMoreCards(
                null,
                cardMoveActions.drawFactionCard(false),
                cardMoveActions.drawFactionCard(false),
                cardMoveActions.drawCommonCard(false),
                cardMoveActions.drawCommonCard(false)
        ));
    }

    public void proceedLookout(Runnable command) {
        cardMoveActions.drawMoreCards(
                command,
                cardMoveActions.drawFactionCard(false),
                cardMoveActions.drawCommonCard(false),
                cardMoveActions.drawCommonCard(false)
        );
    }
}
