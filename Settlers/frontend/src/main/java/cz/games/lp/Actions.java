package cz.games.lp;

import cz.games.lp.components.Card;
import cz.games.lp.panes.PaneModel;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public record Actions(PaneModel model) {

    public void prepareFirstFourCards() {
        Platform.runLater(() -> {
            SequentialTransition sequentialTransition = new SequentialTransition(
                    moveCard(model.getFactionCard(), model.getCardsInHand(), getFactionCard(), model.getFactionCards()),
                    moveCard(model.getFactionCard(), model.getCardsInHand(), getFactionCard(), model.getFactionCards()),
                    moveCard(model.getCommonCard(), model.getCardsInHand(), getCommonCard(), model.getCommonCards()),
                    moveCard(model.getCommonCard(), model.getCardsInHand(), getCommonCard(), model.getCommonCards())
            );
            sequentialTransition.play();
        });
    }

    private Card getFactionCard() {
        return new Card(model.getManager().getFaction(), model);
    }

    private Card getCommonCard() {
        return new Card("common", model);
    }

    private TranslateTransition moveCard(Pane nodeFrom, Pane paneTo, Node card) {
        return moveCard(nodeFrom, paneTo, card, null);
    }

    private TranslateTransition moveCard(Node nodeFrom, Pane paneTo, Node card, List<Card> listOfCards) {
        Bounds boundsFrom = nodeFrom.localToScene(nodeFrom.getBoundsInLocal());
        card.setLayoutX(boundsFrom.getMinX());
        card.setLayoutY(boundsFrom.getMinY());
        model.getFrontPane().getChildren().add(card);
        Bounds boundsTo = paneTo.localToScene(paneTo.getBoundsInLocal());
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), card);
        transition.setToX(boundsTo.getMinX() - boundsFrom.getMinX());
        transition.setToY(boundsTo.getMinY() - boundsFrom.getMinY());
        transition.setOnFinished(e -> {
            if (listOfCards == null) {
                paneTo.getChildren().add(card);
                card.setTranslateX(0);
                card.setTranslateY(0);
            } else {
                model.getFrontPane().getChildren().remove(card);
                paneTo.getChildren().addFirst(listOfCards.getFirst());
                listOfCards.remove(listOfCards.getFirst());
            }
        });
        return transition;
    }
}
