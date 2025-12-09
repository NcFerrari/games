package cz.games.lp.frontend.actions;

import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.ProductionBlocks;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class ProductionActions {

    private static final double DELAY = 1000;
    private final CommonModel model;
    private final AtomicLong stopTime = new AtomicLong();
    private final AtomicInteger counter = new AtomicInteger();
    private Card selectedCard;
    private ProductionBlocks block;

    public ProductionActions(CommonModel model) {
        this.model = model;
        selectedCard = new Card(model);
    }

    public Consumer<Long> proceedProduction(ActionManager actionManager) {
        block = ProductionBlocks.FACTIONS;
        return time -> {
            switch (block) {
                case ProductionBlocks.FACTIONS -> factions();
                case ProductionBlocks.DEALS -> deals();
                case ProductionBlocks.FACTION_BOARD -> factionBoard();
                case ProductionBlocks.COMMONS -> commons();
                default -> actionManager.stop();
            }
        };

//        counter.set(factionCards.size());
//        return time -> {
//            if (time - stopTime.get() < DELAY * 1_000_000) {
//                return;
//            }
//            selectedCard.deselect();
//            if (counter.get() == 0) {
//                actionManager.stop();
//                actionManager.setAnimationRunning(false);
//                return;
//            }
//            stopTime.set(time);
//
//            double hValue = 1.0 / ((HBox) model.getFactionCards().get(CardTypes.PRODUCTION).getContent()).getChildren().size();
//            model.getFactionCards().get(CardTypes.PRODUCTION).setHvalue(1 - hValue * (counter.get() - 1));
//
//            selectedCard = (Card) ((HBox) model.getFactionCards().get(CardTypes.PRODUCTION).getContent()).getChildren().get(counter.decrementAndGet());
//            selectedCard.select();
//            SequentialTransition sequentialTransition = new SequentialTransition();
//
//            model.getManager().getCard(selectedCard.getCardId()).getCardEffect().forEach(effect -> {
//                ImageNode imageNode = new ImageNode(30, 30);
//                imageNode.setImage("source/" + effect.name().toLowerCase());
//                imageNode.getImageView().setX(model.getUIConfig().getCardWidth() / 2 - imageNode.getImageView().getFitWidth() / 2);
//                imageNode.getImageView().setY(3 * model.getUIConfig().getCardHeight() / 4 - imageNode.getImageView().getFitHeight() / 2);
//                imageNode.getImageView().setOpacity(0);
//                selectedCard.getChildren().add(imageNode.getImageView());
//
//                Transition transition = new Transition() {
//                    @Override
//                    protected void interpolate(double v) {
//                        imageNode.getImageView().setOpacity(100);
//                        model.getOwnSupplies().get(Sources.valueOf(effect.name())).addOne();
//                    }
//                };
//
//                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(DELAY / 2), imageNode.getImageView());
//                translateTransition.setToY(-60);
//
//                FadeTransition fadeTransition = new FadeTransition(Duration.millis(DELAY / 2), imageNode.getImageView());
//                fadeTransition.setToValue(0);
//
//                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(DELAY / 2), imageNode.getImageView());
//                scaleTransition.setToX(2);
//                scaleTransition.setToY(2);
//
//                ParallelTransition parallelTransition = new ParallelTransition();
//                parallelTransition.getChildren().addAll(transition, translateTransition, fadeTransition, scaleTransition);
//                parallelTransition.setOnFinished(e -> selectedCard.getChildren().remove(imageNode.getImageView()));
//
//                sequentialTransition.getChildren().add(parallelTransition);
//            });
//            sequentialTransition.play();
//        };
    }

    private void factions() {
        ObservableList<Node> factionCards = ((HBox) model.getFactionCards().get(CardTypes.PRODUCTION).getContent()).getChildren();
        System.out.println("1");
        block = ProductionBlocks.DEALS;
    }

    private void deals() {
        ObservableList<Node> deals = model.getDeals().getDeals();
        System.out.println("2");
        block = ProductionBlocks.FACTION_BOARD;
    }

    private void factionBoard() {
        List<Sources> factionBoardSources = model.getFactionBoard().getFactionData().getFactionProduction();
        System.out.println("3");
        block = ProductionBlocks.COMMONS;
    }

    private void commons() {
        ObservableList<Node> commonCards = ((HBox) model.getCommonCards().get(CardTypes.PRODUCTION).getContent()).getChildren();
        System.out.println("4");
        block = ProductionBlocks.DEFAULT;
    }
}
