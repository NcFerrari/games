package cz.games.lp.frontend.actions;

import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;

public class ActionManager {

    private final CardMoveActions cardMoveActions;
    private final CommonModel model;

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
}
