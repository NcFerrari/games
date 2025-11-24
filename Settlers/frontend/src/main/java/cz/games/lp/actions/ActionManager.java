package cz.games.lp.actions;

import cz.games.lp.panes.PaneModel;
import javafx.application.Platform;

public class ActionManager {

    private final CardMoveActions cardMoveActions;

    public ActionManager(PaneModel model) {
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
                cardMoveActions.drawFactionCard(false),
                cardMoveActions.drawFactionCard(false),
                cardMoveActions.drawCommonCard(false),
                cardMoveActions.drawCommonCard(false)
        ));
    }

    public void proceedLookout() {
        cardMoveActions.drawMoreCards(
                cardMoveActions.drawFactionCard(false),
                cardMoveActions.drawCommonCard(false),
                cardMoveActions.drawCommonCard(false)
        );
    }
}
