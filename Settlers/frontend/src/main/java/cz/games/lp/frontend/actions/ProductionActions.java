package cz.games.lp.frontend.actions;

import cz.games.lp.common.enums.CardEffects;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Conditions;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.ProductionBlocks;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.AnimationTimer;
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
import java.util.stream.IntStream;

public class ProductionActions {

    private final AnimationTimer pointAnimation;
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
    private double delay;
    private int scorePointToAdd;
    private boolean pointAnimationIsRunning;

    public ProductionActions(CommonModel model) {
        this.model = model;
        selectedCard = new Card(model);
        pointAnimation = new AnimationTimer() {
            @Override
            public void handle(long time) {
                if (scorePointToAdd == 0) {
                    pointAnimationIsRunning = false;
                    stop();
                    return;
                }
                if (!model.isTransitionRunning()) {
                    model.getFactionToken().execute();
                    scorePointToAdd--;
                }
            }
        };
    }

    public Consumer<Long> proceedProduction(ActionManager actionManager) {
        updateData();
        return time -> {
            delay = model.getUIConfig().getAnimationSpeed();
            if (time - stopTime.get() < delay * 1_000_000L || pointAnimationIsRunning || model.getChoiceDialog().isShowing()) {
                return;
            }
            stopTime.set(time);
            switch (block) {
                case ProductionBlocks.FACTIONS -> {
                    highlightCard(factionCards, model.getFactionCards().get(CardTypes.PRODUCTION), factionHValue, 0, ProductionBlocks.DEALS, counter.decrementAndGet(), -1, () -> processCard(selectedCard));
                    factionHValue += moveFactionBy;
                }
                case ProductionBlocks.DEALS -> {
                    highlightCard(deals, model.getDeals(), 0, dealVvalue, ProductionBlocks.FACTION_BOARD, counter.incrementAndGet(), deals.size(), () -> makeDeal(selectedCard));
                    dealVvalue += moveDealBy;
                }
                case ProductionBlocks.FACTION_BOARD -> highlightFactionBoard();
                case ProductionBlocks.COMMONS -> {
                    highlightCard(commonCards, model.getCommonCards().get(CardTypes.PRODUCTION), commonHvalue, 0, ProductionBlocks.DEFAULT, counter.getAndIncrement(), commonCards.size(), () -> processCard(selectedCard));
                    commonHvalue += moveCommonBy;
                }
                default -> {
                    selectedCard.deselect();
                    actionManager.setAnimationRunning(false);
                    actionManager.stop();
                }
            }
        };
    }

    private void processCard(Card selectedCard) {
        if (selectedCard.getCardData().getCondition() != null) {
            processCondition(selectedCard.getCardData().getCondition());
        } else if (!selectedCard.getCardData().getOrEffect().isEmpty()) {
            System.out.println(selectedCard.getCardData());
        } else {
            addSourceWithEffect(selectedCard.getCardData().getCardEffect().stream().map(CardEffects::getSource).toList(), false);
        }
    }

    private void makeDeal(Card selectedCard) {

    }

    private void processFactionBoard() {

    }

    private void updateData() {
        model.getFactionCards().get(CardTypes.PRODUCTION).setHvalue(0);
        model.getDeals().setVvalue(0);
        model.getCommonCards().get(CardTypes.PRODUCTION).setHvalue(0);
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
        block = ProductionBlocks.FACTIONS;
    }

    private void highlightCard(ObservableList<Node> list, ScrollPane scrollPane, double hValue, double vValue, ProductionBlocks nextProductionBlock, int index, int finish, Runnable processProducution) {
        if (index == finish) {
            block = nextProductionBlock;
            return;
        }
        selectedCard.deselect();
        selectedCard = (Card) list.get(index);
        selectedCard.select();
        smoothScroll(scrollPane, hValue, vValue);
        processProducution.run();
    }

    private void highlightFactionBoard() {
        selectedCard.deselect();
        model.getFactionBoard().select();
        processFactionBoard();
        model.getFactionBoard().deselect();
        counter.set(0);
        block = ProductionBlocks.COMMONS;
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

    private void addSourceWithEffect(List<Sources> sources, boolean point) {
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
                    if (point) {
                        pointAnimationIsRunning = true;
                        pointAnimation.start();
                    } else {
                        model.getOwnSupplies().get(source).addOne();
                    }
                }
            };

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            translateTransition.setToY(-2 * model.getUIConfig().getFactionTokenHeight());

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            fadeTransition.setToValue(0);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            scaleTransition.setToX(2);
            scaleTransition.setToY(2);

            ParallelTransition parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(transition, translateTransition, fadeTransition, scaleTransition);
            parallelTransition.setOnFinished(e -> selectedCard.getChildren().remove(imageNode.getImageView()));

            sequentialTransition.getChildren().add(parallelTransition);
        });
        sequentialTransition.play();
    }

    public void processCondition(Conditions condition) {
        List<Sources> list;
        switch (condition) {
            case Conditions.FACTION_CARD_2 -> {
                model.getChoiceDialog().addItems(selectedCard.getCardData().getCardEffect(), selectedCard.getCardData().getOrEffect());
                model.getChoiceDialog().show();
            }
            case Conditions.SAMURAI_3 -> {
                int count = CardsOperation.getCardsCountWithCondition(model, Card::hasSamurai, Conditions.SAMURAI_3.getLimit());
                scorePointToAdd = count;
                list = IntStream.range(0, count).mapToObj(i -> Sources.SCORE_POINT).toList();
                addSourceWithEffect(list, true);
            }
            default -> {
                int count = CardsOperation.getCardsCountWithCondition(model, card -> card.getCardData().getColors().contains(condition.getColor()), selectedCard.getCardData().getCondition().getLimit());
                list = IntStream.range(0, count).mapToObj(i -> selectedCard.getCardData().getCardEffect().getFirst().getSource()).toList();
                addSourceWithEffect(list, false);
            }
        }
    }
}
