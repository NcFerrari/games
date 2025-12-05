package cz.games.lp.frontend.actions;

import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.AnimationTimer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ActionManager extends AnimationTimer {

    private final CardMoveActions cardMoveActions;
    private final CommonModel model;
    private final AtomicInteger counter = new AtomicInteger();
    private Consumer<Long> consumerMethod;

    public ActionManager(CommonModel model) {
        this.model = model;
        cardMoveActions = new CardMoveActions(model);
    }

    public void drawFactionCard(Integer cardId) {
        cardMoveActions.drawCard(model.getFactionDeck(), model.getGameData().getSelectedFaction().getFactionCardPath(), cardId);
    }

    public void drawCommonCard(Integer cardId) {
        cardMoveActions.drawCard(model.getCommonDeck(), Texts.COMMON.get(), cardId);
    }

    public void prepareFirstFourCards() {
        counter.set(4);
        consumerMethod = time -> {
            switch (counter.getAndDecrement()) {
                case 3, 4 -> model.getFactionDeck().drawCard();
                case 1, 2 -> model.getCommonDeck().drawCard();
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
}
