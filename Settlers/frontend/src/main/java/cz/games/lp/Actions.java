package cz.games.lp;

import cz.games.lp.components.Card;
import cz.games.lp.panes.PaneModel;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class Actions {

    private final PaneModel model;

    private boolean isTranslateTransactionRunning;

    public Actions(PaneModel model) {
        this.model = model;
    }

    public void prepareFirstFourCards() {
        Platform.runLater(() -> {
            if (model.isSequentialTransitionRunning()) {
                return;
            }
            SequentialTransition sequentialTransition = new SequentialTransition(
                    drawFactionCard(false),
                    drawFactionCard(false),
                    drawCommonCard(false),
                    drawCommonCard(false)
            );
            sequentialTransition.setOnFinished(event -> model.setSequentialTransitionRunning(false));
            model.setSequentialTransitionRunning(true);
            sequentialTransition.play();
        });
    }

    public TranslateTransition moveCard(Pane nodeFrom, Pane paneTo, Node card) {
        return moveCard(nodeFrom, paneTo, card, null, false);
    }

    public void drawFactionCard() {
        drawFactionCard(true);
    }

    public void drawCommonCard() {
        drawCommonCard(true);
    }

    public Animation drawFactionCard(boolean play) {
        return drawCard(model.getFactionCard(), model.getCardsInHand(), getFactionCard(), model.getFactionCards(), model.getFactionCardsStack(), play);
    }

    public Animation drawCommonCard(boolean play) {
        return drawCard(model.getCommonCard(), model.getCardsInHand(), getCommonCard(), model.getCommonCards(), model.getCommonCardsStack(), play);
    }

    public Animation drawCard(Card nodeFrom, HBox toPane, Card card, List<Card> list, StackPane cardStack, boolean play) {
        if (disableToMove(list)) {
            return new PauseTransition(Duration.ZERO);
        }
        Animation animation = moveCard(nodeFrom, toPane, card, list);
        if (list.size() == 1) {
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

    private boolean disableToMove(List<Card> list) {
        boolean runningSingleTransition = isTranslateTransactionRunning;
        boolean emptyList = list.isEmpty();
        boolean runningSequenceTransition = model.isSequentialTransitionRunning();
        return runningSingleTransition || emptyList || runningSequenceTransition;
    }
}
