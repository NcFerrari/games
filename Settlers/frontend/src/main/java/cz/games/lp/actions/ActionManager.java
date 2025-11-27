package cz.games.lp.actions;

import cz.games.lp.panes.PaneModel;
import javafx.application.Platform;

public class ActionManager {

    private final CardMoveActions cardMoveActions;
    private final ProductionActions productionActions;

    public ActionManager(PaneModel model) {
        cardMoveActions = new CardMoveActions(model);
        productionActions = new ProductionActions(model);
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

    public void proceedLookout(Runnable switchPhase) {
        cardMoveActions.drawMoreCards(
                switchPhase,
                cardMoveActions.drawFactionCard(false),
                cardMoveActions.drawCommonCard(false),
                cardMoveActions.drawCommonCard(false)
        );
    }

    public void proceedProduction(Runnable switchPhase) {
        productionActions.proceedProduction(switchPhase);
    }
}
