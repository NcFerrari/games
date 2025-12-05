package cz.games.lp.frontend.actions;

import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.models.CommonModel;
import cz.games.lp.frontend.panes.CardDeckPane;
import javafx.geometry.Bounds;

public record CardMoveActions(CommonModel model) {

    public void drawCard(CardDeckPane cardDeck, String cardPrefix, Integer cardId) {
        if (model.isTransitionRunning()) {
            return;
        }
        Card card = cardDeck.getMovingCard();
        model.getFrontPane().getChildren().add(card.getNodes());
        Bounds boundsFrom = cardDeck.getChildren().getFirst().localToScene(cardDeck.getChildren().getFirst().getBoundsInLocal());
        card.getNodes().setLayoutX(boundsFrom.getMinX());
        card.getNodes().setLayoutY(boundsFrom.getMinY());
        card.getNodes().setTranslateX(0);
        card.getNodes().setTranslateY(0);
        card.setGoalX(model.getCardsInHand().localToScene(model.getCardsInHand().getBoundsInLocal()).getMinX() - boundsFrom.getMinX());
        card.setGoalY(model.getCardsInHand().localToScene(model.getCardsInHand().getBoundsInLocal()).getMinY() - boundsFrom.getMinY());
        card.setOnFinishedAdditional(() -> {
            model.getFrontPane().getChildren().remove(card.getNodes());
            String cardPath = String.format("%s/%s0%s%d", cardPrefix, cardPrefix.substring(0, 3), (cardId < 10 ? "0" : ""), cardId);
            model.getCardsInHand().getChildren().add(new Card(cardPath, model).getNodes());
            cardDeck.removeCard();
        });
        card.execute();
    }
}
