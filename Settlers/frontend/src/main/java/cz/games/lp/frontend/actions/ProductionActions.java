package cz.games.lp.frontend.actions;

import cz.games.lp.common.enums.CardEffects;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Conditions;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.ProductionBlocks;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
    private HighlightMessenger data;

    public ProductionActions(CommonModel model) {
        this.model = model;
        selectedCard = new Card(model);
    }

    public Consumer<Long> proceedProduction() {
        updateData();
        return time -> {
            delay = model.getUIConfig().getAnimationSpeed();
            if (time - stopTime.get() < delay * 1_000_000L || model.getActionManager().isPointAnimationIsRunning() || model.getChoiceDialog().isShowing()) {
                return;
            }
            switch (block) {
                case ProductionBlocks.FACTIONS -> {
                    data = new HighlightMessenger(factionCards, model.getFactionCards().get(CardTypes.PRODUCTION), factionHValue, 0, ProductionBlocks.DEALS, counter.decrementAndGet(), -1, () -> processCard(selectedCard), time);
                    highlightCard(data);
                    factionHValue += moveFactionBy;
                }
                case ProductionBlocks.DEALS -> {
                    data = new HighlightMessenger(deals, model.getDeals(), 0, dealVvalue, ProductionBlocks.FACTION_BOARD, counter.incrementAndGet(), deals.size(), () -> makeDeal(selectedCard), time);
                    highlightCard(data);
                    dealVvalue += moveDealBy;
                }
                case ProductionBlocks.FACTION_BOARD -> highlightFactionBoard();
                case ProductionBlocks.COMMONS -> {
                    data = new HighlightMessenger(commonCards, model.getCommonCards().get(CardTypes.PRODUCTION), commonHvalue, 0, ProductionBlocks.DEFAULT, counter.getAndIncrement(), commonCards.size(), () -> processCard(selectedCard), time);
                    highlightCard(data);
                    commonHvalue += moveCommonBy;
                }
                default -> {
                    selectedCard.deselect();
                    model.getActionManager().setAnimationRunning(false);
                    model.getActionManager().stop();
                }
            }
        };
    }

    private void processCard(Card selectedCard) {
        if (selectedCard.getCardData().getCondition() != null) {
            processCondition(selectedCard.getCardData().getCondition());
        } else if (!selectedCard.getCardData().getOrEffect().isEmpty()) {
            model.getChoiceDialog().addItems(selectedCard, delay, selectedCard.getCardData().getCardEffect(), selectedCard.getCardData().getOrEffect());
            model.getChoiceDialog().show();
        } else {
            model.getActionManager().addSourceWithEffect(selectedCard.getCardData().getCardEffect().stream().map(CardEffects::getSource).toList(), selectedCard);
        }
    }

    private void makeDeal(Card selectedCard) {
        model.getActionManager().addSourceWithEffect(List.of(selectedCard.getCardData().getDealSource()), selectedCard);
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

    private void highlightCard(HighlightMessenger data) {
        if (data.index == data.finish) {
            block = data.nextProductionBlock;
            return;
        }
        stopTime.set(data.time);
        selectedCard.deselect();
        selectedCard = (Card) data.list.get(data.index);
        selectedCard.select();
        smoothScroll(data.scrollPane, data.hValue, data.vValue);
        data.processProduction.run();
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

    public void processCondition(Conditions condition) {
        List<Sources> list;
        switch (condition) {
            case Conditions.FACTION_CARD_2 -> {
                model.getChoiceDialog().addItems(null, 0, selectedCard.getCardData().getCardEffect(), selectedCard.getCardData().getOrEffect());
                model.getChoiceDialog().show();
            }
            case Conditions.SAMURAI_3 -> {
                int count = CardsOperation.getCardsCountWithCondition(model, Card::hasSamurai, Conditions.SAMURAI_3.getLimit());
                list = IntStream.range(0, count).mapToObj(i -> Sources.SCORE_POINT).toList();
                model.getActionManager().addSourceWithEffect(list, selectedCard);
            }
            default -> {
                int count = CardsOperation.getCardsCountWithCondition(model, card -> card.getCardData().getColors().contains(condition.getColor()), selectedCard.getCardData().getCondition().getLimit());
                list = IntStream.range(0, count).mapToObj(i -> selectedCard.getCardData().getCardEffect().getFirst().getSource()).toList();
                model.getActionManager().addSourceWithEffect(list, selectedCard);
            }
        }
    }

    private record HighlightMessenger(ObservableList<Node> list, ScrollPane scrollPane, double hValue, double vValue,
                                      ProductionBlocks nextProductionBlock, int index, int finish,
                                      Runnable processProduction, long time) {

    }
}
