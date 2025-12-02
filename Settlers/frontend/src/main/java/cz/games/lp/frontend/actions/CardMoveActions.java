package cz.games.lp.frontend.actions;

import cz.games.lp.frontend.components.Card;
//import cz.games.lp.backend.frontend.enums.CardType;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;

public class CardMoveActions {

    private final CommonModel model;

    private boolean sequentialTransitionRunning;

    public CardMoveActions(CommonModel model) {
        this.model = model;
    }
//
//    public Animation drawFactionCard() {
//        return drawCard(model.getFactionCard(), model.getCardsInHand(), getFactionCard(), model.getFactionCards(), model.getFactionCardsStack(), CardType.FACTION);
//    }
//
//    public Animation drawCommonCard() {
//        return drawCard(model.getCommonCard(), model.getCardsInHand(), getCommonCard(), model.getCommonCards(), model.getCommonCardsStack(), CardType.COMMON);
//    }

    public void drawMoreCards(Runnable command, Animation... animations) {
        if (sequentialTransitionRunning) {
            return;
        }
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(animations);
        sequentialTransition.setOnFinished(event -> {
            sequentialTransitionRunning = false;
            if (command != null) {
                command.run();
            }
        });
        sequentialTransitionRunning = true;
        sequentialTransition.play();
    }

//    private Animation drawCard(Card nodeFrom, HBox toPane, Card card, List<Card> list, StackPane cardStack, CardType typ) {
//        if (disableToMove(model.getCardSizeMap().get(typ))) {
//            return new PauseTransition(Duration.ZERO);
//        }
//        model.getCardSizeMap().replace(typ, model.getCardSizeMap().get(typ) - 1);
//        Animation animation = moveCard(nodeFrom, toPane, card, list);
//        if (model.getCardSizeMap().get(typ) == 0) {
//            cardStack.getChildren().clear();
//        }
//        animation.setOnFinished(evt -> model.setCardInProcess(false));
//        return animation;
//    }

//    private Animation moveCard(Node nodeFrom, Pane paneTo, Node card, List<Card> listOfCards) {
//        Bounds boundsFrom = nodeFrom.localToScene(nodeFrom.getBoundsInLocal());
//        card.setLayoutX(boundsFrom.getMinX());
//        card.setLayoutY(boundsFrom.getMinY());
////        model.getFrontPane().getChildren().add(card);
//        Bounds boundsTo = paneTo.localToScene(paneTo.getBoundsInLocal());
//        TranslateTransition translateTransition = new TranslateTransition(model.getCardSpeed(), card);
//        translateTransition.setToX(boundsTo.getMinX() - boundsFrom.getMinX());
//        translateTransition.setToY(boundsTo.getMinY() - boundsFrom.getMinY());
////        translateTransition.setOnFinished(e -> {
//            if (listOfCards == null) {
//                paneTo.getChildren().add(card);
//                card.setTranslateX(0);
//                card.setTranslateY(0);
//            } else {
////                model.getFrontPane().getChildren().remove(card);
////                paneTo.getChildren().addFirst(listOfCards.getFirst());
////                listOfCards.remove(listOfCards.getFirst());
//            }
//        });
//        return translateTransition;
//    }

//    private Card getFactionCard() {
////        return new Card(model.getManager().getFaction(), model);
//    }

    private Card getCommonCard() {
        return new Card("common", model);
    }

    private boolean disableToMove(int cardListSize) {
        return cardListSize == 0 || sequentialTransitionRunning;
    }
}
