package cz.games.lp.frontend.actions;

import cz.games.lp.frontend.enums.CardDeckTypes;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ActionManager extends AnimationTimer {

    private final CardMoveActions cardMoveActions;
    private final ProductionActions productionActions;
    private final CommonModel model;
    private final AtomicInteger counter = new AtomicInteger();
    @Setter
    @Getter
    private boolean animationRunning;
    private Consumer<Long> consumerMethod;

    public ActionManager(CommonModel model) {
        this.model = model;
        cardMoveActions = new CardMoveActions(model);
        productionActions = new ProductionActions(model);
    }

    public void drawFactionCard(Integer cardNumber) {
        cardMoveActions.drawCard(model.getFactionDeck(), model.getGameData().getSelectedFaction().getFactionCardPath(), cardNumber);
    }

    public void drawCommonCard(Integer cardNumber) {
        cardMoveActions.drawCard(model.getCommonDeck(), Texts.COMMON.get(), cardNumber);
    }

    public void prepareFirstFourCards() {
        dealCards(4);
    }

    public void lookoutPhase() {
        dealCards(3);
    }

    private void dealCards(int numberOfCards) {
        counter.set(numberOfCards);
        consumerMethod = time -> {
            switch (counter.getAndDecrement()) {
                case 3, 4 -> CardDeckTypes.FACTION.drawCard(model);
                case 1, 2 -> CardDeckTypes.COMMON.drawCard(model);
                default -> stop();
            }
        };
        start();
    }

    @Override
    public void handle(long time) {
        if (model.isTransitionRunning()) {
            return;
        }
        consumerMethod.accept(time);
    }

    public void productionPhase() {
        if (animationRunning) {
            return;
        }
        setAnimationRunning(true);
        consumerMethod = productionActions.proceedProduction(this);
        start();
    }
}
