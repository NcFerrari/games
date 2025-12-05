package cz.games.lp.frontend.enums;

import cz.games.lp.frontend.models.CommonModel;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public enum CardDeckTypes {

    FACTION(model -> model.getGameData().getFactionCards(), (model, cardId) -> model.getActionManager().drawFactionCard(cardId)),
    COMMON(model -> model.getGameData().getCommonCards(), (model, cardId) -> model.getActionManager().drawCommonCard(cardId));

    private final Function<CommonModel, List<Integer>> loadCardFunction;
    private final BiConsumer<CommonModel, Integer> drawCardFunction;

    CardDeckTypes(Function<CommonModel, List<Integer>> modelFunction, BiConsumer<CommonModel, Integer> drawCardFunction) {
        this.loadCardFunction = modelFunction;
        this.drawCardFunction = drawCardFunction;
    }

    public List<Integer> getCardList(CommonModel model) {
        return loadCardFunction.apply(model);
    }

    public void drawCard(CommonModel model) {
        if (!getCardList(model).isEmpty()) {
            drawCardFunction.accept(model, getNextCard(model));
        }
    }

    private Integer getNextCard(CommonModel model) {
        if (getCardList(model).isEmpty()) {
            return -1;
        }
        return getCardList(model).getFirst();
    }
}
