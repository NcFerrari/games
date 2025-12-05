package cz.games.lp.frontend.actions;

import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.AnimationTimer;

public class ProductionActions extends AnimationTimer {

    private final CommonModel model;

    public ProductionActions(CommonModel model) {
        this.model = model;
    }

    public void proceedProduction() {
        
    }

    private void getProductionFromSpecificBlock() {
//        switch (nextProductionBlocks) {
//            case FACTIONS -> {
//                list = model.getBuiltFactionCards().get(CardType.PRODUCTION).getChildren().reversed();
//                executeAnimationTimer(() -> produceFactionOrCommonCards((Card) list.get(atomicIndex.getAndIncrement())));
//                nextProductionBlocks = ProductionBlocks.DEALS;
//            }
//            case DEALS -> {
//                list = model.getDeals().getChildren();
//                executeAnimationTimer(this::produceDeals);
//                nextProductionBlocks = ProductionBlocks.FACTION_BOARD;
//            }
//            case FACTION_BOARD -> {
//                executeAnimationTimer(() -> list = new ArrayList<>());
//                nextProductionBlocks = ProductionBlocks.COMMONS;
//            }
//            case COMMONS -> {
//                list = model.getBuiltCommonCards().get(CardType.PRODUCTION).getChildren();
//                executeAnimationTimer(() -> produceFactionOrCommonCards((Card) list.get(atomicIndex.getAndIncrement())));
//                nextProductionBlocks = ProductionBlocks.DEFAULT;
//            }
//            default -> {
//                isAnimationTimerRunning = false;
//                switchPhase.run();
//            }
//        }
    }

    private void produceFactionOrCommonCards(Card selectedCard) {
//        this.selectedCard = selectedCard;
////        model.setCardInProcess(true);
//        selectedCard.select();
//        if (selectedCard.getCondition() != null) {
//            useConditionFromProductionCard();
//        } else if (!selectedCard.getOrEffect().isEmpty()) {
//            Platform.runLater(() -> {
//                Optional<List<String>> response = choiceDialog.addSources(selectedCard.getCardEffect(), selectedCard.getOrEffect());
//                response.ifPresent(responseList -> {
//                    if ("SCORE_POINT".equals(responseList.getFirst())) {
////                        model.getScoreBoard().scorePoint(responseList.size());
//                    } else {
////                        responseList.forEach(cardEffect -> model.getSources().get(Sources.valueOf(cardEffect)).addOne());
////                        model.setCardInProcess(false);
//                    }
//                });
//            });
//        } else if ("COMMON_CARD".equals(selectedCard.getCardEffect().getFirst())) {
////            model.getActionManager().drawCommonCard(selectedCard.getCardEffect().size());
//        } else {
////            selectedCard.getCardEffect().forEach(cardEffect -> model.getSources().get(Sources.valueOf(cardEffect)).addOne());
////            model.setCardInProcess(false);
//        }
    }

    private void useConditionFromProductionCard() {
        int founds;
//        switch (selectedCard.getCondition()) {
//            case "SAMURAI" -> {
////                founds = checkAllCards(Card::isSamurai);
////                model.getScoreBoard().scorePoint(founds);
//            }
//            case "FACTION_CARD" -> {
////                model.getActionManager().drawFactionCard(selectedCard.getCardEffect().size());
//            }
//            default -> {
////                founds = checkAllCards(card -> card.getColors().contains(Colors.valueOf(selectedCard.getCondition())));
////                IntStream.range(0, founds).forEach(index -> model.getSources().get(Sources.valueOf(selectedCard.getCardEffect().getFirst())).addOne());
//            }
//        }
    }

//    private int checkAllCards(Predicate<Card> predicate) {
//        AtomicInteger foundCondition = new AtomicInteger(0);
//        model.getBuiltFactionCards().forEach((cardType, cards) ->
//                cards.getChildren().stream().filter(node -> predicate.test((Card) node)).forEach(node ->
//                        foundCondition.getAndIncrement()));
//        model.getBuiltCommonCards().forEach((cardType, cards) ->
//                cards.getChildren().stream().filter(node -> predicate.test((Card) node)).forEach(node ->
//                        foundCondition.getAndIncrement()));
//
//        if (foundCondition.get() > selectedCard.getCardEffect().size()) {
//            foundCondition.set(selectedCard.getCardEffect().size());
//        }
//        return foundCondition.get();
//    }

//    private void produceDeals() {
//        selectedCard = (Card) list.get(atomicIndex.getAndIncrement());
//        selectedCard.select();

    /// /        model.getSources().get(Sources.valueOf(selectedCard.getDealSource())).addOne();
//    }
//
//    private void produceFactionBoard() {
//
//    }
//
//    private void executeAnimationTimer(Runnable method) {
//        productionMethod = method;
//        atomicIndex.set(0);
//        start();
//    }
//
    @Override
    public void handle(long currentTime) {
//        if (currentTime < stoppedTime + DELAY || model.isCardInProcess()) {
//            return;
//        }
//        if (selectedCard != null) {
//            selectedCard.deselect();
//        }
//        if (atomicIndex.get() == list.size()) {
//            stop();
//            getProductionFromSpecificBlock();
//            return;
//        }
//        stoppedTime = currentTime;
//        productionMethod.run();
    }
}
