package cz.games.lp.actions;

import cz.games.lp.components.Card;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Sources;
import cz.games.lp.panes.PaneModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductionActions {

    private final PaneModel model;
    private boolean isTimeLineRunning;
    private final Timeline timeline = new Timeline();
    private final AtomicInteger atomicIndex = new AtomicInteger();

    public ProductionActions(PaneModel model) {
        this.model = model;
    }

    public void proceedProduction(Runnable switchPhase) {
        if (isTimeLineRunning) {
            return;
        }
        timeline.getKeyFrames().clear();
        atomicIndex.set(0);
        produceFactionCards();
        produceDeals();
        produceFactionBoard();
        produceCommonCards();
        timeline.setOnFinished(evt -> {
//            switchPhase.run();
            isTimeLineRunning = false;
        });
        isTimeLineRunning = true;
        timeline.play();
    }

    private void produceFactionCards() {
        model.getBuiltFactionCards().get(CardType.PRODUCTION).getChildren().forEach(card -> timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(atomicIndex.get()), e -> ((Card) card).select()),
                new KeyFrame(Duration.seconds(atomicIndex.getAndIncrement()), e -> executeCardEffect((Card) card)),
                new KeyFrame(Duration.seconds(atomicIndex.get()), e -> ((Card) card).deselect())
        ));
    }

    private void produceDeals() {
        model.getDeals().getChildren().forEach(card -> timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(atomicIndex.get()), e -> ((Card) card).select()),
                new KeyFrame(Duration.seconds(atomicIndex.getAndIncrement()), e -> getDeal((Card) card)),
                new KeyFrame(Duration.seconds(atomicIndex.get()), e -> ((Card) card).deselect())
        ));
    }

    private void produceFactionBoard() {

    }

    private void produceCommonCards() {
//        model.getBuiltCommonCards().get(CardType.PRODUCTION).getChildren().forEach(card -> timeline.getKeyFrames().addAll(
//                new KeyFrame(Duration.seconds(atomicIndex.get()), e -> ((Card) card).select()),
//                new KeyFrame(Duration.seconds(atomicIndex.getAndIncrement()), e -> executeEffect((Card) card)),
//                new KeyFrame(Duration.seconds(atomicIndex.get()), e -> ((Card) card).deselect())
//        ));
    }

    private void executeCardEffect(Card card) {
        if (card.getOrEffect().isEmpty()) {
            card.getCardEffect().forEach(source -> model.getSources().get(Sources.valueOf(source)).addOne());
        } else {
            ProduceChoiceDialog dialog = new ProduceChoiceDialog(model);
            dialog.showAndWait();
        }
    }

    private void getDeal(Card card) {
        model.getSources().get(Sources.valueOf(card.getDealSource())).addOne();
    }

    public CompletableFuture<Void> produce(Card card, Map<Sources, SourceStatusBlock> sources) {
//        if (card.getCardEffect().contains("|||")) {
//            String[] choices = card.getCardEffect().split("\\|\\|");
//            CompletableFuture[] futures = new CompletableFuture[choices.length];
//            for (int i = 0; i < choices.length; i++) {
//                futures[i] = sources.get(Sources.valueOf(choices[i])).waitForSelection();
//            }
//            return CompletableFuture.anyOf(futures).thenAccept(selectedObj -> {
//                SourceStatusBlock selected = (SourceStatusBlock) selectedObj;
//                selected.addOne();
//                for (String choice : choices) {
//                    sources.get(Sources.valueOf(choice)).deselect();
//                }
//            }).thenApply(v -> null);
//        }
//        if (card.getCardEffect().contains("&&")) {
//            Arrays.stream(card.getCardEffect().split("&&")).forEach(source ->
//                    sources.get(Sources.valueOf(source)).addOne()
//            );
//        }
//        return CompletableFuture.completedFuture(null);
        return null;
    }
}
