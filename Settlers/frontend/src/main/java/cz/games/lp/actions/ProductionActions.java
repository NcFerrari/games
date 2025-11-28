package cz.games.lp.actions;

import cz.games.lp.components.Card;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.ProductionBlocks;
import cz.games.lp.enums.Sources;
import cz.games.lp.panes.PaneModel;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductionActions extends AnimationTimer {

    private static final long DELAY = 1_000_000_000L;
    private final PaneModel model;
    private final AtomicInteger atomicIndex = new AtomicInteger();

    private List<Node> list;
    private boolean isAnimationTimerRunning;
    private boolean produceDialogOpen;
    private long stoppedTime = 0L;
    private Card selectedCard;
    private Runnable productionMethod;
    private Runnable switchPhase;
    private ProductionBlocks nextProductionBlocks;

    public ProductionActions(PaneModel model) {
        this.model = model;
    }

    public void proceedProduction(Runnable switchPhase) {
        this.switchPhase = switchPhase;
        if (isAnimationTimerRunning) {
            return;
        }
        isAnimationTimerRunning = true;
        nextProductionBlocks = ProductionBlocks.FACTIONS;
        getProductionFromSpecificBlock();
    }

    private void getProductionFromSpecificBlock() {
        switch (nextProductionBlocks) {
            case FACTIONS -> {
                list = model.getBuiltFactionCards().get(CardType.PRODUCTION).getChildren().reversed();
                executeAnimationTimer(this::produceFactionOrCommonCards);
                nextProductionBlocks = ProductionBlocks.DEALS;
            }
            case DEALS -> {
                list = model.getDeals().getChildren();
                executeAnimationTimer(this::produceDeals);
                nextProductionBlocks = ProductionBlocks.FACTION_BOARD;
            }
            case FACTION_BOARD -> {
                executeAnimationTimer(() -> list = new ArrayList<>());
                nextProductionBlocks = ProductionBlocks.COMMONS;
            }
            case COMMONS -> {
                list = model.getBuiltCommonCards().get(CardType.PRODUCTION).getChildren();
                executeAnimationTimer(this::produceFactionOrCommonCards);
                nextProductionBlocks = ProductionBlocks.DEFAULT;
            }
            default -> {
                isAnimationTimerRunning = false;
                switchPhase.run();
            }
        }
    }

    private void produceFactionOrCommonCards() {
        selectedCard = (Card) list.get(atomicIndex.getAndIncrement());
        selectedCard.select();
        selectedCard.getCardEffect().forEach(cardEffect -> model.getSources().get(Sources.valueOf(cardEffect)).addOne());
    }

    private void produceDeals() {
        selectedCard = (Card) list.get(atomicIndex.getAndIncrement());
        selectedCard.select();
        model.getSources().get(Sources.valueOf(selectedCard.getDealSource())).addOne();
    }

    private void produceFactionBoard() {

    }

    private void executeAnimationTimer(Runnable method) {
        productionMethod = method;
        atomicIndex.set(0);
        start();
    }

    @Override
    public void handle(long currentTime) {
        if (currentTime < stoppedTime + DELAY || produceDialogOpen) {
            return;
        }
        if (selectedCard != null) {
            selectedCard.deselect();
        }
        if (atomicIndex.get() == list.size()) {
            stop();
            getProductionFromSpecificBlock();
            return;
        }
        stoppedTime = currentTime;
        productionMethod.run();
    }
}
