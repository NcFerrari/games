package cz.games.lp;

import cz.games.lp.components.Card;
import cz.games.lp.enums.Sources;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class Actions {

    private final ActionMessenger actionM;
    private final Card movingCommonCardForEffect;
    private Card movingFactionCardForEffect;
    private Card factionCard;
    private Card commonCard;

    public Actions(ActionMessenger actionM) {
        this.actionM = actionM;
        movingCommonCardForEffect = new Card("common", actionM.cardWidth(), actionM.cardHeight());
    }

    public void updateFactionCardForEffect(Card factionCard, Card commonCard) {
        this.factionCard = factionCard;
        this.commonCard = commonCard;
        movingFactionCardForEffect = new Card(actionM.manager().getFaction(), actionM.cardWidth(), actionM.cardHeight());
    }

    public void addSettler(int count) {
        actionM.sourceStatusMap().get(Sources.SETTLER).add(count);
    }

    public void removeSettler(int count) {
        actionM.sourceStatusMap().get(Sources.SETTLER).remove(count);
    }

    public void buyCardIntoHand(List<Card> cards, StackPane cardStack) {
        if (actionM.sourceStatusMap().get(Sources.SETTLER).getValue() < 2 || cards.isEmpty()) {
            return;
        }
        removeSettler(2);
        addCardIntoHand(cards, cardStack);
    }

    public void addCardIntoHand(List<Card> cards, StackPane cardStack) {
        if (cards.isEmpty()) {
            return;
        }
        actionM.cardsInHand().getChildren().add(cards.getFirst());
        cards.removeFirst();
        if (cards.isEmpty() && cardStack != null) {
            cardStack.getChildren().clear();
        }
    }

    public void activateLookupPhase() {
        actionM.frontPane().getChildren().add(movingFactionCardForEffect);
        Bounds boundsFrom = factionCard.localToScene(factionCard.getBoundsInLocal());
        movingFactionCardForEffect.setLayoutX(boundsFrom.getMinX());
        movingFactionCardForEffect.setLayoutY(boundsFrom.getMinY());
        Bounds boundsTo = actionM.cardsInHand().localToScene(actionM.cardsInHand().getBoundsInLocal());
        TranslateTransition factionTransition = translateTransitionSetting(movingFactionCardForEffect, boundsFrom, boundsTo);
        factionTransition.play();
    }

    private TranslateTransition translateTransitionSetting(Card card, Bounds boundsFrom, Bounds boundsTo) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(card);
        transition.setToX(boundsTo.getMinX() - boundsFrom.getMinX());
        transition.setToY(boundsTo.getMinY() - boundsFrom.getMinY());
        transition.setDuration(Duration.millis(800));
        transition.setOnFinished(evt -> {
            actionM.frontPane().getChildren().remove(card);
//            addCardIntoHand(actionM.factionCards(), );
        });
        return transition;
    }
}
