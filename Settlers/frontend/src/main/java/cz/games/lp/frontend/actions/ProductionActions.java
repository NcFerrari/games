package cz.games.lp.frontend.actions;

import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.ProductionBlocks;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
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
    private double moveFactionBy;
    private double factionHValue;
    private double moveDealBy;
    private double dealVvalue;
    private double moveCommonBy;
    private double commonHvalue;
    private ObservableList<Node> factionCards;
    private ObservableList<Node> deals;
    private ObservableList<Node> commonCards;

    public ProductionActions(CommonModel model) {
        this.model = model;
        selectedCard = new Card(model);
    }

    public Consumer<Long> proceedProduction(ActionManager actionManager) {
        updateData();
        block = ProductionBlocks.FACTIONS;
        return time -> {
            if (time - stopTime.get() < DELAY * 1_000_000) {
                return;
            }
            stopTime.set(time);
            switch (block) {
                case ProductionBlocks.FACTIONS -> factions(factionCards);
                case ProductionBlocks.DEALS -> deals(deals);
                case ProductionBlocks.FACTION_BOARD -> factionBoard();
                case ProductionBlocks.COMMONS -> commons(commonCards);
                default -> {
                    selectedCard.deselect();
                    actionManager.setAnimationRunning(false);
                    actionManager.stop();
                }
            }
        };
    }

    private void updateData() {
        factionCards = ((HBox) model.getFactionCards().get(CardTypes.PRODUCTION).getContent()).getChildren();
        deals = model.getDeals().getDeals();
        commonCards = ((HBox) model.getCommonCards().get(CardTypes.PRODUCTION).getContent()).getChildren();
        moveFactionBy = 1.0 / (factionCards.size() - 1);
        factionHValue = 0;
        moveDealBy = 1.0 / (deals.size() - 1);
        dealVvalue = 0;
        moveCommonBy = 1.0 / (commonCards.size() - 1);
        commonHvalue = 0;
        counter.set(factionCards.size());
    }

    private void selectCard(ObservableList<Node> list, ScrollPane scrollPane, double hValue, double vValue, ProductionBlocks nextProductionBlock) {
        selectedCard.deselect();
        selectedCard = (Card) list.get(counter.getAndIncrement());

        selectedCard.select();
        smoothScroll(scrollPane, hValue, vValue);
        //PROCESS
        if (counter.get() == 0) {
            block = nextProductionBlock;
        }
    }

    private void factions(ObservableList<Node> factionCards) {
        selectedCard.deselect();
        selectedCard = (Card) factionCards.get(counter.decrementAndGet());

        selectedCard.select();
        smoothScroll(model.getFactionCards().get(CardTypes.PRODUCTION), factionHValue, 0);
        addSourceWithEffect(List.of(Sources.STONE, Sources.WOOD));
        if (counter.get() == 0) {
            block = ProductionBlocks.DEALS;
        }
        factionHValue += moveFactionBy;
    }

    private void deals(ObservableList<Node> deals) {
        selectedCard.deselect();
        selectedCard = (Card) deals.get(counter.getAndIncrement());

        selectedCard.select();
        smoothScroll(model.getDeals(), 0, dealVvalue);
        //PROCESS DEAL
        if (counter.get() == deals.size()) {
            block = ProductionBlocks.FACTION_BOARD;
        }
        dealVvalue += moveDealBy;
    }

    private void factionBoard() {
        selectedCard.deselect();
        model.getFactionBoard().select();
        //PROCESS BOARD
        model.getFactionBoard().deselect();
        counter.set(0);
        block = ProductionBlocks.COMMONS;
    }

    private void commons(ObservableList<Node> commonCards) {
        selectedCard.deselect();
        selectedCard = (Card) commonCards.get(counter.getAndIncrement());

        selectedCard.select();
        smoothScroll(model.getCommonCards().get(CardTypes.PRODUCTION), commonHvalue, 0);
        //PROCESS CARD
        if (counter.get() == commonCards.size()) {
            block = ProductionBlocks.DEFAULT;
        }
        commonHvalue += moveCommonBy;
    }

    private void smoothScroll(ScrollPane scrollPane, double targetHvalue, double targetVvalue) {
        Timeline timeline = new Timeline();

        KeyValue kv = new KeyValue(scrollPane.hvalueProperty(), targetHvalue, Interpolator.EASE_BOTH);
        if (targetHvalue == 0) {
            kv = new KeyValue(scrollPane.vvalueProperty(), targetVvalue, Interpolator.EASE_BOTH);
        }
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private void addSourceWithEffect(List<Sources> sources) {
        SequentialTransition sequentialTransition = new SequentialTransition();
        sources.forEach(source -> {
            ImageNode imageNode = new ImageNode(model.getUIConfig().getFactionTokenWidth(), model.getUIConfig().getFactionTokenHeight());
            imageNode.setImage("source/" + source.name().toLowerCase());
            imageNode.getImageView().setX(model.getUIConfig().getCardWidth() / 2 - imageNode.getImageView().getFitWidth() / 2);
            imageNode.getImageView().setY(3 * model.getUIConfig().getCardHeight() / 4 - imageNode.getImageView().getFitHeight() / 2);
            imageNode.getImageView().setVisible(false);
            selectedCard.getChildren().add(imageNode.getImageView());

            Transition transition = new Transition() {
                @Override
                protected void interpolate(double v) {
                    imageNode.getImageView().setVisible(true);
                    model.getOwnSupplies().get(source).addOne();
                }
            };

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(DELAY / sources.size()), imageNode.getImageView());
            translateTransition.setToY(-2 * model.getUIConfig().getFactionTokenHeight());

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(DELAY / sources.size()), imageNode.getImageView());
            fadeTransition.setToValue(0);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(DELAY / sources.size()), imageNode.getImageView());
            scaleTransition.setToX(2);
            scaleTransition.setToY(2);

            ParallelTransition parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(transition, translateTransition, fadeTransition, scaleTransition);
            parallelTransition.setOnFinished(e -> selectedCard.getChildren().remove(imageNode.getImageView()));

            sequentialTransition.getChildren().add(parallelTransition);
        });
        sequentialTransition.play();
    }
}
