package cz.games.lp.actions;

import cz.games.lp.components.Card;
import cz.games.lp.enums.CardType;
import cz.games.lp.panes.PaneModel;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class CardMoveActions {

    private final PaneModel model;

    private boolean isTranslateTransactionRunning;
    private boolean sequentialTransitionRunning;

    public CardMoveActions(PaneModel model) {
        this.model = model;
    }

    public Animation drawFactionCard(boolean play) {
        return drawCard(model.getFactionCard(), model.getCardsInHand(), getFactionCard(), model.getFactionCards(), model.getFactionCardsStack(), CardType.FACTION, play);
    }

    public Animation drawCommonCard(boolean play) {
        return drawCard(model.getCommonCard(), model.getCardsInHand(), getCommonCard(), model.getCommonCards(), model.getCommonCardsStack(), CardType.COMMON, play);
    }

    public void drawMoreCards(Animation... animations) {
        if (sequentialTransitionRunning) {
            return;
        }
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(animations);
        sequentialTransition.setOnFinished(event -> sequentialTransitionRunning = false);
        sequentialTransitionRunning = true;
        sequentialTransition.play();
    }

    private Animation drawCard(Card nodeFrom, HBox toPane, Card card, List<Card> list, StackPane cardStack, CardType typ, boolean play) {
        if (disableToMove(model.getCardSizeMap().get(typ))) {
            return new PauseTransition(Duration.ZERO);
        }
        model.getCardSizeMap().replace(typ, model.getCardSizeMap().get(typ) - 1);
        Animation animation = moveCard(nodeFrom, toPane, card, list);
        if (model.getCardSizeMap().get(typ) == 0) {
            cardStack.getChildren().clear();
        }
        if (play) {
            isTranslateTransactionRunning = true;
            animation.play();
        }
        return animation;
    }

    private Animation moveCard(Node nodeFrom, Pane paneTo, Node card, List<Card> listOfCards) {
        Bounds boundsFrom = nodeFrom.localToScene(nodeFrom.getBoundsInLocal());
        card.setLayoutX(boundsFrom.getMinX());
        card.setLayoutY(boundsFrom.getMinY());
        model.getFrontPane().getChildren().add(card);
        Bounds boundsTo = paneTo.localToScene(paneTo.getBoundsInLocal());
        TranslateTransition translateTransition = new TranslateTransition(model.getCardSpeed(), card);
        translateTransition.setToX(boundsTo.getMinX() - boundsFrom.getMinX());
        translateTransition.setToY(boundsTo.getMinY() - boundsFrom.getMinY());
        translateTransition.setOnFinished(e -> {
            if (listOfCards == null) {
                paneTo.getChildren().add(card);
                card.setTranslateX(0);
                card.setTranslateY(0);
            } else {
                model.getFrontPane().getChildren().remove(card);
                paneTo.getChildren().addFirst(listOfCards.getFirst());
                listOfCards.remove(listOfCards.getFirst());
            }
            isTranslateTransactionRunning = false;
        });
        return translateTransition;
    }

    private Card getFactionCard() {
        return new Card(model.getManager().getFaction(), model);
    }

    private Card getCommonCard() {
        return new Card("common", model);
    }

    private boolean disableToMove(int cardListSize) {
        return isTranslateTransactionRunning || cardListSize == 0 || sequentialTransitionRunning;
    }
}
