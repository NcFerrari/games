package cz.games.lp.frontend.actions;

import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Animation;
import javafx.application.Platform;

public class ActionManager {

    private final CardMoveActions cardMoveActions;
    private final ProductionActions productionActions;

    public ActionManager(CommonModel model) {
        cardMoveActions = new CardMoveActions(model);
        productionActions = new ProductionActions(model);
    }

    public void drawFactionCard(int cardCount) {
        Animation[] animations = new Animation[cardCount];
//        IntStream.range(0, cardCount).forEach(i -> animations[i] = cardMoveActions.drawFactionCard());
        Platform.runLater(() -> cardMoveActions.drawMoreCards(null, animations));
    }

    public void drawCommonCard(int cardCount) {
        Animation[] animations = new Animation[cardCount];
//        IntStream.range(0, cardCount).forEach(i -> animations[i] = cardMoveActions.drawCommonCard());
        Platform.runLater(() -> cardMoveActions.drawMoreCards(null, animations));
    }

    public void prepareFirstFourCards() {
//        Platform.runLater(() -> cardMoveActions.drawMoreCards(
//                null,
//                cardMoveActions.drawFactionCard(),
//                cardMoveActions.drawFactionCard(),
//                cardMoveActions.drawCommonCard(),
//                cardMoveActions.drawCommonCard()
//        ));
    }

    public void proceedLookout(Runnable switchPhase) {
//        cardMoveActions.drawMoreCards(
//                switchPhase,
//                cardMoveActions.drawFactionCard(),
//                cardMoveActions.drawCommonCard(),
//                cardMoveActions.drawCommonCard()
//        );
    }

    public void proceedProduction(Runnable switchPhase) {
        productionActions.proceedProduction(switchPhase);
    }
}
